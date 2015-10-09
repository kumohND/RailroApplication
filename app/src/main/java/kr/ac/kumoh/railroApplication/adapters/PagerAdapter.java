package kr.ac.kumoh.railroApplication.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.classes.AddItem;
import kr.ac.kumoh.railroApplication.fragments.tabs.TabFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String mdata;


    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {
        // AddItem item = new AddItem();
        return TabFragment.newInstance(getOffset(position));
        //return TabFragment.newInstance();
    }


    private int getOffset(int position) {
        switch (position) {
            /*
            case 0: return 0;
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
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = mContext.getString(R.string.not_title_set);
        switch (position) {
            case 0:
                title = "7/29";
                break;
            case 1:
                title = "8/1";
                break;
            case 2:
                title = "8/2";
                break;
            case 3:
                title = "8/3";
                break;
            case 4:
                title = "8/4";
                break;
        }
        return title;
    }


}
