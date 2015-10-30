package kr.ac.kumoh.railroApplication.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.util.Calendar;

import kr.ac.kumoh.railroApplication.manager.SQLiteManager;

/**
 * Created by WooChan on 2015-10-20.
 */
public class UseDB {
    SQLiteDatabase tDb;
    SQLiteManager helper;
    Context mContext;

    public UseDB(Context mContext)
    {
        this.mContext = mContext;
        helper = new SQLiteManager(this.mContext);
    }

    public ContentValues Read(int index)
    {
        helper = new SQLiteManager(mContext);
        tDb = helper.getReadableDatabase();
        Cursor c= tDb.query("railo",null,null,null,null,null,null);
        ContentValues values = new ContentValues();
        while(c.moveToNext())
        {


            int getIndex = c.getInt(c.getColumnIndex("_id"));
            if(getIndex == index) {
                String dbName = c.getString(c.getColumnIndex("dbTextName"));
                String tName = c.getString(c.getColumnIndex("dbTitleName"));
                String date = c.getString(c.getColumnIndex("startDate"));
                String duration = c.getString(c.getColumnIndex("duration"));
                String year = c.getString(c.getColumnIndex("year"));
                String month = c.getString(c.getColumnIndex("month"));
                String day = c.getString(c.getColumnIndex("day"));
                values.put("index_id",getIndex);
                values.put("dbTextName",dbName);
                values.put("dbTitleName",tName);
                values.put("startDate", date);
                values.put("duration",duration);
                values.put("year",year);
                values.put("month",month);
                values.put("day",day);

                tDb.close();
                return values;
            }

            // titleName , Duration + date , 그림
        }
        tDb.close();
        return null;

    }


    public void Insert(String tName,String year,String month,String day,String duration) // DB 저장 이름,
    {
        String date = year + month + day;
        String dbName = tName + "-" + date + "-" + duration;

        String path = "/data/data/kr.ac.kumoh.railrotravel/files/datasheet.ext"; // 저장 할 곳
        File file;
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + File.separator + dbName + ".txt"); // 여기서 텍스트 이름만 수정하면 될듯

        ContentValues values = new ContentValues();


        values.put("dbTextName",dbName);
        values.put("dbTitleName",tName);
        values.put("startDate", date);
        values.put("year",year);
        values.put("month",month);
        values.put("day", day);
        values.put("duration",duration);
        tDb = helper.getWritableDatabase();
        tDb.insert("railo",null,values);
        tDb.close();
    } // 같은 이름 예외처리

    public void Delete(int id)
    {
        String str_ID = String.valueOf(id);
        tDb = helper.getWritableDatabase();
        tDb.delete("railo","_id=?",new String[]{str_ID});
        tDb.close();
    }

    public void DeleteTable()
    {
        tDb = helper.getWritableDatabase();
        helper.onUpgrade(tDb,0,0);
    }

}
