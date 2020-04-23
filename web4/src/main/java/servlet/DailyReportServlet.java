package servlet;

import com.google.gson.Gson;
import model.DailyReport;
import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DailyReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = "";
        if (req.getPathInfo().contains("all")) {
            List<DailyReport> list = DailyReportService.getInstance().getAllDailyReports();
            json = gson.toJson(list);
            resp.getWriter().print(json);
        } else if (req.getPathInfo().contains("last")) {
            DailyReport dailyReport = DailyReportService.getInstance().getLastReport();
            json = gson.toJson(dailyReport);
            resp.getWriter().print(json);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DailyReportService.getInstance().delete();
    }
}
