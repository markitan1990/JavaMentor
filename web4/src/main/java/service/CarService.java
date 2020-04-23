package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.DBHelper;

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
        try (Session session = sessionFactory.openSession();) {
            CarDao carDao = new CarDao(sessionFactory.openSession());
            carDao.addCar(car);
        }
    }

    public List<Car> getAllCars() {
        CarDao carDao = null;
        try (Session session = sessionFactory.openSession()) {
            carDao = new CarDao(sessionFactory.openSession());
        }
        return carDao.getAllCars();
    }

    public void bayCar(String brand, String model, String licensePlate) {
        try (Session session = sessionFactory.openSession();) {
            CarDao carDao = new CarDao(session);
            carDao.bayCar(brand, model, licensePlate);
        }
    }
}
