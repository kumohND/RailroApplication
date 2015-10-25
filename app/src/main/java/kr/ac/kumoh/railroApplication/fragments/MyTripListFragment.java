package kr.ac.kumoh.railroApplication.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.adapters.TripListRVArrayAdapter;
import kr.ac.kumoh.railroApplication.fragments.tabs.PlanListTabActivity;
import kr.ac.kumoh.railroApplication.fragments.tabs.PlanListTabFragment;
import kr.ac.kumoh.railroApplication.fragments.tabs.SearchPlaceActivity;
import kr.ac.kumoh.railroApplication.fragments.tabs.SetTripPlanActivity;
import kr.ac.kumoh.railroApplication.widget.RecyclerClickListener;
import kr.ac.kumoh.railroApplication.classes.TripListItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyTripListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTripListFragment extends BaseFragment {

    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PinnedAppBarkFragment.
     */

    private List<TripListItem> mTripList;
    private RecyclerView recyclerView;

    public static MyTripListFragment newInstance() {
        return new MyTripListFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCollapsingToolbar.setTitle(getString(getTitle()));
        setList();
    }

    public MyTripListFragment() {
        // Required empty public constructor
    }

    private void setList() {

        recyclerView = ButterKnife.findById(getActivity(), R.id.simpleList);
        recyclerView.addOnItemTouchListener(new RecyclerClickListener(getActivity(), new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // do whatever
                Toast.makeText(getActivity(), "I'm Clicked~~", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PlanListTabActivity.class);
                startActivity(intent);
            }
        }));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //  TripListRVArrayAdapter arrayAdapter = new TripListRVArrayAdapter(getData());
        // recyclerView.setAdapter(arrayAdapter);

        InitializeData();
        InitializeAdapter();

    }

    private void InitializeData() {
        mTripList = new ArrayList<>();
        mTripList.add(new TripListItem("맛집 내일로 여행", "2015/07/25 ~ 2015/08/01", R.drawable.ic_android));
        mTripList.add(new TripListItem("사진 여행", "2015/08/11 ~ 2015/08/15", R.drawable.ic_android));
    }

    private void InitializeAdapter() {
        TripListRVArrayAdapter arrayAdapter = new TripListRVArrayAdapter(mTripList);
        recyclerView.setAdapter(arrayAdapter);
    }

    @Override
    protected int getTitle() {
        return R.string.my_plan_list;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar_flexible_space;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_my_trip_list;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
