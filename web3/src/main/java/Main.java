import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.ApiServlet;
import servlet.MoneyTransactionServlet;
import servlet.RegistrationServlet;
import servlet.ResultServlet;

public class Main {
    private static ApiServlet apiServlet = new ApiServlet();
    private static RegistrationServlet registrationServlet = new RegistrationServlet();
    private static ResultServlet resultServlet = new ResultServlet();
    private static MoneyTransactionServlet moneyTransactionServlet = new MoneyTransactionServlet();
    private static ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    private static Server server = new Server(8080);

    public static void main(String[] args) throws Exception {

        context.addServlet(new ServletHolder(apiServlet), "/api/*");
        context.addServlet(new ServletHolder(registrationServlet), "/registration");
        context.addServlet(new ServletHolder(resultServlet), "/result");
        context.addServlet(new ServletHolder(moneyTransactionServlet), "/transaction");

        server.setHandler(context);

        server.start();
        server.join();
    }
}
