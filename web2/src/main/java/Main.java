import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.ApiServlet;
import servlet.LoginServlet;
import servlet.RegistrationServlet;

public class Main {
    private static ApiServlet apiServlet = new ApiServlet();
    private static RegistrationServlet registrationServlet = new RegistrationServlet();
    private static LoginServlet loginServlet = new LoginServlet();
    private static ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    private static Server server = new Server(8080);
    public static void main(String[] args) throws Exception {

        context.addServlet(new ServletHolder(apiServlet), "/api/*");
        context.addServlet(new ServletHolder(registrationServlet), "/register");
        context.addServlet(new ServletHolder(loginServlet), "/login");

        server.setHandler(context);

        server.start();
        server.join();
    }
}
