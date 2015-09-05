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
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.classes.BeaconMacAddressInfo;
import kr.ac.kumoh.railroApplication.util.AnimUtils;

public class DigitalFootprintFragment extends BaseFragment implements View.OnClickListener{

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
    ImageAdapter adapter;
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
    // Register the BroadcastReceiver
    @InjectView(R.id.simpleList)
    RecyclerView mRecyclerView;

    @InjectView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @InjectView(R.id.share_menu_item)
    FloatingActionButton mFab;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

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
//                final ProgressDialog mProgressDialog;
//                mProgressDialog = new ProgressDialog(mContext);

                final kr.ac.kumoh.railroApplication.classes.ProgressDialog mProgressDialog = new kr.ac.kumoh.railroApplication.classes.ProgressDialog(mContext);
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
    public static DigitalFootprintFragment newInstance() {
        return new DigitalFootprintFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setList();
        moveFab();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        AddtoBeaconStationData();
//        View v = (View)inflater.inflate(R.layout.beacone_list, container, false);


        View v = view;
        g = (GridView)v.findViewById(R.id.station_grid_list);
         mArrayAdapter = new ArrayList<String>();
        mContext = v.getContext();
        AddtoBeaconStationData();
//
//
        scan_blue = (Button)v.findViewById(R.id.scan_ble);
        scan_blue.setOnClickListener(this);
        test = (TextView)v.findViewById(R.id.check_ble);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(mContext, "Bluetooth not Working", Toast.LENGTH_SHORT);
            // Device does not support Bluetooth
//TODO: hhhhhh
        }
        adapter = new ImageAdapter(mContext);
        g.setAdapter(adapter);
        g.setOnItemClickListener(mMessageClicked);
//        return v;
    }

    private void moveFab(){
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float deltaY = mFab.getHeight()*1.5f;
                if(i <0)animFab(deltaY);
                else animFab(-deltaY);
            }
        });
    }



    private void animFab(final float deltaY){
        ViewCompat.animate(mFab)
                .translationYBy(deltaY)
                .setInterpolator(AnimUtils.FAST_OUT_SLOW_IN_INTERPOLATOR)
                .withLayer()
                .start();
    }

    private void setList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

       // TripListRVArrayAdapter arrayAdapter = new TripListRVArrayAdapter(getData());
      //  mRecyclerView.setAdapter(arrayAdapter);
    }


    @Override
    protected int getTitle() {
        return R.string.digital_footprint;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_digital_footprint;
    }


    public String[] getData() {
        return getActivity().getResources().getStringArray(R.array.countries);
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
