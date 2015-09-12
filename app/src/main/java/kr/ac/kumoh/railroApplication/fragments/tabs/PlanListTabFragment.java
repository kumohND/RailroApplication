package kr.ac.kumoh.railroApplication.fragments.tabs;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.adapters.PagerAdapter;
import kr.ac.kumoh.railroApplication.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanListTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanListTabFragment extends BaseFragment {

    @InjectView(R.id.tab_layout)
    TabLayout mTabLayout;

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;

/*    @InjectView(R.id.share_menu_item)
    FloatingActionButton mFab;

    @InjectView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;*/

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TabFragment.
     */
    public static PlanListTabFragment newInstance() {
        return new PlanListTabFragment();
    }

    public PlanListTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root =  super.onCreateView(inflater, container, savedInstanceState);

     /*   mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mCoordinatorLayout, "블루투스를 검색합니다!", Snackbar.LENGTH_LONG).show();

            }
        });*/
        setupTabTextColor();
        setupViewPager();
        return root;
    }

    View.OnClickListener snackbarClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getActivity(), SetTripPlanActivity.class);
            //TODO : 선정 나중에 StartActivityForResult해서 값 받아서 세팅
            startActivity(intent);
        }
    };

    private void setupTabTextColor() {
        int tabTextColor = getResources().getColor(R.color.titleTextColor);
        mTabLayout.setTabTextColors(tabTextColor, tabTextColor);
    }

    private void setupViewPager() {
        //You could use the normal supportFragmentManger if you like
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), getActivity());
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);//this is the new nice thing ;D
    }
/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected int getToolbarId() {
        return R.id.toolbar_tabbar;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    protected int getTitle() {
        return R.string.plan_list;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_plan_list_tab;
    }


}
