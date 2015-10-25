package kr.ac.kumoh.railroApplication.fragments;


<<<<<<< HEAD
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
=======
import android.content.Intent;
>>>>>>> master
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.adapters.TripListRVArrayAdapter;
<<<<<<< HEAD
import kr.ac.kumoh.railroApplication.classes.UseDB;
import kr.ac.kumoh.railroApplication.fragments.tabs.TabFragment;
import kr.ac.kumoh.railroApplication.manager.SQLiteManager;
=======
import kr.ac.kumoh.railroApplication.fragments.tabs.PlanListTabActivity;
import kr.ac.kumoh.railroApplication.fragments.tabs.PlanListTabFragment;
import kr.ac.kumoh.railroApplication.fragments.tabs.SearchPlaceActivity;
import kr.ac.kumoh.railroApplication.fragments.tabs.SetTripPlanActivity;
>>>>>>> master
import kr.ac.kumoh.railroApplication.widget.RecyclerClickListener;
import kr.ac.kumoh.railroApplication.classes.TripListItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyTripListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTripListFragment extends BaseFragment {

    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PinnedAppBarkFragment.
     */

    private List<TripListItem> mTripList;
    private RecyclerView recyclerView;
    //UseDB mDB_Helper;
    SQLiteManager mDB_Helper;
    SQLiteDatabase mDB;
    public static MyTripListFragment newInstance(Context mContext) {
        return new MyTripListFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCollapsingToolbar.setTitle(getString(getTitle()));
        mContext = getActivity().getApplicationContext();
        setList();
    }

    public MyTripListFragment() {
        // Required empty public constructor
    }

    private void setList() {

        recyclerView = ButterKnife.findById(getActivity(), R.id.simpleList);
        recyclerView.addOnItemTouchListener(new RecyclerClickListener(getActivity(), new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // do whatever
<<<<<<< HEAD
                // 수정 부분
                Toast.makeText(getActivity(), position + "I'm Clicked~~", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, TabFragment.newInstance(0));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                Intent intent = new Intent(getActivity(), TabFragment.class);
//                getParentFragment().startActivityForResult(intent, 0);


=======
                Toast.makeText(getActivity(), "I'm Clicked~~", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PlanListTabActivity.class);
                startActivity(intent);
>>>>>>> master
            }
        }));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //  TripListRVArrayAdapter arrayAdapter = new TripListRVArrayAdapter(getData());
        // recyclerView.setAdapter(arrayAdapter);

        InitializeData();
        InitializeAdapter();

    }

    private void InitializeData() {
//        mTripList = new ArrayList<>();
//        mTripList.add(new TripListItem("맛집 내일로 여행", "2015/07/25 ~ 2015/08/01", R.drawable.ic_android));
//        mTripList.add(new TripListItem("사진 여행", "2015/08/11 ~ 2015/08/15", R.drawable.ic_android));
        mDB_Helper = new SQLiteManager(mContext);
        mDB = mDB_Helper.getReadableDatabase();
        Cursor c= mDB.query("railo",null,null,null,null,null,null);
        mTripList = new ArrayList<>();

        while(c.moveToNext())
        {
            String duration = c.getString(c.getColumnIndex("duration"));
            String year = c.getString(c.getColumnIndex("year"));
            String month = c.getString(c.getColumnIndex("month"));
            String day = c.getString(c.getColumnIndex("day"));
            Calendar compare = Calendar.getInstance();
            compare.set(Calendar.YEAR, Integer.valueOf(year));
            compare.set(Calendar.MONTH, (Integer.valueOf(month)));
            compare.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
            for(int i = 0 ; i < (Integer.valueOf(duration) -1) ; i++) // 날자증가
                compare.add(Calendar.DAY_OF_MONTH,1);

            mTripList.add(new TripListItem(c.getString(c.getColumnIndex("dbTitleName")),
                    year + "/" + month + "/" + day + "~" +
                            compare.get(Calendar.YEAR) + "/" + (compare.get(Calendar.MONTH))
                            + "/" + compare.get(Calendar.DAY_OF_MONTH), R.drawable.ic_android));
            // titleName , Duration + date , 그림
        }

    }

    private void InitializeAdapter() {
        TripListRVArrayAdapter arrayAdapter = new TripListRVArrayAdapter(mTripList);
        recyclerView.setAdapter(arrayAdapter);
    }

    @Override
    protected int getTitle() {
        return R.string.my_plan_list;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar_flexible_space;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_my_trip_list;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
