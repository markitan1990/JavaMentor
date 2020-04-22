import model.Car;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.Session;
import servlet.CustomerServlet;
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

    public static void main(String[] args) throws Exception {

        servletContextHandler.addServlet(new ServletHolder(customerServlet), "/customer");
        servletContextHandler.addServlet(new ServletHolder(producerServlet), "/produser");
        servletContextHandler.addServlet(new ServletHolder(newDayServlet), "/newday");

        server.setHandler(servletContextHandler);

        server.start();
        server.join();

    }
}
