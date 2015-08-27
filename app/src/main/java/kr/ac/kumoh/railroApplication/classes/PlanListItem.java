package kr.ac.kumoh.railroApplication.classes;

/**
 * Created by sj on 2015-07-23.
 */
public class PlanListItem {

    String PlanTitle;
    String PlanDetail;
    int Time;
    int tagColor;

    public PlanListItem(){
        super();
    }

    public PlanListItem(String planTitle, String planDetail, int Time, int tagColor) {
        PlanTitle = planTitle;
        PlanDetail = planDetail;
        this.Time = Time;
        this.tagColor = tagColor;
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

    public int getTagColor() {
        return tagColor;
    }

    public void setTagColor(int tagColor) {
        this.tagColor = tagColor;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }
}
