package kr.ac.kumoh.railroApplication.classes;

/**
 * Created by sj on 2015-08-13.
 */
public class StationInfo {

    String stationName;
    String stationCode;


    public StationInfo(String stationName, String stationCode) {
        this.stationName = stationName;
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public String getStationCode() {
        return stationCode;
    }

}
