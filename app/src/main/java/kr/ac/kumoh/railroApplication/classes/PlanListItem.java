package kr.ac.kumoh.railroApplication.classes;

/**
 * Created by sj on 2015-07-23.
 */
public class PlanListItem {

    String PlanTitle;
    String PlanDetail;
    int PlanImg;
    int Time;

    public PlanListItem() {
        super();
    }

    public PlanListItem(String planTitle, String planDetail, int PlanImg, int Time) {
        PlanTitle = planTitle;
        PlanDetail = planDetail;
        this.PlanImg = PlanImg;
        this.Time = Time;
    }

    public String getPlanTitle() {
        return PlanTitle;
    }

    public void setPlanTitle(String planTitle) {
        PlanTitle = planTitle;
    }

    public String getPlanDetail() {
        return PlanDetail;
    }

    public void setPlanDetail(String planDetail) {
        PlanDetail = planDetail;
    }

    public int getPlanImg() {
        return PlanImg;
    }

    public void setPlanImg(int PlanImg) {
        this.PlanImg = PlanImg;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }
}
