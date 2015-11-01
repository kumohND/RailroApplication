package kr.ac.kumoh.railroApplication.classes;

/**
 * Created by sj on 2015-07-23.
 */
public class TripListItem {
    String TripTitle;
    String TripDate;
    int iconId;
    int mDB_Position;
    public TripListItem(){
        super();
    }
    public TripListItem(String tripTitle, String tripDate, int iconId,int position) {
        TripTitle = tripTitle;
        TripDate = tripDate;
        this.iconId = iconId;
        this.mDB_Position = position;
    }

    public int getmDB_Position() {
        return mDB_Position;
    }

    public void setmDB_Position(int mDB_Position) {
        this.mDB_Position = mDB_Position;
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
