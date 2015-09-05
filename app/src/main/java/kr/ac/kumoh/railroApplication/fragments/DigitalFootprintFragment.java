package kr.ac.kumoh.railroApplication.fragments;

import android.app.Dialog;
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
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.classes.BeaconMacAddressInfo;
import kr.ac.kumoh.railroApplication.util.AnimUtils;

public class DigitalFootprintFragment extends BaseFragment implements View.OnClickListener {


    @InjectView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @InjectView(R.id.share_menu_item)
    FloatingActionButton mFab;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    private static final int REQUEST_ENABLE_BT = 2;
    private ArrayList<String> mArrayAdapter;

    private BluetoothAdapter mBluetoothAdapter;
    Context mContext;
    private static final long SCAN_PERIOD = 10000;
    //Button scan_blue;

    TextView test;

    ArrayList<BeaconMacAddressInfo> bData;
    BeaconMacAddressInfo red;
    BeaconMacAddressInfo yellow;
    BeaconMacAddressInfo green;
    ImageAdapter adapter;
    GridView g;

    Dialog mProgress;


    String seoul = "C8:E2:88:D8:60:9F"; // red
    String jeonju = "FE:84:D5:15:AE:69"; // yellow
    String donghae = "F7:B3:43:E2:FE:9A"; // green

    private void bluetoothSetup() {
        // Initialize
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }


    }

    void AddtoBeaconStationData() {
        bData = new ArrayList<BeaconMacAddressInfo>();
        red = new BeaconMacAddressInfo(seoul, "Seoul Station", R.drawable.un_seoul, R.drawable.seoul);
        yellow = new BeaconMacAddressInfo(jeonju, "Jeonju Station", R.drawable.un_jeonju, R.drawable.jeonju);
        green = new BeaconMacAddressInfo(donghae, "Donghae Station", R.drawable.un_donghae, R.drawable.donghae);
        bData.add(red);
        bData.add(yellow);
        bData.add(green);
    }
    // Register the BroadcastReceiver


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public static DigitalFootprintFragment newInstance() {
        return new DigitalFootprintFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Snackbar.make(mCoordinatorLayout, "오른쪽 하단의 블루투스 검색을 해주세요!", Snackbar.LENGTH_LONG).show();
        moveFab();
    }

    View.OnClickListener snackbarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            bluetoothSetup();

            if (!mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.startDiscovery();

            }


            mProgress = new Dialog(getActivity(), R.style.MyProgressDialog);
            mProgress.setCancelable(true);
            mProgress.addContentView(new ProgressBar(getActivity()), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mProgress.show();

            //TODO : 우찬 시간 내에 못찾으면 실패 Dialog 띄우기

            final BroadcastReceiver mReceiver = new BroadcastReceiver() {

                public void onReceive(Context context, Intent intent) {

                    String action = intent.getAction();

                    // When discovery finds a device

                    if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                        // Get the BluetoothDevice object from the Intent
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                        // Add the name and address to an array adapter to show in a ListView
                        mArrayAdapter.add(device.getName() + "n" + device.getAddress() + "(RSSI: " + rssi + "dBM)");

                        if (rssi <= -35) {
                            for (int i = 0; i < bData.size(); i++) {
                                if (device.getAddress().equals(bData.get(i).getMac_Address())) {
                                    mProgress.cancel();
                                    test.setText(bData.get(i).getSetStation());
                                    bData.get(i).FlagOn();
                                    adapter.notifyDataSetChanged();
                                    return;
                                }
                            }

                        }
                    }
                }
            };

            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            getActivity().registerReceiver(mReceiver, filter); // Don�셳 forget to unregister during onDestroy

        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View v = view;
        g = (GridView) v.findViewById(R.id.station_grid_list);
        mArrayAdapter = new ArrayList<String>();
        mContext = v.getContext();
        AddtoBeaconStationData();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mCoordinatorLayout, "블루투스를 검색합니다!", Snackbar.LENGTH_LONG)
                        .setAction("검색 시작", snackbarClickListener).show();

            }
        });

        test = (TextView) v.findViewById(R.id.check_ble);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(mContext, "블루투스가 작동하지 않습니다!", Toast.LENGTH_SHORT);
        }
        adapter = new ImageAdapter(mContext);
        g.setAdapter(adapter);
        g.setOnItemClickListener(mMessageClicked);

    }

    private void moveFab() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float deltaY = mFab.getHeight() * 1.5f;
                if (i < 0) animFab(deltaY);
                else animFab(-deltaY);
            }
        });
    }


    private void animFab(final float deltaY) {
        ViewCompat.animate(mFab)
                .translationYBy(deltaY)
                .setInterpolator(AnimUtils.FAST_OUT_SLOW_IN_INTERPOLATOR)
                .withLayer()
                .start();
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

        public ImageAdapter(Context mContext) {
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

            DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
            //  int rowWidth = metrics.widthPixels;
            int rowWidth = metrics.heightPixels / 4;


            View v = convertView;
            if (v == null) {
                image = new ImageView(mContext);
               /* image.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));*/

                image.setLayoutParams(new GridView.LayoutParams(rowWidth, rowWidth));
                image.setAdjustViewBounds(false);
                image.setPadding(1, 1, 1, 1);
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);

            } else {
                image = (ImageView) v;
            }
            image.setImageResource(bData.get(position).getPicture());
            return image;
        }
    }
}
