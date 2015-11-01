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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.adapters.PlanListRVArrayAdapter;
import kr.ac.kumoh.railroApplication.classes.AddItem;
import kr.ac.kumoh.railroApplication.classes.NoAscCompare;
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
    PlanListRVArrayAdapter arrayAdapter;
    String TOKEN = "%&#";
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
    int clicked_List = -1;

    //    public void test() {
//
//        Bundle bundle = getArguments();
//        String str = null;
//
//        if (bundle.getString("date") != null) {
//            str = bundle.getString("date!!!!!!!!!!!!!");
//        }
//
//        Log.d("d","TestFunction!");
//        Toast.makeText(getActivity(), "TestFuction!!",Toast.LENGTH_SHORT).show();
//        mPlanList.add(new PlanListItem("", "", R.color.cardview_shadow_end_color, 9));
//        InitializeAdapter();
//
//    }
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

    public void GetContentValue() {
        mDB = new UseDB(mContext);
        ContentValues mValue = mDB.Read(index);
        String temp1 = String.valueOf(mValue.get("index_id"));
        String temp2 = String.valueOf(mValue.get("dbTextName"));
        textName = temp1 + temp2;
        duration = Integer.valueOf(String.valueOf(mValue.get("duration")));
        //Collections.sort(mPlanList,new NoAscCompare());

    }


    void ReadIndex() {
        String named_buffer;
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
        try {
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
        } catch (IOException e) {

        }
    }

    public void ReadViewPagerIdFromText() {
        String named_buffer;
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
        try {
            File file;
            file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path + File.separator + "view_Pager" + ".txt");


            BufferedReader buw = new BufferedReader(new FileReader(file));
            named_buffer = buw.readLine();
            viewPagerState = named_buffer;
        } catch (IOException e) {

        }
    }

    public void ReadSetRecyclerView() {
        ReadViewPagerIdFromText();
        String named_buffer;
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
        ArrayList<String> mTokens = new ArrayList<String>();
        File file;
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + File.separator + textName + ".txt");


        try {

            BufferedReader buw = new BufferedReader(new FileReader(file));

            int duration = Integer.valueOf(buw.readLine());
            for (int i = 0; i < duration; i++) {
                String test_Check = buw.readLine();
                if (test_Check.equals(viewPagerState)) // 해당 뷰페이저 인덱스 List
                {
                    for (int j = 1; j < 25; j++) {
                        String check = buw.readLine();
                        if (check.length() > 10) { // 데이터 존재

                            String value[] = check.split("%&#");

                            if (value[1].equals(String.valueOf(MOVE_TRAIN))
                                    || value[1].equals(String.valueOf(MOVE_BUS))) {
                                mPlanList.add(new PlanListItem(
                                        Integer.valueOf(value[1]), // category
                                        Integer.valueOf(value[2]), // 출발 시간
                                        Integer.valueOf(value[3]), // 도착 시간
                                        value[4],                  // 할 일
                                        value[5],                  // 출발 장소
                                        value[6],                  // 도착 장소
                                        ""));                      // 타이틀

                            } else if (value[1].equals(String.valueOf(EAT)) || value[1].equals(String.valueOf(SLEEP))) {
                                mPlanList.add(new PlanListItem(
                                        Integer.valueOf(value[1]), // category
                                        Integer.valueOf(value[2]), // 출발 시간
                                        Integer.valueOf(value[3]), // 도착 시간
                                        value[4],                  // 할 일
                                        value[5],                  // 출발 장소
                                        "",                        // 도착 장소
                                        ""));                      // 타이틀
                            }

                        }
                    }
                }
                for (int j = 1; j < 25; j++)
                    buw.readLine();
            }
            buw.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }

    }

    //    void TestRead()
//    {
//        BufferedReader buw;
//        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
//        File file;
//        file = new File(path);
//        String rawString = "";
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        file = new File(path + File.separator + textName + ".txt");
//
//
//        try{
//            int check;
//
//            String rawData = "";
//            buw =  new BufferedReader(new FileReader(file));
//            int duration = Integer.valueOf(buw.readLine());
//            Log.d("READ", rawData);
//
//
//            buw.close();
//        }catch(IOException e)
//        {
//        }
//
//    }
    View test;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        test = view;
        mContext = view.getContext();
        ReadIndex();
        GetContentValue();
        setupRecyclerView(view); // List초기화

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

    public String returnWriteString(int hour_index) {
        return "time " + hour_index + TOKEN;
    }

    private boolean DeleteTextLine(int position) {


        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext"; // 저장 할 곳
        File file;
        BufferedReader bur;
        BufferedWriter buw;
        int sHour = mPlanList.get(index).getTime();
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + File.separator + textName + ".txt"); // 여기서 텍스트 이름만 수정하면 될듯

        try {
            String dummy = "";
            String line;
            //String add_Line = returnWriteString();
            bur = new BufferedReader(new FileReader(file));

            int duration = Integer.valueOf(bur.readLine());
            dummy = String.valueOf(duration) + "\r\n";

            if (sHour != 0) {
                for (int j = 0; j < duration; j++) {
                    String state = bur.readLine();
                    dummy = dummy + state + "\r\n"; // 카테고리
                    if (state.equals(viewPagerState)) { // 데이터 체인지
                        for (int i = 1; i < 25; i++) {
                            //if(i == sHour){
                            if (i == sHour) {
                                String add_Line = returnWriteString(i);
                                dummy = dummy + add_Line + "\r\n";
                                bur.readLine();
                            } else {
                                dummy = dummy + bur.readLine() + "\r\n";
                            }
                        }
                    }
                    for (int i = 1; i < 25; i++)// 24시간 넘어감
                        dummy = dummy + bur.readLine() + "\r\n";
                }

                buw = new BufferedWriter(new FileWriter(file));
                buw.write(dummy);
                buw.close();
            }
            bur.close();
        } catch (IOException e) {

        }

        mPlanList.remove(position);
        return true;
    }

    private void setupRecyclerView(View view) {


        mPlanList = new ArrayList<>();
        recyclerView = ButterKnife.findById(view, R.id.simpleGrid);

        recyclerView.addOnItemTouchListener(new RecyclerClickListener(getActivity(), new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "I'm Clicked~~", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SetTripPlanActivity.class);
                intent.putExtra("index", index);
                intent.putExtra("pager", viewPagerState);
                intent.putExtra("position", position);
                if (mPlanList.size() != 0) {
                    intent.putExtra("startHour", mPlanList.get(position).getTime());
                    startActivityForResult(intent, REQUEST_PLAN);
                }

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
        arrayAdapter.notifyDataSetChanged();
        //view.notify();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

//        int position = intent.getIntExtra("position",-1);
//        int start_Hour = intent.getIntExtra("start_Hour",-1);
//
//        if(position == -1) {
//            //mPlanList.add(new PlanListItem("", "", R.color.cardview_shadow_end_color, 9));
//            InitializeData();
//            InitializeAdapter();
//        }else{
//            //텍스트는 수정된 상황, 추가말고
//            FixedList(start_Hour,position);
//        }

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

    public void NotifyAdaptter() {


        //test.notifyAll();
    }

    private void InitializeAdapter() {
//        PlanListRVArrayAdapter arrayAdapter = new PlanListRVArrayAdapter(mPlanList);
        arrayAdapter = new PlanListRVArrayAdapter(mPlanList);
        recyclerView.setAdapter(arrayAdapter);
        arrayAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                //arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                // arrayAdapter.notifyItemRangeChanged(positionStart,itemCount);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                // arrayAdapter.notifyItemRangeRemoved(positionStart,itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                // arrayAdapter.notifyItemRangeRemoved(positionStart,itemCount);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                // arrayAdapter.notifyItemMoved(fromPosition,toPosition);
                // TODO itemcount가 1일 경우이므로 1보다 크면 제대로 동작하지 않는다.
            }
        });


    }

    //Text저장된 곳에서 지움
    private boolean DeletePositionList(int position) {
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext"; // 저장 할 곳
        File file;
        BufferedReader bur;
        BufferedWriter buw;
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + File.separator + textName + ".txt"); // 여기서 텍스트 이름만 수정하면 될듯
        if (mPlanList.size() == 0) return false;

        try {

            String dummy = "";
            //String add_Line = returnWriteString();
            bur = new BufferedReader(new FileReader(file));

            int duration = Integer.valueOf(bur.readLine());
            dummy = String.valueOf(duration) + "\r\n";


            for (int j = 0; j < duration; j++) {
                String state = bur.readLine();
                dummy = dummy + state + "\r\n"; // 카테고리
                if (state.equals(viewPagerState)) { // 데이터 체인지
                    for (int i = 1; i < 25; i++) {
                        //if(i == sHour){
                        int deleteHour = mPlanList.get(position).getTime();
                        if (i == deleteHour) {
                            String add_Line = "time " + deleteHour + TOKEN;
                            dummy = dummy + add_Line + "\r\n";
                            bur.readLine();
                        } else {
                            dummy = dummy + bur.readLine() + "\r\n";
                        }
                    }
                }
                for (int i = 1; i < 25; i++)// 24시간 넘어감
                    dummy = dummy + bur.readLine() + "\r\n";
            }

            buw = new BufferedWriter(new FileWriter(file));
            buw.write(dummy);
            buw.close();

            bur.close();
        } catch (IOException e) {

        }

        return true;
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
                //  TestRead();
                intent.putExtra("index", index);
                intent.putExtra("pager", viewPagerState);
                startActivityForResult(intent, REQUEST_PLAN);
            }
        }); // 새로만든상황

    }

    public void FixedList(int Hour, int endHour, int position) {
        if (Hour == -1) return;

        ReadViewPagerIdFromText();
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
        File file;
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + File.separator + textName + ".txt");


        try {

            BufferedReader buw = new BufferedReader(new FileReader(file));

            int duration = Integer.valueOf(buw.readLine());
            for (int i = 0; i < duration; i++) {
                String test_Check = buw.readLine();
                if (test_Check.equals(viewPagerState)) // 해당 뷰페이저 인덱스 List
                {
                    for (int j = 1; j < 25; j++) {
                        String check = buw.readLine();
                        if (j == Hour) { // 데이터 존재
                            String value[] = check.split("%&#");

                            if (value[1].equals(String.valueOf(MOVE_TRAIN))
                                    || value[1].equals(String.valueOf(MOVE_BUS))) {
                                mPlanList.get(position).setCategory(Integer.valueOf(value[1]));
                                mPlanList.get(position).setTime(Integer.valueOf(value[2]));
                                mPlanList.get(position).setEndTime(Integer.valueOf(value[3]));
                                mPlanList.get(position).setPlanDetail(value[4]);
                                mPlanList.get(position).setStartPlace(value[5]);
                                mPlanList.get(position).setEndPlace(value[6]);

                            } else if (value[1].equals(String.valueOf(EAT))
                                    || value[1].equals(String.valueOf(SLEEP))) {
                                mPlanList.get(position).setCategory(Integer.valueOf(value[1]));
                                mPlanList.get(position).setTime(Integer.valueOf(value[2]));
                                mPlanList.get(position).setEndTime(Integer.valueOf(value[3]));
                                mPlanList.get(position).setPlanDetail(value[4]);
                                mPlanList.get(position).setStartPlace(value[5]);
                            }
                        }

                    }
                }
                for (int j = 1; j < 25; j++)
                    buw.readLine();

            }
            buw.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        int position = data.getIntExtra("position", -1);
        int start_Hour = data.getIntExtra("start_Hour", -1);
        int end_Hour = data.getIntExtra("end_Hour", -1);
        if (position == -1) {
            //mPlanList.add(new PlanListItem("", "", R.color.cardview_shadow_end_color, 9));
//                InitializeData();
//                InitializeAdapter();
//                NotifyAdaptter();
            setupRecyclerView(test);
            //NotifyAdaptter();
        } else {
            //텍스트는 수정된 상황, 추가말고
            FixedList(start_Hour, end_Hour, position);
            //NotifyAdaptter();
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
        return data;android:hardwareAccelerated="false" android:largeHeap="true"
    }*/


    @Override
    protected int getLayout() {
        return R.layout.fragment_tab;
    }


}
