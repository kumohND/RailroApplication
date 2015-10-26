package kr.ac.kumoh.railroApplication.classes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

/**
 * Created by sj on 2015-07-23.
 * 지연 코드수정 :
 */
public class TripInfoFragInfo {
    String Addr1;
    String Addr2;
    int AreaCode;
    int ContentTypeId;
    String Image;
    double MapX;
    double MapY;
    String Title;
    String ZipCode;
    int Index;
    int Count;


    public TripInfoFragInfo(String addr1, String addr2, int areacode, int contenttypeid, String image, double mapx, double mapy,
                            String title, String zipcode, int index, int count) {
        Addr1 = addr1;
        Addr2 = addr2;
        AreaCode = areacode;
        this.ContentTypeId = contenttypeid;
        this.Image = image;
        MapX = mapx;
        MapY = mapy;
        Title = title;
        ZipCode = zipcode;
        this.Index = index;
        this.Count = count;
    }

    public String getAddr1() {
        return Addr1;
    }
    public void setAddr1(String addr1) { Addr1 = addr1;}

    public String getAddr2() { return Addr2;  }

    public void setAddr2(String addr2) { Addr2 = addr2;}

    public int getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(int areacode){ AreaCode = areacode;
    }
    public int getContentTypeId() { return ContentTypeId;}

    public void setContentTypeId(int contenttypeid) { ContentTypeId = contenttypeid;}

    public String getImage(){return Image;}

    public void setImage(String image){ Image=  image;}

    public double getMapX() {
        return MapX;
    }

    public void setMapX(double mapx){ MapX = mapx;}

    public double getMapY() { return MapY; }

    public void setMapY(double mapy){ MapY = mapy;}


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title){ Title = title;}

    public String getZipCode() {  return ZipCode; }

    public void setZipCode(String zipcode){ ZipCode = zipcode;}

    public int getIndex(){ return Index;}

    public void setIndex(int index){ Index = index;}

    public int getCount(){ return Count;}

    public void setCount(int count){ Count = count;}

}
