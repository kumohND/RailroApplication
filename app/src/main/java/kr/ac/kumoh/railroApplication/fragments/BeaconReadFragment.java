package kr.ac.kumoh.railroApplication.fragments;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.classes.BeaconMacAddressInfo;

/**
 * Created by Woocha on 2015-09-02.
 */
// 기기있는곳으로 ㄱㄱ -> 검색중이라는 다이얼로그, -> 검색 완료 -> 그림 바뀜
// 기기의 device mac 아이디 조회, 존재하면 다음 단계로 진행, 문제가 되는 것은 범위
public class BeaconReadFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected int getLayout() {
        return 0;
    }

    public static BeaconReadFragment newInstance() {
        BeaconReadFragment fragment = new BeaconReadFragment();
        return fragment;
    }

    private static final int REQUEST_ENABLE_BT = 2;
    private ArrayList<String> mArrayAdapter;

    private BluetoothAdapter mBluetoothAdapter;
    Context mContext;
    private static final long SCAN_PERIOD = 10000;
    Button scan_blue;
    TextView test;

    ArrayList<BeaconMacAddressInfo> bData;
    BeaconMacAddressInfo red;
    BeaconMacAddressInfo yellow;
    BeaconMacAddressInfo green;

    GridView g;


    String seoul = "C8:E2:88:D8:60:9F"; // red
    String jeonju = "FE:84:D5:15:AE:69"; // yellow
    String donghae = "F7:B3:43:E2:FE:9A"; // green

    private void bluetoothSetup() {
        // Initialize
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }


    } //꺼져있을경우 확인 창 나타냄


    // Register the BroadcastReceiver


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.scan_ble:
                bluetoothSetup();

                if(! mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.startDiscovery();
                    // 프로그래스바가 뜨면 좋을거 같군.
                }
                // 검색하는거 같은디
                // Create a BroadcastReceiver for ACTION_FOUND
                final ProgressDialog mProgressDialog;
                mProgressDialog = new ProgressDialog(mContext);

// 위에서 테두리를 둥글게 했지만 다이얼로그 자체가 네모라 사각형 여백이 보입니다. 아래 코드로 다이얼로그 배경을 투명처리합니다.
                mProgressDialog .getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                mProgressDialog.show(); // 보여주기

                final BroadcastReceiver mReceiver = new BroadcastReceiver() {

                    public void onReceive(Context context, Intent intent) {

                        String action = intent.getAction();

                        // When discovery finds a device

                        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                            // Get the BluetoothDevice object from the Intent
                            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,  Short.MIN_VALUE);
                            // Add the name and address to an array adapter to show in a ListView
                            mArrayAdapter.add(device.getName() + "n" + device.getAddress()+ "(RSSI: " + rssi + "dBM)");

                            if(rssi <= -35)
                            {
                                for(int i = 0; i < bData.size(); i++) {
                                    if (device.getAddress().equals(bData.get(i).getMac_Address())) {
                                        mProgressDialog.dismiss(); // 없애기
                                        test.setText(bData.get(i).getSetStation());
                                        bData.get(i).FlagOn();
                                        adapter.notifyDataSetChanged();
                                        return ;
                                    }
                                }

                            }
                        }
                    }
                };

                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                getActivity().registerReceiver(mReceiver, filter); // Don’t forget to unregister during onDestroy

                break;
        }
    }



    void AddtoBeaconStationData()
    {
        bData = new ArrayList<BeaconMacAddressInfo>();
        red = new BeaconMacAddressInfo(seoul,"Seoul Station",R.drawable.un_seoul,R.drawable.seoul);
        yellow = new BeaconMacAddressInfo(jeonju,"Jeonju Station",R.drawable.un_jeonju,R.drawable.jeonju);
        green = new BeaconMacAddressInfo(donghae,"Donghae Station",R.drawable.un_donghae,R.drawable.donghae);
        bData.add(red);
        bData.add(yellow);
        bData.add(green);
    }
    ImageAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = (View)inflater.inflate(R.layout.beacon_list, container, false);
        AddtoBeaconStationData();
        mArrayAdapter = new ArrayList<String>();
        mContext = v.getContext();
        g = (GridView)v.findViewById(R.id.station_grid_list);


        scan_blue = (Button)v.findViewById(R.id.scan_ble);
        scan_blue.setOnClickListener(this);
        test = (TextView)v.findViewById(R.id.check_ble);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(mContext, "Bluetooth not Working", Toast.LENGTH_SHORT);
            // Device does not support Bluetooth

        }
        adapter = new ImageAdapter(mContext);
        g.setAdapter(adapter);
        g.setOnItemClickListener(mMessageClicked);
        return v;
    }

    private final AdapterView.OnItemClickListener mMessageClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };


    public class ImageAdapter extends BaseAdapter {

        Context mContext;
        public ImageAdapter(Context mContext){
            this.mContext = mContext;
        }


        @Override
        public int getCount() {
            return bData.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView image;

            View v = convertView;
            if(v == null)
            {
                image = new ImageView(mContext);
                image.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                image.setAdjustViewBounds(false);
                image.setScaleType(ImageView.ScaleType.FIT_XY);

            }else{
                image = (ImageView)v;
            }
            image.setImageResource(bData.get(position).getPicture());
            return image;
        }
    }



}

