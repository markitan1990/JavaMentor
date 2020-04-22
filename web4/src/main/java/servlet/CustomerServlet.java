package servlet;

import com.google.gson.Gson;
import model.Car;
import org.hibernate.HibernateException;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomerServlet extends HttpServlet {
    private CarService carService = CarService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = new Gson().toJson(CarService.getInstance().getAllCars());
        resp.getWriter().print(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            carService.bayCar(req.getParameter("brand"),
                    req.getParameter("model"),
                    req.getParameter("licensePlate"));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (HibernateException | NumberFormatException e) {
            log("Что то не так");
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
