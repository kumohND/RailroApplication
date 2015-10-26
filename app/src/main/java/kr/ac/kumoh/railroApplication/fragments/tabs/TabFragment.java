package kr.ac.kumoh.railroApplication.fragments.tabs;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.adapters.PlanListRVArrayAdapter;
import kr.ac.kumoh.railroApplication.classes.AddItem;
import kr.ac.kumoh.railroApplication.classes.PlanListItem;
import kr.ac.kumoh.railroApplication.classes.RealTimeLocationListener;
import kr.ac.kumoh.railroApplication.classes.UseDB;
import kr.ac.kumoh.railroApplication.fragments.BaseFragment;
import kr.ac.kumoh.railroApplication.util.AnimUtils;
import kr.ac.kumoh.railroApplication.widget.RecyclerClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//Tab 에서 수정해야될 가능성이 높을듯..
public class TabFragment extends BaseFragment {


    private static final String ARG_START = "start_param";
    public static final int USER_SET_START_TIME = 6;
    public static final int USER_SET_END_TIME = 22;
    public static final int AMOUNT_OF_COLUMNS = 1;
    public static final int AMOUNT_OF_TIME_COUNT = USER_SET_END_TIME - USER_SET_START_TIME + 1;
    private
    @DrawableRes
    int mStart;

    public static int AAA;

    private final int REQUEST_PLAN = 1000;
    private final int MOVE_TRAIN = 0;
    private final int MOVE_BUS = 1;
    private final int EAT = 2;
    private final int SLEEP = 3;

    static LocationManager mManager;
    static RealTimeLocationListener mRTLocation;
    Context mContext;
    String viewPagerState;
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

    private static List<PlanListItem> mPlanList;
    private RecyclerView recyclerView;
    private Button mButton;
    private int index;
    UseDB mDB;
    String textName;
    int duration;
    public void test() {

        Bundle bundle = getArguments();
        String str = null;

        if (bundle.getString("date") != null) {
            str = bundle.getString("date!!!!!!!!!!!!!");
        }

        Log.d("d","TestFunction!");
        Toast.makeText(getActivity(), "TestFuction!!",Toast.LENGTH_SHORT).show();
        mPlanList.add(new PlanListItem("", "", R.color.cardview_shadow_end_color, 9));
        InitializeAdapter();

    }
    public static TabFragment newInstance(int start) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_START, start);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void GetContentValue()
    {
        mDB = new UseDB(mContext);
        ContentValues mValue = mDB.Read(index);
        textName = String.valueOf(mValue.get("dbTextName"));
        duration = Integer.valueOf(String.valueOf(mValue.get("duration")));


    }


    void ReadIndex()
    {
        String named_buffer;
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
        try{
            File file;
            file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path + File.separator + "temp_index" + ".txt");


            BufferedReader buw = new BufferedReader(new FileReader(file));
            named_buffer = buw.readLine();
            index = Integer.valueOf(named_buffer);
            buw.close();
        }catch(IOException e)
        {

        }
    }
    public void ReadViewPagerIdFromText()
    {
        String named_buffer;
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
        try{
            File file;
            file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path + File.separator + "temp" + ".txt");


            BufferedReader buw = new BufferedReader(new FileReader(file));
            named_buffer = buw.readLine();
            viewPagerState = named_buffer;
            buw.close();
        }catch(IOException e)
        {

        }
    }

    public void ReadSetRecyclerView()
    {
        ReadViewPagerIdFromText();
        String named_buffer;
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
        ArrayList<String> mTokens = new ArrayList<String>();
        try{
            File file;
            file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path + File.separator + textName + ".txt");


            BufferedReader buw = new BufferedReader(new FileReader(file));
            int duration = Integer.valueOf(buw.readLine());
            for(int i = 0 ; i < duration; i++)
            {
                if(buw.readLine().equals(viewPagerState)) // 해당 뷰페이저 인덱스 List
                {
                    for(int j = 1; j < 25; j++)
                    {
                        String check = buw.readLine();
                        if(check.length() > 10){ // 데이터 존재
                            StringTokenizer token = new StringTokenizer(check,"%&#");
                            for( ; !token.hasMoreElements(); )
                                 mTokens.add(token.nextToken());


                            if(mTokens.get(0).equals(String.valueOf(MOVE_TRAIN)) || mTokens.get(0).equals(String.valueOf(MOVE_BUS))) {
                                mPlanList.add(new PlanListItem("출발 시간 출발 장소 -> 도착 시간 도착 장소 "
                                        , "설명", R.color.cardview_shadow_end_color, 7));
                            }else if(mTokens.get(0).equals(String.valueOf(MOVE_TRAIN))) {
                                mPlanList.add(new PlanListItem("출발 시간 출발 장소 -> 도착 시간 도착 장소 "
                                        , "설명", R.color.cardview_shadow_end_color, 7));
                            }else if(mTokens.get(0).equals(String.valueOf(MOVE_TRAIN))) {
                                mPlanList.add(new PlanListItem("출발 시간 출발 장소 -> 도착 시간 도착 장소 "
                                        , "설명", R.color.cardview_shadow_end_color, 7));
                            }
                        }
                    }
                }

            }
            buw.close();
        }catch(IOException e)
        {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mContext = view.getContext();
        ReadIndex();
        GetContentValue();
        setupRecyclerView(view);

//        mRTLocation = new RealTimeLocationListener(view.getContext());
//        long minTime = 1000;
//        float minDistance = 0;
//
////        isGPSEnabled = mManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
////        if(isGPSEnabled != false) {
//        mManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
//        mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, mRTLocation);
//        //}



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


                //getParentFragment().startActivityForResult(intent, REQUEST_PLAN);
  //              getParentFragment().startActivityForResult(intent, REQUEST_PLAN);
                //TODO : 선정 완성된 정보 보여주기 + 수정 버튼 포함

            }
        }));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), AMOUNT_OF_COLUMNS);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        InitializeData();
        InitializeAdapter();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        // super.startActivityForResult(intent, ((TabFragment.mIndex + 1) << 16) + (requestCode & 0xffff));

    }

    // Text 에서 데이터 읽어오면될듯, 시간정보
    private void InitializeData() {
        ReadSetRecyclerView();


//        for (int i = USER_SET_START_TIME; i <= USER_SET_END_TIME; i++) {
//            // mPlanList.add(new PlanListItem("", "", R.color.cardview_shadow_end_color, i ));
//        }
        // mPlanList.add(new PlanListItem("이동(기차)", "서울역 -> 대전역", R.color.titleTextColor));
        //  mPlanList.add(new PlanListItem("이동(버스)", "범물동 -> 지산동", 0));
    }

    private void InitializeAdapter() {
        PlanListRVArrayAdapter arrayAdapter = new PlanListRVArrayAdapter(mPlanList);
        recyclerView.setAdapter(arrayAdapter);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mButton = ButterKnife.findById(getActivity(), R.id.add_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetTripPlanActivity.class);
               // getParentFragment().startActivityForResult(intent, REQUEST_PLAN);
                startActivityForResult(intent, REQUEST_PLAN);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_PLAN) {
            Log.d("d", "!!!!!!!!!!!!!!!!!!!!");
            mPlanList.add(new PlanListItem("", "", R.color.cardview_shadow_end_color, 9));
            InitializeAdapter();
        }
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
