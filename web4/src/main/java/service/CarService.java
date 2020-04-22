package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.TimeZone;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;


    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public void addCar(Car car) {
        CarDao carDao = new CarDao(sessionFactory.openSession());
        carDao.addCar(car);
    }

    public List<Car> getAllCars() {
        CarDao carDao = new CarDao(sessionFactory.openSession());
        return carDao.getAllCars();
    }


    public void addListCars(List list) {
        CarDao carDao = new CarDao(sessionFactory.openSession());
        carDao.addListCars(list);
    }

    public void bayCar(String brand, String model, String licensePlate) {
        try
        {
            CarDao carDao = new CarDao(sessionFactory.withOptions()
                    .jdbcTimeZone(TimeZone.getTimeZone("UTC"))
                    .openSession());
            carDao.bayCar(brand,model,licensePlate);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Такой машины нет");
        }

    }
}
