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
import java.util.HashMap;
import java.util.Map;

public class RegistrationServlet extends HttpServlet {
    private BankClientService bankClientService = new BankClientService();
    private Map<String, Object> pageVariables = new HashMap<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print(PageGenerator.getInstance().getPage("registrationPage.html", pageVariables));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        try {
            bankClientService.createTable();
            long count = 0;
            try {
                count = new Long(req.getParameter("money"));
            } catch (NumberFormatException e) {
                System.out.println("Сумма не указана и будет по умолчанию = 0");
            }
            boolean res = bankClientService.addClient(new BankClient(req.getParameter("name"),
                    req.getParameter("password"), count));
            if (res) {
                pageVariables.put("message", "Add client successful");
            } else {
                pageVariables.put("message", "Client not add");
            }
            resp.getWriter().print(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));

        } catch (DBException y) {
            resp.getWriter().print(PageGenerator.getInstance().getPage("registrationPage.html", pageVariables));
            resp.getWriter().print("A user with this name already exists");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (NumberFormatException e) {
            resp.getWriter().print(PageGenerator.getInstance().getPage("registrationPage.html", pageVariables));
            resp.getWriter().print("The amount cannot be negative");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
