package kr.ac.kumoh.railroApplication.fragments.tabs;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.classes.ReadTrainInfoSet;
import kr.ac.kumoh.railroApplication.classes.StationInfo;

/**
 * Created by sj on 2015-07-30.
 */
public class SetTripPlanActivity extends ActionBarActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    @InjectView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    Spinner mSpinner;

    private ArrayAdapter<String> cate;
    int selected;

    private ArrayList<String> station;

    //Context mContext;
    LinearLayout moveTrain;
    LinearLayout moveBus;
    LinearLayout toMeal;
    LinearLayout sleep;

    Button sTimeFix;
    Button eTimeFix;
    TextView startTime;
    TextView endTime;
    TextView startStation;
    TextView endStation;
    TextView doSomething;
    TextView movingTime;
    TextView movingTime2;
    TextView startLocation;
    TextView endLocation;

    TextView whereSleep;
    TextView wheretoEat;

    Button sStation;
    Button eStation;

    Button plan_Success;
    Button plan_Cancel;

    StationInfo data_startStation;
    StationInfo data_endStation;

    int flag_StartStation = 0;
    int flag_EndStation = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_plan_list);
        ButterKnife.inject(this);
        setupToolbar();

        //  TextInputLayout lNameLayout = (TextInputLayout)findViewById(R.id.lNameLayout);
        // lNameLayout.setErrorEnabled(true);
     /*   Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/

        moveTrain = (LinearLayout) findViewById(R.id.MoveToTrain);
        moveBus = (LinearLayout) findViewById(R.id.MoveTobus);
        toMeal = (LinearLayout) findViewById(R.id.ToMeal);
        sleep = (LinearLayout) findViewById(R.id.Sleep);

        sTimeFix = (Button) findViewById(R.id.fix_start_Time);
        eTimeFix = (Button) findViewById(R.id.fix_end_Time);
        startTime = (TextView) findViewById(R.id.schedule_start_time);
        endTime = (TextView) findViewById(R.id.schedule_end_time);
        doSomething = (TextView) findViewById(R.id.tv_DoSomething);

        startStation = (TextView) findViewById(R.id.tv_start_station);
        endStation = (TextView) findViewById(R.id.tv_end_station);

        startLocation = (TextView) findViewById(R.id.tv_start_location);
        endLocation = (TextView) findViewById(R.id.tv_end_location);

        whereSleep = (TextView) findViewById(R.id.tv_Sleep_Location);
        wheretoEat = (TextView) findViewById(R.id.tv_toMeal_Location);

        movingTime = (TextView) findViewById(R.id.tv_moving_time);
        movingTime2 = (TextView) findViewById(R.id.tv_moving_time2);

        sStation = (Button) findViewById(R.id.set_start_station);
        eStation = (Button) findViewById(R.id.set_end_station);

        plan_Success = (Button) findViewById(R.id.btn_success_plan);
        plan_Cancel = (Button) findViewById(R.id.btn_cancel_plan);

        sStation.setOnClickListener(this);
        eStation.setOnClickListener(this);
        plan_Success.setOnClickListener(this);
        plan_Cancel.setOnClickListener(this);

        sTimeFix.setOnClickListener(this);


        //mContext = getContext();


        onReadDetail(); // 초기, text data 존재하나 확인

        mSpinner = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);


    }

    private void setupToolbar() {
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if (mToolbar == null) {
            //  LOGD(this, "Didn't find a toolbar");
            return;
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setTitle("여행 설정");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.fix_start_Time: // STARTtIME만 설정
                TimePickerDialog dialog = new TimePickerDialog(this, listener, 00, 00, false);
                dialog.show();
                break;
            case R.id.fix_end_Time: // STARTtIME만 설정
                TimePickerDialog dialog2 = new TimePickerDialog(this, listener, 00, 00, false);
                dialog2.show();
                break;
            case R.id.set_start_station: // 시작하는 기차역 조회
                intent = new Intent(this, ReadTrainInfoSet.class);
                startActivityForResult(intent, 1);
                flag_StartStation = 1;
                break;

            case R.id.set_end_station: // 끝나는 기차역 조회
                intent = new Intent(this, ReadTrainInfoSet.class);
                startActivityForResult(intent, 2);
                flag_EndStation = 1;
                break;

            case R.id.btn_success_plan: // 저장 버튼 누를시 실행
                onPlanOkay();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // moveFab();
    }


    BufferedWriter buf;

    private void onPlanOkay() {
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext"; // 저장 할 곳
        File file;
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + File.separator + "201508065" + ".txt"); // 여기서 텍스트 이름만 수정하면 될듯
        try {

            int check = selected;
            // BufferedWriter buw = new BufferedWriter(new FileReader(file,true));
//            plan_startTime = Integer.getInteger((String)startTime.getText());
//            plan_endTime = Integer.getInteger((String)endTime.getText());


            buf = new BufferedWriter(new FileWriter(file));

            buf.write(check);
            buf.newLine();
            // 구분 번호
            buf.write((String) startTime.getText());
            buf.newLine();
            buf.write((String) endTime.getText());
            buf.newLine();
            // Time Save //
            buf.write((String) doSomething.getText());
            buf.newLine();
            // Do Something //
            // ------------------ 공통 부분은 여기에 저장 ----------------- //
            if (check == 0) // train
            {
                buf.write((String) startStation.getText());
                buf.newLine();
                buf.write(data_startStation.getStationCode());
                buf.newLine();
                buf.write((String) endStation.getText());
                buf.newLine();
                buf.write(data_endStation.getStationCode());
                buf.newLine();
                buf.write((String) movingTime.getText());
                buf.newLine();
            } else if (check == 1) // bus
            {
                buf.write((String) startLocation.getText());
                buf.newLine();
                buf.write((String) endLocation.getText());
                ;
                buf.newLine();
                buf.write((String) movingTime2.getText());
            } else if (check == 2) { // sleep
                buf.write((String) whereSleep.getText());
            } else { //toMeal
                buf.write((String) wheretoEat.getText());
            }


            buf.close();

        } catch (IOException e) {

        }
    }

    BufferedReader buw;
    int readCheck = -1; // 껏다 켯을때, spin위치 체크

    private void onReadDetail() {
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
        File file;
        file = new File(path);
        ArrayList<String> mData;
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + File.separator + "201508065" + ".txt");
        try {

            int check;

            buw = new BufferedReader(new FileReader(file));
            String rawData = "";
            String temp;
            while ((temp = buw.readLine()) != null) {
                rawData += (temp + "\r\n");
            }

            buw.close();
            if (rawData.equals("")) return;

            mData = InitDataStringTokenizer(rawData); // text 데이터 -> ArrayList로 만들어 반환

            readCheck = Integer.valueOf(mData.get(0));
            check = Integer.valueOf(mData.get(0));
            // 구분 번호 //
            startTime.setText(mData.get(1));
            endTime.setText(mData.get(2));

            // Time Save //
            doSomething.setText(mData.get(3));

            // Do Something //


            // 보여줄 데이터 셋
            if (check == 0) // train
            {
                data_startStation = new StationInfo(mData.get(4), mData.get(5));
                startStation.setText(mData.get(4));
                data_endStation = new StationInfo(mData.get(6), mData.get(7));
                endStation.setText(mData.get(6));
                movingTime.setText(mData.get(7));
            } else if (check == 1) // bus
            {
                startLocation.setText(mData.get(4));
                endLocation.setText(mData.get(5));
                movingTime.setText(mData.get(6));
            } else if (check == 2) { // sleep
                whereSleep.setText(mData.get(4));
            } else { //toMeal
                wheretoEat.setText(mData.get(4));
            }
        } catch (IOException e) {

        }
    }

    //토큰 분리
    private ArrayList<String> InitDataStringTokenizer(String rawString) {

        StringTokenizer mToken = new StringTokenizer(rawString, "\r\n");
        ArrayList<String> mData = new ArrayList<String>();
        while (mToken.hasMoreTokens())
            mData.add(mToken.nextToken());


        return mData;

    }

    String named_buffer;

    // 기차역 조회
    private void onTestReadAndSet(int check) {
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
        try {
            File file;
            file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path + File.separator + "temp" + ".txt");
            BufferedReader buw = new BufferedReader(new FileReader(file));

            named_buffer = buw.readLine();
            named_buffer += "\r\n" + buw.readLine();
        } catch (IOException e) {

        }

        if (check == 1) //startStation
        {
            data_startStation = StringToToken(named_buffer);
            startStation.setText(data_startStation.getStationName());
            flag_StartStation = 0;
        } else if (check == 2) // endStation
        {
            data_endStation = StringToToken(named_buffer);
            endStation.setText(data_endStation.getStationName());
            flag_EndStation = 0;
        }
    }


    StationInfo StringToToken(String rawString) {
        StringTokenizer mToken = new StringTokenizer(rawString, "\r\n");
        StationInfo temp;

        String mName = mToken.nextToken();
        String mCode = mToken.nextToken();
        temp = new StationInfo(mName, mCode);
        return temp;
    }


    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            startTime.setText(hourOfDay + minute);
        }
    };


    @Override // 기차역 조회 Activity 끝나고 실행되는 함수 onStart
    public void onStart() {
        super.onStart();
        if (flag_StartStation == 1) // 시작
        {
            onTestReadAndSet(1);
        }
        if (flag_EndStation == 1) // 종료
        {
            onTestReadAndSet(2);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        if(readCheck != -1)
        {
            if(position == 0) // Train
            {
                selected = 0;
                moveTrain.setVisibility(View.VISIBLE);
                moveBus.setVisibility(View.GONE);
                toMeal.setVisibility(View.GONE);
                sleep.setVisibility(View.GONE);
            }else if(position == 1) // Bus
            {
                selected = 1;
                moveTrain.setVisibility(View.GONE);
                moveBus.setVisibility(View.VISIBLE);
                toMeal.setVisibility(View.GONE);
                sleep.setVisibility(View.GONE);
            }else if(position == 2) // Sleep
            {
                selected = 2;
                moveTrain.setVisibility(View.GONE);
                moveBus.setVisibility(View.GONE);
                toMeal.setVisibility(View.GONE);
                sleep.setVisibility(View.VISIBLE);
            }else // toMeal
            {
                selected = 3;
                moveTrain.setVisibility(View.GONE);
                moveBus.setVisibility(View.GONE);
                toMeal.setVisibility(View.VISIBLE);
                sleep.setVisibility(View.GONE);
            }
            readCheck = -1;
            return ;
        }

        selected = position;

        if(position == 0) // Train
        {
            moveTrain.setVisibility(View.VISIBLE);
            moveBus.setVisibility(View.GONE);
            toMeal.setVisibility(View.GONE);
            sleep.setVisibility(View.GONE);


        }else if(position == 1) // Bus
        {
            moveTrain.setVisibility(View.GONE);
            moveBus.setVisibility(View.VISIBLE);
            toMeal.setVisibility(View.GONE);
            sleep.setVisibility(View.GONE);
        }else if(position == 2) // Sleep
        {
            moveTrain.setVisibility(View.GONE);
            moveBus.setVisibility(View.GONE);
            toMeal.setVisibility(View.GONE);
            sleep.setVisibility(View.VISIBLE);
        }else // toMeal
        {
            moveTrain.setVisibility(View.GONE);
            moveBus.setVisibility(View.GONE);
            toMeal.setVisibility(View.VISIBLE);
            sleep.setVisibility(View.GONE);
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
