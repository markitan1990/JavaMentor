package DAO;

import model.Car;
import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import service.DailyReportService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class DailyReportDao {
    LocalDate toDay = LocalDate.now();
    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        List<DailyReport> dailyReports = null;
        Transaction transaction = session.beginTransaction();
        try {
            dailyReports = session.createQuery("FROM DailyReport").list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return dailyReports;
    }

    public DailyReport getLastReport() {
        DailyReport dailyReport = null;
        List<DailyReport> list = null;
        Transaction transaction = session.beginTransaction();
        try {
            Query find = session.createQuery("FROM DailyReport");
            list = find.list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return list.get(list.size()-1);
    }

    public LocalDate getToDay() {
        Transaction transaction = session.getTransaction();
        LocalDate localDate = toDay;
        try {
            Query query = session.createQuery("from DailyReport");
            List<DailyReport> list = query.list();
            localDate = (list.get(list.size() - 1)).getDate();
        } catch (Exception e) {
            transaction.rollback();
        }
        return localDate;
    }

    public void delete() {
        Transaction transaction = session.beginTransaction();
        try {
            session.createQuery("delete from DailyReport ").executeUpdate();
            session.createQuery("delete from Car ").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public void generateDailyReport(LocalDate localDate, List<Car> list) {
        long salary = 0;
        long count = 0;
        for (Car a : list) {
            salary += a.getPrice();
            count++;
        }
        Transaction transaction = session.getTransaction();
        try {
            localDate = localDate.plusDays(1);
            DailyReport dailyReport = new DailyReport(localDate, salary, count);
            session.save(dailyReport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
