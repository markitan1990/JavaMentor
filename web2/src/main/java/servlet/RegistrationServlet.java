package servlet;

import model.User;
import service.UserService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    private UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print(PageGenerator.getInstance().getPage("registerPage.html", null));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (!email.isEmpty() && !password.isEmpty()) {
            User user = new User(email, password);
            if (!userService.addUser(user)) {
                resp.getWriter().print(PageGenerator.getInstance().getPage("registerPage.html", null));
                resp.getWriter().print("This user is already registered, please try to login.");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                resp.getWriter().printf("Congratulations %s you registered successfully, your registration number %d", email, user.hashCode());
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } else {
            resp.getWriter().print(PageGenerator.getInstance().getPage("registerPage.html", null));
            resp.getWriter().print("Incorect data");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
