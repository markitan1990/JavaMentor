import model.Car;
import model.DailyReport;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.Session;
import servlet.CustomerServlet;
import servlet.DailyReportServlet;
import servlet.NewDayServlet;
import servlet.ProducerServlet;
import util.DBHelper;

//import sun.security.pkcs11.Secmod;

public class Main {

    private static ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    private static Server server = new Server(8080);

    private static CustomerServlet customerServlet = new CustomerServlet();
    private static NewDayServlet newDayServlet = new NewDayServlet();
    private  static ProducerServlet producerServlet = new ProducerServlet();
    private static DailyReportServlet dailyReportServlet = new DailyReportServlet();

    public static void main(String[] args) throws Exception {
        servletContextHandler.addServlet(new ServletHolder(producerServlet), "/produser");
        servletContextHandler.addServlet(new ServletHolder(customerServlet), "/customer");

        servletContextHandler.addServlet(new ServletHolder(newDayServlet), "/newday");
        servletContextHandler.addServlet(new ServletHolder(dailyReportServlet), "/report/*");

        server.setHandler(servletContextHandler);

        server.start();
        server.join();

    }
}
