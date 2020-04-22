package service;

import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DailyReportService {
    LocalDate todayLocalDate = LocalDate.now( ZoneId.of( "Europe/Minsk" ) );

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() {
        return new DailyReportDao(sessionFactory.openSession()).getAllDailyReport();
    }


    public DailyReport getLastReport() {
        return null;
    }

    public LocalDate getLocalDate() {
        return todayLocalDate;
    }

    public void setNewDay(LocalDate todayLocalDate) {
        LocalDate n = todayLocalDate.plusDays(1);
        this.todayLocalDate = n;
    }
}
