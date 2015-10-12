package kr.ac.kumoh.railroApplication.classes;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

import kr.ac.kumoh.railroApplication.R;

/**
 * Created by sj on 2015-08-13.
 */
public class ReadTrainInfoSetActivity extends Activity {
    String station_name;
    String station_code;
    Context mContext;
    String name_buffer;
    String code_buffer;
    private static final char HANGUL_BEGIN_UNICODE = 44032; // 가
    private static final char HANGUL_LAST_UNICODE = 55203; // 힣
    private static final char HANGUL_BASE_UNIT = 588;//각자음 마다 가지는 글자수
    //자음
    private static final char[] INITIAL_SOUND = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };
    AssetManager mag;
    String index[] = {"ㄱ","ㄴ","ㄷ","ㄹ","ㅁ","ㅂ","ㅅ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
    ArrayList<StationInfo> mList;
    TrainListAdpater mAdapter;
    short flag_onClick = 0;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(flag_onClick != 1 )
         onTexStationNameWriting("nothing","nothing");
    }

    ListView lv;
    EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_read_layout);
        mList = new ArrayList<StationInfo>();

        mContext = this.getApplicationContext();
        mag = mContext.getResources().getAssets();
        search = (EditText)findViewById(R.id.search_station);
        search.addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = search.getText().toString();
                mAdapter.Filter(temp);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }));
        try { // getAssets 저장된 텍스트 데이터 두개 불러옴
            InputStream name = mag.open("InfoTrainStation.txt");
            InputStream code = mag.open("InfoTrainStationCode.txt");

            int nameSize = name.available();
            int codeSize = code.available();

            byte[] buffer = new byte[nameSize];
            byte[] buffer2 = new byte[codeSize];

            name.read(buffer);
            code.read(buffer2);

            name.close();
            code.close();

            name_buffer = new String(buffer);
            code_buffer = new String(buffer2);
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        StringToToken(name_buffer,code_buffer);

        lv = (ListView)findViewById(R.id.station_list);



        mAdapter = new TrainListAdpater(mContext,android.R.layout.simple_list_item_1, 1,mList);
        lv.setFastScrollEnabled(true);
        lv.setAdapter(mAdapter);
    }




    void StringToToken(String name,String code)
    {
        StringTokenizer mToken = new StringTokenizer(name, "\r\n");
        StringTokenizer mToken2 = new StringTokenizer(code, "\r\n");

        while(mToken.hasMoreTokens() )
        {
            String mName = mToken.nextToken();
            String mCode = mToken2.nextToken();

            StationInfo temp = new StationInfo(mName+"역",mCode);
            mList.add(temp);


        }


    }

    private void onTexStationNameWriting(String name,String code) { // 임시로, 기차역 출발 , 도착역 씀
        File file;
        String path = "/data/data/kr.ac.kumoh.railroApplication/files/datasheet.ext";
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + File.separator + "temp" + ".txt");
        try {
            BufferedWriter buw = new BufferedWriter(new FileWriter(file));


            buw.write(name);
            buw.newLine();
            buw.write(code);
            buw.close();
        } catch (IOException e) {

        }
    }
    public class TrainListAdpater extends ArrayAdapter<StationInfo> implements View.OnClickListener, SectionIndexer
    {

        Context mContext;
        private ArrayList<StationInfo> items;
        private ArrayList<StationInfo> temp_items;
        private AlphabetIndexer mIndexer;
        Cursor cursor;

        void Sort_String()
        {
            final Comparator<StationInfo> myComparator= new Comparator<StationInfo>() {
                private final Collator collator = Collator.getInstance();
                @Override
                public int compare(StationInfo object1,StationInfo object2) {
                    return collator.compare(object1.getStationName(), object2.getStationName());
                }
            };
            // Collections.sort 로 comparator 를 주어서 데이터를 정렬 시킨다.
            Collections.sort(items, myComparator);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            final int set = position;
            ArrayList<StationInfo> temp = new ArrayList<StationInfo>();

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.train_read_list_item, null);
            }
            v.setTag(position);
            v.setOnClickListener(this);
            final StationInfo p = items.get(position);
            if (p != null) {
                TextView tv = (TextView) v.findViewById(R.id.station_name);

                if (tv != null) {
                    tv.setText(p.getStationName());
                }
            }

            return v;
        }
        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();

            StationInfo temp = items.get(position);
            onTexStationNameWriting(temp.getStationName(),temp.getStationCode());

            ReadTrainInfoSetActivity.this.finish();
        }


        public TrainListAdpater(Context context, int resource, int textViewResourceId, ArrayList<StationInfo> objects) {
            super(context, resource, textViewResourceId, objects);
            this.mContext = context;
            this.items = objects;
            temp_items = new ArrayList<StationInfo>();
            temp_items.addAll(items);
            Sort_String();

        }

        public void Filter(String data)
        {

            if(data.length() == 0) items.addAll(temp_items);

            items.clear();
            for(StationInfo temp : temp_items)
            {

                if(matchString(temp.getStationName(),data))
                    items.add(temp);
            }
            Sort_String();
            notifyDataSetChanged();
        }

        public  boolean matchString(String value, String search){
            int t = 0;
            int seof = value.length() - search.length();
            int slen = search.length();
            if(seof < 0)
                return false; //검색어가 더 길면 false를 리턴한다.
            for(int i = 0;i <= seof;i++){
                t = 0;
                while(t < slen){
                    if(isInitialSound(search.charAt(t))==true && isHangul(value.charAt(i+t))){
                        //만약 현재 char이 초성이고 value가 한글이면
                        if(getInitialSound(value.charAt(i+t))==search.charAt(t))
                            //각각의 초성끼리 같은지 비교한다
                            t++;
                        else
                            break;
                    } else {
                        //char이 초성이 아니라면
                        if(value.charAt(i+t)==search.charAt(t))
                            //그냥 같은지 비교한다.
                            t++;
                        else
                            break;
                    }
                }
                if(t == slen)
                    return true; //모두 일치한 결과를 찾으면 true를 리턴한다.
            }
            return false; //일치하는 것을 찾지 못했으면 false를 리턴한다.
        }

        private  boolean isHangul(char c) {
            return HANGUL_BEGIN_UNICODE <= c && c <= HANGUL_LAST_UNICODE;
        }

        private  char getInitialSound(char c) {
            int hanBegin = (c - HANGUL_BEGIN_UNICODE);
            int index = hanBegin / HANGUL_BASE_UNIT;
            return INITIAL_SOUND[index];
        }

        private  boolean isInitialSound(char searchar){
            for(char c:INITIAL_SOUND){
                if(c == searchar){
                    return true;
                }
            }
            return false;
        }

        String val[] = {"ㄱ","ㄴ","ㄷ","ㄹ","ㅁ","ㅂ","ㅅ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
        @Override
        public Object[] getSections() {
//            String[] chars = new String[SideSelector.ALPHABET.length];
//            for (int i = 0; i < SideSelector.ALPHABET.length; i++) {
//                chars[i] = String.valueOf(SideSelector.ALPHABET[i]);
//            }

            return val;
        }

        @Override
        public int getPositionForSection(int i) {

            //Log.d(TAG, "getPositionForSection " + i);
            return (int) (getCount() * ((float)i/(float)getSections().length));
//            return (int) (items.size() * ((float)i/(float)getSections().length));
        }


        @Override
        public int getSectionForPosition(int position) {
            return 0;
        }
    }

}
