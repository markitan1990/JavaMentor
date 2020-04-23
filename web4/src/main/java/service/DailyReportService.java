package service;

import DAO.DailyReportDao;
import model.Car;
import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class DailyReportService {
    private LocalDate localDate = LocalDate.now(TimeZone.getTimeZone("Europe/Minsk").toZoneId());
//    private LocalDate localDate = LocalDate.now();
    private  List<Car> list = new ArrayList<>();

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

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
        DailyReportDao dailyReportDao = null;
        List<DailyReport> list = null;
        try (Session session = sessionFactory.openSession()) {
            dailyReportDao = new DailyReportDao(session);
            list = dailyReportDao.getAllDailyReport();
        }
        return list;
    }


    public DailyReport getLastReport() {
        DailyReportDao dailyReportDao = null;
        DailyReport dailyReport = null;
        try (Session session = sessionFactory.openSession()) {
            dailyReportDao = new DailyReportDao(session);
            dailyReport = dailyReportDao.getLastReport();
        }
        return dailyReport;
    }
public void generateDailyReport(){
        DailyReportDao dailyReportDao = null;
        try(Session session = sessionFactory.openSession()){
            dailyReportDao = new DailyReportDao(session);
            dailyReportDao.generateDailyReport(localDate ,list);
            list.clear();
        }
}
    public List<Car> getList() {
        return list;
    }

    public void setList(List<Car> list) {
        this.list = list;
    }
//    public LocalDate getToDay() {
//        DailyReportDao dailyReportDao = null;
//        LocalDate localDate = null;
//        try (Session session = sessionFactory.openSession()) {
//            dailyReportDao = new DailyReportDao(session);
//            localDate = dailyReportDao.getToDay();
//        }
//        return localDate;
//    }
//    public void nextDay(LocalDate localDate){
//        DailyReportDao dailyReportDao = null;
//        try (Session session = sessionFactory.openSession()) {
//            dailyReportDao = new DailyReportDao(session);
//            dailyReportDao.setToDay(localDate);
//        }
//    }

    public void delete() {
        try(Session session = sessionFactory.openSession()){
            DailyReportDao dailyReportDao = new DailyReportDao(session);
            dailyReportDao.delete();
        }
    }
}
