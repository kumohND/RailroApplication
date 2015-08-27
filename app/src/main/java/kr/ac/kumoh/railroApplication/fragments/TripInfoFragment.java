package kr.ac.kumoh.railroApplication.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.adapters.TripInfoListRVArrayAdapter;
import kr.ac.kumoh.railroApplication.classes.TripInfoListItem;
import kr.ac.kumoh.railroApplication.widget.RecyclerClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TripInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TripInfoFragment extends BaseFragment {

    private static final int AMOUNT_OF_DATA = 5;
    private View rootView;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TripInfoFragment.
     */

    private List<TripInfoListItem> mTripInfoList;
    private RecyclerView recyclerView;
    private Bitmap mBitmap;

    public static TripInfoFragment newInstance() {
        return new TripInfoFragment();
    }

    public TripInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(R.layout.mainlist, container, false);

        return super.onCreateView(inflater, container, savedInstanceState);
        // return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setList();
    }

    private void setList() {
 /*   RecyclerView recyclerView = ButterKnife.findById(getActivity(),R.id.simpleList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
*/
        //  TripListRVArrayAdapter arrayAdapter = new TripListRVArrayAdapter(getData());
        //  recyclerView.setAdapter(arrayAdapter);

        recyclerView = ButterKnife.findById(getActivity(), R.id.simpleList);
        recyclerView.addOnItemTouchListener(new RecyclerClickListener(getActivity(), new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 지연 : 여기서 누르면 다음 화면으로 가도록 하면되용!
                Toast.makeText(getActivity(), "Position " + position, Toast.LENGTH_SHORT).show();
            }
        }));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        InitializeData();
        InitializeAdapter();

    }

    private void InitializeData() {
        mTripInfoList = new ArrayList<>();

   /*
        지연: 여기에 불러오는 코드 그대로 넣으면 될듯!
        InputStream is;
        mBitmap = BitmapFactory.decodeStream();
        //Bitmap to Drawable
        BitmapDrawable bitmapDrawable = (BitmapDrawable)bitmap;
        Drawable drawable = (Drawable)bitmapDrawable;*/

        mTripInfoList.add(new TripInfoListItem(R.drawable.fantastic, "전주 명동성당", "전주 전주동 전주주", 5));
        mTripInfoList.add(new TripInfoListItem(R.drawable.plunge, "홍익대학교", "서울시 마포구 와우산로", 4));
        mTripInfoList.add(new TripInfoListItem(R.drawable.desert, "사막", "서울시 마포구 와우산로", 3));
        mTripInfoList.add(new TripInfoListItem(R.drawable.fantastic, "벽", "서울시 마포구 와우산로", 1));

    }

    private void InitializeAdapter() {
        TripInfoListRVArrayAdapter arrayAdapter = new TripInfoListRVArrayAdapter(mTripInfoList);
        recyclerView.setAdapter(arrayAdapter);
    }

    @NonNull
    private String[] getData() {
        String[] data = new String[AMOUNT_OF_DATA];
        for (int i = 0; i < AMOUNT_OF_DATA; ++i) {
            data[i] = (getString(R.string.sample_data, (i + 1)));
        }
        return data;
    }

    @Override
    protected int getTitle() {
        return R.string.plan_info;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.standard_app_bar_fragment;
    }
}
