package kr.ac.kumoh.railroApplication.fragments.tabs;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.adapters.PlanListRVArrayAdapter;
import kr.ac.kumoh.railroApplication.classes.AddItem;
import kr.ac.kumoh.railroApplication.classes.PlanListItem;
import kr.ac.kumoh.railroApplication.fragments.BaseFragment;
import kr.ac.kumoh.railroApplication.util.AnimUtils;
import kr.ac.kumoh.railroApplication.widget.RecyclerClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragment extends BaseFragment {


    private static final String ARG_START = "start_param";
    public static final int USER_SET_START_TIME = 6;
    public static final int USER_SET_END_TIME = 22;
    public static final int AMOUNT_OF_COLUMNS = 1;
    public static final int AMOUNT_OF_TIME_COUNT = USER_SET_END_TIME - USER_SET_START_TIME + 1;
    private
    @DrawableRes
    int mStart;

    private final int REQUEST_PLAN = 1000;
    private final int REQUEST_CANCLE = 1001;
    private final int REQUEST_MODIFY = 1002;
/*
    private final
    @DrawableRes
    int[] ROOT_DATA = {
            R.drawable.car,
            R.drawable.cross,
            R.drawable.doll,
            R.drawable.desert,
            R.drawable.fantastic,
            R.drawable.girl,
            R.drawable.glass,
            R.drawable.jump,
            R.drawable.mens,
            R.drawable.plunge,
            R.drawable.shadow,
            R.drawable.wall,
            R.drawable.baseball,
            R.drawable.beach_with_hair,
            R.drawable.cat_window,
            R.drawable.crying,
            R.drawable.food,
            R.drawable.map,
            R.drawable.mini_food,
            R.drawable.mirror,
            R.drawable.soup
    };
*/

    private List<PlanListItem> mPlanList;
    private RecyclerView recyclerView;

    public static TabFragment newInstance(int start) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_START, start);
        fragment.setArguments(args);
        return fragment;
    }

    public TabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStart = getArguments().getInt(ARG_START);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setupRecyclerView(view);
        return view;
    }


    private void setupRecyclerView(View view) {
        mPlanList = new ArrayList<>();

        recyclerView = ButterKnife.findById(view, R.id.simpleGrid);
        recyclerView.addOnItemTouchListener(new RecyclerClickListener(getActivity(), new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "I'm Clicked~~", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SetTripPlanActivity.class);
                startActivityForResult(intent, REQUEST_PLAN);

            }
        }));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), AMOUNT_OF_COLUMNS);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        InitializeData();
        InitializeAdapter();
    }

    private void InitializeData() {


        mPlanList.add(new PlanListItem("", "", R.color.cardview_shadow_end_color, 7 ));
        for (int i = USER_SET_START_TIME; i <= USER_SET_END_TIME; i++) {
           // mPlanList.add(new PlanListItem("", "", R.color.cardview_shadow_end_color, i ));
        }
        // mPlanList.add(new PlanListItem("이동(기차)", "서울역 -> 대전역", R.color.titleTextColor));
        //  mPlanList.add(new PlanListItem("이동(버스)", "범물동 -> 지산동", 0));
    }

    private void InitializeAdapter() {
        PlanListRVArrayAdapter arrayAdapter = new PlanListRVArrayAdapter(mPlanList);
        recyclerView.setAdapter(arrayAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_PLAN:

                Toast.makeText(getActivity(), "YEEEEEEEEEEEH", Toast.LENGTH_SHORT).show();
                String Title = data.getStringExtra("setTitle");
                String Detail = data.getStringExtra("setTitle");
                int Image = data.getIntExtra("setImage", 0);
                int Time = data.getIntExtra("setTime",6);
                mPlanList.add(new PlanListItem(Title, Detail, Image, Time));

                InitializeAdapter();
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected PlanListItem[] getData() {
        PlanListItem[] data = new PlanListItem[AMOUNT_OF_TIME_COUNT];
        int j = 0;
        for (int i = 0; i < AMOUNT_OF_TIME_COUNT; i++) {
            // data[i] = new PlanListItem("이동(기차)", "서울역 -> 대전역", R.color.titleTextColor);
        }
        return data;
    }
   /* protected @DrawableRes int[] getData(){
        @DrawableRes int[] data = new int[AMOUNT_OF_TIME_COUNT];
        int j=0;
        for(int i=mStart; i<(mStart+ AMOUNT_OF_TIME_COUNT);++i){
            data[j++] = ROOT_DATA[i];
        }
        return data;
    }*/



    @Override
    protected int getLayout() {
        return R.layout.fragment_tab;
    }


}
