package kr.ac.kumoh.railroApplication.classes;

import kr.ac.kumoh.railroApplication.R;

/**
 * Created by sj on 2015-07-23.
 */
public class PlanListItem {

    int category;
    String PlanTitle;
    String PlanDetail;
    String startPlace;
    String endPlace;
    int PlanImg;
    int Time;
    int endTime;
    private final int MOVE_TRAIN = 0;
    private final int MOVE_BUS = 1;
    private final int EAT = 2;
    private final int SLEEP = 3;
    void SetChangeCategory()
    {
        if(this.category == MOVE_TRAIN)
        {
             PlanImg = R.drawable.ic_android;
        }else if(this.category == MOVE_BUS)
        {
            PlanImg = R.drawable.ic_email;
        }else if(this.category == EAT)
        {
            PlanImg = R.drawable.ic_food;
        }else if(this.category == SLEEP)
        {
            PlanImg = R.drawable.ic_expand_more_black_18dp;
        }
    }


    public PlanListItem() {
        super();
    }

    public PlanListItem(String planTitle, String planDetail, int PlanImg, int Time) {
        PlanTitle = planTitle;
        PlanDetail = planDetail;
        this.PlanImg = PlanImg;
        this.Time = Time;
    }

    void SetttingTitle()
    {
        if(category == MOVE_TRAIN || category == MOVE_BUS) {
            this.PlanTitle = startPlace + " -> " + endPlace;
        }else
            this.PlanTitle = startPlace;
    }


    public PlanListItem(int category, int time, int endTime,  String planDetail, String startPlace,String endPlace, String planTitle) {
       this.category = category;
        this.Time = time;
        this.endTime = endTime;
        this.PlanTitle = planTitle;
        this.PlanDetail = planDetail;
        this.startPlace = startPlace;
        this.endPlace = endPlace;

        SetttingTitle();
        SetChangeCategory();

    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
        SetChangeCategory();
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
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
