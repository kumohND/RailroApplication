package kr.ac.kumoh.railroApplication.classes;

/**
 * Created by sj on 2015-10-13.
 */
public class SetTripDate {
    int trip_days;
    int year;
    int month;
    int day;
    String trip_title;

    public SetTripDate(int trip_days, int year, int month, int day, String trip_title) {
        this.trip_days = trip_days;
        this.year = year;
        this.month = month;
        this.day = day;
        this.trip_title = trip_title;
    }

    public int getTrip_days() {
        return trip_days;
    }

    public void setTrip_days(int trip_days) {
        this.trip_days = trip_days;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTrip_title() {
        return trip_title;
    }

    public void setTrip_title(String trip_title) {
        this.trip_title = trip_title;
    }
}
