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
import android.widget.TimePicker;
import android.widget.Toast;

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
import kr.ac.kumoh.railroApplication.classes.LatLng;
import kr.ac.kumoh.railroApplication.classes.LocationChangeLonLat;
import kr.ac.kumoh.railroApplication.classes.LocationInform;
import kr.ac.kumoh.railroApplication.classes.ReadTrainInfoSetActivity;
import kr.ac.kumoh.railroApplication.classes.StationInfo;
import kr.ac.kumoh.railroApplication.classes.WebViewActivity;

/**
 * Created by sj on 2015-07-30.
 */
public class SetTripPlanActivity extends ActionBarActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    @InjectView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    Spinner mSpinner;

    Intent mIntent;

    private ArrayAdapter<String> cate;
    int selected;

    private ArrayList<String> station;

    LocationChangeLonLat mLocationData;
    //Context mContext;
    LinearLayout moveTrain;
    LinearLayout moveBus;
    LinearLayout toMeal;
    LinearLayout sleep;

    Button doSomething;
    Button sTimeFix;
    Button eTimeFix;

    Button sLocation;
    Button eLocation;
    Button wSleep;
    Button wEat;

    Button movingTime;
    Button movingTime2;
    Button sStation;
    Button eStation;
    Button plan_Success;
    LocationInform mStartInform;
    LocationInform mEndInform;
    StationInfo data_startStation;
    StationInfo data_endStation;

    int flag_StartStation = 0;
    int flag_EndStation = 0;

    String default_sTime = "출발 시간:";
    String default_eTime = "도착 시간:";

    String default_sStation = "출발역:";
    String default_eStation = "도착역:";

    String default_toDo = "할 일:";
    String default_moveValue = "이동 시간:";

    String TokenForLocation(String val)
    {
        StringTokenizer token = new StringTokenizer(val,":");
        token.nextToken();
        String value = token.nextToken();
        return value;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_plan_list);
        ButterKnife.inject(this);
        setupToolbar();



        moveTrain = (LinearLayout)findViewById(R.id.MoveToTrain);
        moveBus = (LinearLayout)findViewById(R.id.MoveTobus);
        toMeal = (LinearLayout)findViewById(R.id.ToMeal);
        sleep = (LinearLayout)findViewById(R.id.Sleep);

        sTimeFix = (Button)findViewById(R.id.fix_start_Time);
        eTimeFix = (Button)findViewById(R.id.fix_end_Time);
        doSomething = (Button)findViewById(R.id.tv_DoSomething);



        movingTime = (Button)findViewById(R.id.btn_moving_time);
        movingTime2 = (Button)findViewById(R.id.btn_moving_time2);

        sStation = (Button)findViewById(R.id.set_start_station);
        eStation = (Button)findViewById(R.id.set_end_station);

        plan_Success = (Button)findViewById(R.id.btn_success_plan);

        sLocation = (Button)findViewById(R.id.set_start_location);
        eLocation = (Button)findViewById(R.id.set_end_location);
        wSleep = (Button)findViewById(R.id.btn_Sleep_location);
        wEat = (Button)findViewById(R.id.btn_toMeal_location);

        sStation.setOnClickListener(this);
        eStation.setOnClickListener(this);
        plan_Success.setOnClickListener(this);
//        plan_Cancel.setOnClickListener(this);
        doSomething.setOnClickListener(this);
        sTimeFix.setOnClickListener(this);
        wEat.setOnClickListener(this);
        wSleep.setOnClickListener(this);
        eLocation.setOnClickListener(this);
        sLocation.setOnClickListener(this);
        movingTime.setOnClickListener(this);
        movingTime2.setOnClickListener(this);


        sStation.setAlpha(200);
        eStation.setAlpha(200);
        plan_Success.setAlpha(200);
        doSomething.setAlpha(200);
        sTimeFix.setAlpha(200);
        wEat.setAlpha(200);
        wSleep.setAlpha(200);
        eLocation.setAlpha(200);
        sLocation.setAlpha(200);
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
        Intent intent ;
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
                intent = new Intent(this, ReadTrainInfoSetActivity.class);
                startActivityForResult(intent, 1);
                flag_StartStation = 1;
                break;

            case R.id.set_end_station: // 끝나는 기차역 조회
                intent = new Intent(this, ReadTrainInfoSetActivity.class);
                startActivityForResult(intent, 2);
                flag_EndStation = 1;
                break;

            case R.id.btn_success_plan: // 저장 버튼 누를시 실행
                onPlanOkay();
                //TODO : 정보 여기로 넘겨줄 수 있도록 하기
              /*  mIntent = new Intent();
                mIntent.putExtra("Result","SUCCESS");
                mIntent.putExtra("setTime",22);
                mIntent.putExtra("setImage",R.drawable.ic_email);
                mIntent.putExtra("setRate", 100);
                mIntent.putExtra("setTitle", "위치정보");
                mIntent.putExtra("setCategory","한식");
                setResult(RESULT_OK, mIntent);
                finish();*/

                break;
            case R.id.btn_moving_time:
                    intent = InputData(1);
                    if(intent == null){
                        Toast.makeText(this,"주소 오류",Toast.LENGTH_SHORT);
                        break;
                    }
                    startActivity(intent);

                break;
            case R.id.btn_moving_time2:
                intent = InputData(2);
                if(intent == null){
                    Toast.makeText(this,"주소 오류",Toast.LENGTH_SHORT);
                    break;
                }
                startActivity(intent);
                break;
        }
    }

    public Intent InputData(int index)
    {

        mLocationData = new LocationChangeLonLat();
        if(index == 1 ) {
            mLocationData.initControl(TokenForLocation(String.valueOf(sStation.getText())),
                    TokenForLocation(String.valueOf(eStation.getText())),this );
        }else{
            mLocationData.initControl(TokenForLocation(String.valueOf(sLocation.getText())),
                    TokenForLocation(String.valueOf(sLocation.getText())),this );
        }
        mStartInform = mLocationData.getStartLocationLonLat();
        mEndInform =  mLocationData.getEndLocationLonLat();

        if(mStartInform != null && mEndInform != null) {

            Intent i = new Intent(this, WebViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("start", mStartInform);
            bundle.putParcelable("end", mEndInform);
            bundle.putString("url", getUrl(mStartInform.latlng, mEndInform.latlng));
            i.putExtras(bundle);
            return i;
        }
        return null;
    }
    public String cutTail(double number, int n){//소수점  n자리 밑으로 자르기
        String cuttedNumber = Double.toString(number);
        int idxPoint = cuttedNumber.indexOf(".");
        cuttedNumber = cuttedNumber.substring(0, idxPoint + n);

        return cuttedNumber;
    }
    public String getUrl(LatLng start, LatLng end){
        StringBuilder urlString = new StringBuilder();

        urlString.append("https://www.google.com:443/maps/dir/");

        int n = 6;

        //from
        //urlString.append("&saddr=");
        urlString.append(cutTail(start.getLat(), n));
        urlString.append(",");
        urlString.append(cutTail(start.getLng(), n));

        //to
        urlString.append("/");
        urlString.append(cutTail(end.getLat(), n));
        urlString.append(",");
        urlString.append(cutTail(end.getLng(), n));


        return urlString.toString();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // moveFab();
    }


    BufferedWriter buf;

    private void onPlanOkay() {
        String path = "/data/data/kr.ac.kumoh.railrotravel/files/datasheet.ext"; // 저장 할 곳
        File file;
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + File.separator + "201508065" + ".txt"); // 여기서 텍스트 이름만 수정하면 될듯
        try{

            int check = selected;

            buf = new BufferedWriter(new FileWriter(file));

            buf.write(String.valueOf(check));  buf.newLine();
            // 구분 번호
            buf.write((String)sTimeFix.getText()); buf.newLine();
            buf.write((String)eTimeFix.getText());   buf.newLine();
            // Time Save //
            buf.write((String)doSomething.getText()); buf.newLine();
            // Do Something //
            // ------------------ 공통 부분은 여기에 저장 ----------------- //
            if(check == 0) // train
            {
                buf.write((String) sStation.getText()); buf.newLine();
                buf.write(data_startStation.getStationCode()); buf.newLine();
                buf.write((String) eStation.getText()); buf.newLine();
                buf.write(data_endStation.getStationCode()); buf.newLine();
                buf.write((String) movingTime.getText());  buf.newLine();
            }else if(check ==1) // bus
            {
                buf.write((String) sLocation.getText()); buf.newLine();
                buf.write((String) eLocation.getText()); ; buf.newLine();
                buf.write((String) movingTime2.getText());
            }else if(check ==2){ // sleep
                buf.write((String) wSleep.getText());
            }else{ //toMeal
                buf.write((String) wEat.getText());
            }



            buf.close();

        }catch(IOException e)
        {

        }
    }

    BufferedReader buw;
    int readCheck = -1; // 껏다 켯을때, spin위치 체크

    private void onReadDetail() {
        String path = "/data/data/kr.ac.kumoh.railrotravel/files/datasheet.ext";
        File file;
        file = new File(path);
        ArrayList<String> mData;
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + File.separator + "201508065" + ".txt");
        try{

            int check;

            buw = new BufferedReader(new FileReader(file));
            String rawData = "";
            String temp;
            while((temp = buw.readLine()) != null)
            {
                rawData += (temp + "\r\n");
            }

            buw.close();
            if(rawData.equals("")) return ;

            mData = InitDataStringTokenizer(rawData); // text 데이터 -> ArrayList로 만들어 반환

            readCheck = Integer.valueOf(mData.get(0));
            check = Integer.valueOf(mData.get(0));
            // 구분 번호 //
            sTimeFix.setText(default_sTime + mData.get(1));
            eTimeFix.setText(default_eTime + mData.get(2));

            // Time Save //
            doSomething.setText(default_toDo + mData.get(3));

            // Do Something //


            // 보여줄 데이터 셋
            if(check == 0) // train
            {
                data_startStation = new StationInfo(mData.get(4),mData.get(5));
                sStation.setText(default_sStation + mData.get(4));
                data_endStation = new StationInfo(mData.get(6),mData.get(7));
                eStation.setText(default_eStation + mData.get(6));
                movingTime.setText(mData.get(7) + default_moveValue );
            }else if(check ==1) // bus
            {
                sLocation.setText(mData.get(4));
                eLocation.setText(mData.get(5));
                movingTime.setText(mData.get(6) + default_moveValue);
            }else if(check ==2){ // sleep
                wSleep.setText(mData.get(4));
            }else{ //toMeal
                wEat.setText(mData.get(4));
            }
        }catch(IOException e)
        {

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
        //mCast = new ForeCast();
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
            named_buffer += "\r\n" + buw.readLine();
        }catch(IOException e)
        {

        }

        if(check == 1) //startStation
        {

            flag_StartStation = 0 ;
            data_startStation = StringToToken(named_buffer);
            if(data_startStation == null){
                sStation.setText(default_sStation + "서울역");
                return ;
            }
            sStation.setText(default_sStation + data_startStation.getStationName());
        }else if(check == 2) // endStation
        {
            flag_EndStation = 0;
            data_endStation = StringToToken(named_buffer);
            if(data_endStation == null){
                eStation.setText(default_eStation + "부산역");
                return ;
            }
            eStation.setText(default_eStation + data_endStation.getStationName());
        }
    }



    StationInfo StringToToken(String rawString) {
        if(rawString.contains("nothing")){


           return null;
        }
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
            //startTime.setText(hourOfDay + minute);
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
