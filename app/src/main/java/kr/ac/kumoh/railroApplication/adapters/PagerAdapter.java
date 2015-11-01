package kr.ac.kumoh.railroApplication.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.classes.AddItem;
import kr.ac.kumoh.railroApplication.classes.TripListItem;
import kr.ac.kumoh.railroApplication.classes.UseDB;
import kr.ac.kumoh.railroApplication.fragments.tabs.TabFragment;
import kr.ac.kumoh.railroApplication.manager.SQLiteManager;

public class PagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private ArrayList<String> mDate;
    SQLiteManager mDB_Helper;
    SQLiteDatabase mDB;
    String duration;
    String year;
    int index;

    public void DateReturn() {
        mDB_Helper = new SQLiteManager(mContext);
        mDB = mDB_Helper.getReadableDatabase();
        Cursor c = mDB.query("railo", null, null, null, null, null, null);
        mDate = new ArrayList<String>();

        UseDB useDB = new UseDB(mContext);
        ContentValues mContentValue = new ContentValues();
        mContentValue = useDB.Read(index);
        duration = String.valueOf(mContentValue.get("duration"));
        year = String.valueOf(mContentValue.get("year"));
        String month = String.valueOf(mContentValue.get("month"));
        String day = String.valueOf(mContentValue.get("day"));


        Calendar compare = Calendar.getInstance();
        compare.set(Calendar.YEAR, Integer.valueOf(year));
        compare.set(Calendar.MONTH, (Integer.valueOf(month)));
        compare.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
        mDate.add(month + "/" + day);
        for (int i = 0; i < (Integer.valueOf(duration) - 1); i++) { // 날자증가
            compare.add(Calendar.DATE, 1);
            month = String.valueOf(compare.get(Calendar.MONTH));
            day = String.valueOf(compare.get(Calendar.DAY_OF_MONTH));

            mDate.add(month + "/" + day);
        }


        // titleName , Duration + date , 그림

    }

    public PagerAdapter(FragmentManager fm, Context context, int index) {
        super(fm);
        mContext = context;
        this.index = index;
        DateReturn();
    }

    @Override
    public Fragment getItem(int position) {
        // AddItem item = new AddItem();
        return TabFragment.newInstance(getOffset(position));
        //return TabFragment.newInstance();
    }


    private int getOffset(int position) {
        switch (position) {

         /*   case 0: return 0;
            case 1: return 5;
            case 2: return 10;
            case 3: return 15;*/

        }
        return 1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TabFragment f = (TabFragment) super.instantiateItem(container, position);
        // String title = mList.get(position);
        //  f.setTitle(title);
        return f;
    }

    @Override
    public int getItemPosition(Object object) {
        //return PagerAdapter.POSITION_NONE;

        return POSITION_NONE;

    }

    @Override
    public int getCount() {
        return Integer.valueOf(duration);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = mContext.getString(R.string.not_title_set);

        if (mDate.size() == 5) {

            switch (position) {
                case 0:
                    title = mDate.get(0);
                    break;
                case 1:
                    title = mDate.get(1);
                    break;
                case 2:
                    title = mDate.get(2);
                    break;
                case 3:
                    title = mDate.get(3);
                    break;
                case 4:
                    title = mDate.get(4);
                    break;
            }

        } else if (mDate.size() == 7) {

            switch (position) {
                case 0:
                    title = mDate.get(0);
                    break;
                case 1:
                    title = mDate.get(1);
                    break;
                case 2:
                    title = mDate.get(2);
                    break;
                case 3:
                    title = mDate.get(3);
                    break;
                case 4:
                    title = mDate.get(4);
                    break;
                case 5:
                    title = mDate.get(5);
                    break;
                case 6:
                    title = mDate.get(6);
                    break;
            }


        }
        return title;
    }


}
