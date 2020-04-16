import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.MainServlet;
import servlets.MyServlet;


public class Main {
    private static MainServlet mainServlet = new MainServlet();
    private static MyServlet myServlet = new MyServlet();
    private static ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    private static Server server = new Server(8080);

    public static void main(String[] args) throws Exception {

        context.addServlet(new ServletHolder(mainServlet), "/");
        context.addServlet(new ServletHolder(myServlet), "/mult");


        server.setHandler(context);

        server.start();
        server.join();
    }
}
