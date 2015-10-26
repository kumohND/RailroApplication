package kr.ac.kumoh.railroApplication.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by WooChan on 2015-10-20.
 */
public class SQLiteManager extends SQLiteOpenHelper {
    public static final String DB_NAME = "railo.db";
    public static final int DB_VERSION = 1;

    public SQLiteManager(Context context) {
            super(context, DB_NAME,null,DB_VERSION);
    }
    String sql;
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Create Table", "Suc");
        sql = "create table railo(" +
                "_id integer primary key autoincrement, " +
                "dbTitleName text,"+ //User Making Name
                "dbTextName text,"+ //User Making Name
                "startDate text,"+ //User Making Name
                "year text,"+ //User Making Name
                "month text,"+ //User Making Name
                "day text,"+ //User Making Name
                "duration text"+ //User Making Name
                ");";

        db.execSQL(sql);
        Log.i("Create Table", "End");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql2 = "drop table if exist nep";

        db.execSQL(sql2);
        onCreate(db);
    }
}
