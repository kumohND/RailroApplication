package kr.ac.kumoh.railroApplication.classes;

/**
 * Created by Jongmin on 2015-10-30.
 */
public class PlaceinfoDataList {

    String imageURL;
    String mTitle;
    String mContent;

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
