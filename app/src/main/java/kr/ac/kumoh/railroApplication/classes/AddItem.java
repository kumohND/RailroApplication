package kr.ac.kumoh.railroApplication.classes;

/**
 * Created by sj on 2015-09-10.
 */
public class AddItem {

    int AddTime;
    int AddImage;
    int AddRate;
    String AddTitle;
    String AddCategory;

    public AddItem(){
        super();
    }

    public AddItem(int addTime, int addImage, int addRate, String addTitle, String addCategory) {
        AddTime = addTime;
        AddImage = addImage;
        AddRate = addRate;
        AddTitle = addTitle;
        AddCategory = addCategory;
    }

    public int getAddTime() {
        return AddTime;
    }

    public void setAddTime(int addTime) {
        AddTime = addTime;
    }

    public int getAddImage() {
        return AddImage;
    }

    public void setAddImage(int addImage) {
        AddImage = addImage;
    }

    public int getAddRate() {
        return AddRate;
    }

    public void setAddRate(int addRate) {
        AddRate = addRate;
    }

    public String getAddTitle() {
        return AddTitle;
    }

    public void setAddTitle(String addTitle) {
        AddTitle = addTitle;
    }

    public String getAddCategory() {
        return AddCategory;
    }

    public void setAddCategory(String addCategory) {
        AddCategory = addCategory;
    }
}
