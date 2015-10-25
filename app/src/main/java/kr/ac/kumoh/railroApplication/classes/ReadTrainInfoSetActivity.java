package kr.ac.kumoh.railroApplication.classes;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AlphabetIndexer;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.HashMap;
import java.util.StringTokenizer;

import kr.ac.kumoh.railroApplication.R;

/**
 * Created by sj on 2015-08-13.
 */
public class ReadTrainInfoSetActivity extends Activity {
    //TODO : Handler 사용 해야함
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
    TextView head;


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

        head = (TextView) findViewById(R.id.head_Section);



        mAdapter = new TrainListAdpater(mContext,android.R.layout.simple_list_item_1, 1,mList);
        lv.setFastScrollEnabled(true);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//                int section = mAdapter.getSectionForPosition(i);
                String title = "ㄱ";
                for(int string_Check = 0; string_Check < mAdapter.index_Value.length - 1; string_Check++)
                {
                    if((mAdapter.index_Value[string_Check] <= i &&
                            mAdapter.index_Value[string_Check+1] > i) ){
                         title = (String)mAdapter.index_String[string_Check];
                    }else if(mAdapter.index_Value[string_Check+1] < i )
                    {
                        title = (String)mAdapter.index_String[string_Check+1];
                    }

                }
;
                head.setText(title);
            }
        });
        lv.setAdapter(mAdapter);
    }
//    private void bindSectionHeader(int position) {
//        final int section = mAdapter.getSectionForPosition(position);
//        String title = (String)mAdapter.index_String[section];
//        head.setText(title);
//    }



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
        boolean setting_Flag = false;
        Context mContext;
        private ArrayList<StationInfo> items;
        private ArrayList<StationInfo> temp_items;
        HashMap<String, Integer> alphaIndexer;
        LinearLayout header;
        TextView head;
        Cursor cursor;
        String val[] = {"ㄱ","ㄴ","ㄷ","ㄹ","ㅁ","ㅂ","ㅅ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
        String index_String[] = {"ㄱ","ㄴ","ㄷ","ㅁ","ㅂ","ㅅ","ㅇ","ㅈ","ㅊ","ㅌ","ㅍ","ㅎ",};
        int index_Value[] =     {0,    87,   114,  177, 216, 263,  372, 505,  565,  597, 607, 619};
        final int set = 0 ;
        View main_View;
        public void IndexorSetting()
        {
            alphaIndexer = new HashMap<String,Integer>();
            // value -> ㄱ 이면 Hash값 으로 int값 몇을 반환
//            for(int i = 0; i < val.length; i ++) {
//                for (int j = 0; j < items.size(); j++) {
//                    if (matchFirstString(items.get(j).getStationName(), val[i])) {
//                        alphaIndexer.put(val[i], j);
//                        break;
//                    }
//                }
//            }

            for(int i = 0 ; i < index_String.length; i++)
            {
                alphaIndexer.put(index_String[i],index_Value[i]);
            }
        }

//        private void bindSectionHeader(int position) {
//            final int section = getSectionForPosition(position);
//
//            if (getPositionForSection(section) == position) {
//                String title = (String)index_String[section];
//                head.setText(title);
//                header.setVisibility(View.VISIBLE);
//            } else {
//                header.setVisibility(View.GONE);
//            }
//        }
//        private void bindSectionHeader(int position) {
//            final int section = getSectionForPosition(position);
//                String title = (String)index_String[section];
//                head.setText(title);
//
//        }
        public void bindChangedSectionHeader(int indexScroll)
        {
            for(int i = 0 ; i < index_Value.length; i++) {
                if (indexScroll-5 < index_Value[i] && indexScroll+5 < index_Value[i])
                {
                    String title = (String)index_String[i];
                    head.setText(title);
                    header.setVisibility(View.VISIBLE);
                }else {
                    header.setVisibility(View.GONE);
                }
            }
        }
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
            parent.invalidate();
            View v = convertView;

            final int set = position;
            ArrayList<StationInfo> temp = new ArrayList<StationInfo>();
            if(setting_Flag == false) {
                IndexorSetting();
                setting_Flag = true;
            }
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
            //bindSectionHeader(set);
            return v;
        }
        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();

            StationInfo temp = items.get(position);
            onTexStationNameWriting(temp.getStationName(), temp.getStationCode());

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
        public  boolean matchFirstString(String value, String search){
            int t = 0;

                    if(isInitialSound(search.charAt(t))==true && isHangul(value.charAt(t))){
                        //만약 현재 char이 초성이고 value가 한글이면
                        if(getInitialSound(value.charAt(t))==search.charAt(t))
                            return true;
                        else
                            return false;
                    }
                    return false;
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


        @Override
        public Object[] getSections() {
//            String[] chars = new String[SideSelector.ALPHABET.length];
//            for (int i = 0; i < SideSelector.ALPHABET.length; i++) {
//                chars[i] = String.valueOf(SideSelector.ALPHABET[i]);
//            }

            return index_String;
        }
        @Override
        public int getPositionForSection(int i) {
            //Log.d(TAG, "getPositionForSection " + i);

            //return i;
//            int test = getCount();
////            return (int) (getCount() * ((float)i/(float)getSections().length));
//            for(int temp_Index = 0; i < this.getCount(); temp_Index++)
//            {
//                String temp_item = items.get(temp_Index).getStationName();
//                if(matchString(temp_item,val[i])) {
//                    return temp_Index;
//                }
//            }
//
//            return i;
//            return (int) (getCount() * ((float)i/(float)items.size()));
//            return (int) (items.size() * ((float)i/(float)getSections().length));
           // String test = val[i];
            //bindSectionHeader(alphaIndexer.get(index_String[i]));
            //bindChangedSectionHeader(alphaIndexer.get(index_String[i]));
            Log.i("index_Value", "" + alphaIndexer.get(index_String[i]));
            return alphaIndexer.get(index_String[i]);
        }


        @Override
        public int getSectionForPosition(int position) {
            return 0;
        }
    }

}
