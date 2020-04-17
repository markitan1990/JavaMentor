package servlet;

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

public class MoneyTransactionServlet extends HttpServlet {
    private BankClientService bankClientService = new BankClientService();
    private Map<String, Object> pageVariables = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print(PageGenerator.getInstance().getPage("moneyTransactionPage.html", new HashMap<>()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        long longValue = new Long(req.getParameter("count"));
        String name = req.getParameter("senderName");
        String pass = req.getParameter("senderPass");
        BankClient bankClient = new BankClient(name, pass, longValue);
        boolean res = bankClientService.sendMoneyToClient(bankClient,
                req.getParameter("nameTo"),
                new Long(req.getParameter("count")));

        if (res) {
            pageVariables.put("message", "The transaction was successful");
        } else {
            pageVariables.put("message", "transaction rejected");
        }
        resp.getWriter().print(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));

    }
}
