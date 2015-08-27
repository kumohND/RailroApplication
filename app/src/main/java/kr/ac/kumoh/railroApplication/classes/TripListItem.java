package kr.ac.kumoh.railroApplication.classes;

/**
 * Created by sj on 2015-07-23.
 */
public class TripListItem {
    String TripTitle;
    String TripDate;
    int iconId;

    public TripListItem(){
        super();
    }
    public TripListItem(String tripTitle, String tripDate, int iconId) {
        TripTitle = tripTitle;
        TripDate = tripDate;
        this.iconId = iconId;
    }

    public String getTripTitle() {
        return TripTitle;
    }

    public void setTripTitle(String tripTitle) {
        TripTitle = tripTitle;
    }

    public String getTripDate() {
        return TripDate;
    }

    public void setTripDate(String tripDate) {
        TripDate = tripDate;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
