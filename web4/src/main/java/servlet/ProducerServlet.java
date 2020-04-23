package servlet;

import model.Car;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import service.CarService;
import util.DBHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {
    private CarService carService = CarService.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            carService.addCar(new Car(req.getParameter("brand"),
                    req.getParameter("model"),
                    req.getParameter("licensePlate"),
                    new Long(req.getParameter("price"))));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (HibernateException | NumberFormatException e) {
            log("Что то не так");
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
