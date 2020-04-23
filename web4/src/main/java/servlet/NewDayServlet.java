package servlet;

import service.DailyReportService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewDayServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DailyReportService dailyReportService = DailyReportService.getInstance();
        dailyReportService.generateDailyReport();
        dailyReportService.setLocalDate(dailyReportService.getLocalDate().plusDays(1));
        System.out.println(dailyReportService.getLocalDate());
    }
}
