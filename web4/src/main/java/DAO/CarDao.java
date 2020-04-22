package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import service.DailyReportService;
import util.DBHelper;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public void addCar(Car car) {
        session.beginTransaction();
        if (!isCarExist(car) && isCarsLessTen(car)) {
            session.save(car);
        } else {
            System.out.println("Bad query");
            throw new HibernateException("Bad query");
        }
        session.getTransaction().commit();
        session.close();
    }

    private boolean isCarExist(Car car) {
        boolean res = false;
        Query query = session.createQuery("from Car WHERE brand = :brand and model = :model and licensePlate = :licensePlate");
        query.setParameter("brand", car.getBrand())
                .setParameter("model", car.getModel())
                .setParameter("licensePlate", car.getLicensePlate());
        List list = query.list();
        if (list.size() > 0) {
            res = true;
            System.out.println("this Car already exist");
        }
        return res;
    }

    private boolean isCarsLessTen(Car car) {
        boolean res = false;
        Query query = session.createQuery("from Car WHERE brand = :brand");
        query.setParameter("brand", car.getBrand());
        List list = query.list();
        if (list.size() < 10) {
            res = true;
        } else {
            System.out.println("Cars this brand should be less 10");
        }
        return res;
    }

    public List<Car> getAllCars() {
        return session.createQuery("From Car").list();
    }

    public void addListCars(List<Car> cars) {
        Session session = DBHelper.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        for (Car a : cars) {
            session.save(a);
        }
        t.commit();
        session.close();
    }

    public void bayCar(String brand, String model, String licensePlate) {

        Transaction t = session.beginTransaction();
        long id = getIdFromCar(brand, model, licensePlate);
        Car bayCar = (Car) session.load(Car.class, id);
        setReport(bayCar);
        bayCar.setId(id);
        session.delete(bayCar);
        t.commit();
    }

    public Long getIdFromCar(String brand, String model, String licensePlate) {
        Query find = session.createQuery("from Car where brand = :brand and model = :model and licensePlate = :licensePlate");
        find.setParameter("brand", brand)
                .setParameter("model", model)
                .setParameter("licensePlate", licensePlate);
        List<Car> list = find.list();
        return list.get(0).getId();
    }

    public void setReport(Car car) {
        DailyReportService dailyReportService = DailyReportService.getInstance();
        LocalDate localDate = dailyReportService.getLocalDate();

        DailyReport dailyReport = new DailyReport(localDate, car.getPrice(), 1L);
        session.save(dailyReport);
    }

}
