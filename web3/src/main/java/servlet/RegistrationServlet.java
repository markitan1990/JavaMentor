package servlet;

import exception.DBException;
import model.BankClient;
import service.BankClientService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class RegistrationServlet extends HttpServlet {
    private BankClientService bankClientService = new BankClientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print(PageGenerator.getInstance().getPage("registrationPage.html", new HashMap<>()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            bankClientService.createTable();
            bankClientService.addClient(new BankClient(req.getParameter("name"),
                    req.getParameter("password"),
                    new Long(req.getParameter("money"))));

            List<BankClient> list = bankClientService.getAllClient();
            for ( BankClient a : list) {
                System.out.println(a.getName());
            }

//            System.out.println(bankClientService.getClientById(2).getMoney().toString());
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException y) {
            resp.getWriter().print(PageGenerator.getInstance().getPage("registrationPage.html", new HashMap<>()));
            resp.getWriter().print("A user with this name already exists");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (NumberFormatException e) {
            resp.getWriter().print(PageGenerator.getInstance().getPage("registrationPage.html", new HashMap<>()));
            resp.getWriter().print("The amount cannot be negative");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
