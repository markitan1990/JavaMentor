package model;

import org.hibernate.Session;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "daily_reports")
public class DailyReport {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
//    @Type(type = "org.hibernate.type.LocalDateType")
    private LocalDate date;

    @Column(name = "earnings")
    private Long earnings;

    @Column(name = "soldCars")
    private Long soldCars;

    public DailyReport() {

    }

    public DailyReport(Long id, LocalDate date, Long earnings, Long soldCars) {
        this.earnings = earnings;
        this.soldCars = soldCars;
        this.date = date;
        this.id = id;
    }
    public DailyReport(LocalDate date, Long earnings, Long soldCars) {
        this.earnings = earnings;
        this.soldCars = soldCars;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEarnings() {
        return earnings;
    }

    public void setEarnings(Long earnings) {
        this.earnings = earnings;
    }

    public Long getSoldCars() {
        return soldCars;
    }

    public void setSoldCars(Long soldCars) {
        this.soldCars = soldCars;
    }
}
