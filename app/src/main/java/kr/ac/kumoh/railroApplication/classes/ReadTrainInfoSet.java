package kr.ac.kumoh.railroApplication.classes;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

import kr.ac.kumoh.railroApplication.R;

/**
 * Created by sj on 2015-08-13.
 */
public class ReadTrainInfoSet extends Activity {
    String station_name;
    String station_code;
    Context mContext;
    String name_buffer;
    String code_buffer;

    AssetManager mag;

    ArrayList<StationInfo> mList;
    TrainListAdpater mAdapter;


    TextView tv;
    TextView tv2;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_read_layout);
        mList = new ArrayList<StationInfo>();

        mContext = this.getApplicationContext();
        mag = mContext.getResources().getAssets();


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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringToToken(name_buffer, code_buffer);

        lv = (ListView) findViewById(R.id.station_list);
        mAdapter = new TrainListAdpater(this, android.R.layout.simple_list_item_1, 1, mList);
        lv.setAdapter(mAdapter);

    }


    void StringToToken(String name, String code) {
        StringTokenizer mToken = new StringTokenizer(name, "\r\n");
        StringTokenizer mToken2 = new StringTokenizer(code, "\r\n");

        while (mToken.hasMoreTokens()) {
            String mName = mToken.nextToken();
            String mCode = mToken2.nextToken();

            StationInfo temp = new StationInfo(mName + "역", mCode);
            mList.add(temp);


        }


    }


    public class TrainListAdpater extends ArrayAdapter<StationInfo> implements View.OnClickListener {

        Context mContext;
        private ArrayList<StationInfo> items;


        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();

            StationInfo temp = items.get(position);
            onTexStationNameWriting(temp.getStationName(), temp.getStationCode());

            ReadTrainInfoSet.this.finish();
        }

        private void onTexStationNameWriting(String name, String code) { // 임시로, 기차역 출발 , 도착역 씀
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

        public TrainListAdpater(Context context, int resource, int textViewResourceId, ArrayList<StationInfo> objects) {
            super(context, resource, textViewResourceId, objects);
            this.mContext = context;
            this.items = objects;

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
    }


}
