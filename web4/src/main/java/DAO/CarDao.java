package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import service.DailyReportService;

import java.time.LocalDate;
import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public void addCar(Car car) {
        Transaction transaction = session.beginTransaction();
        try {
            if (!isCarExist(car) && isCarsLessTen(car)) {
                session.save(car);
                transaction.commit();
            } else {
                System.out.println("Bad query");
                throw new HibernateException("Bad query");
            }
        } catch (Exception e) {
            transaction.rollback();
        }
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
        Transaction transaction = session.getTransaction();
        List<Car> list = null;
        try {
            list = session.createQuery("From Car").list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return list;
    }

    public void bayCar(String brand, String model, String licensePlate) {
        DailyReportService dailyReportService = DailyReportService.getInstance();
        Transaction transaction = session.beginTransaction();
        Car bayCar = null;
        try {
            long id = getIdFromCar(brand, model, licensePlate);
            bayCar = (Car) session.load(Car.class, id);
            dailyReportService.getList().add(bayCar);
            session.delete(bayCar);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public Long getIdFromCar(String brand, String model, String licensePlate) {
        Query find = session.createQuery("from Car where brand = :brand and model = :model and licensePlate = :licensePlate");
        find.setParameter("brand", brand)
                .setParameter("model", model)
                .setParameter("licensePlate", licensePlate);
        List<Car> list = find.list();
        return list.get(0).getId();
    }
}
