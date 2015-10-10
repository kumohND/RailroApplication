package kr.ac.kumoh.railroApplication.fragments;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, View.OnTouchListener {

    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    @InjectView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @InjectView(R.id.share_menu_item)
    FloatingActionButton mFloatingButton;

    ViewFlipper viewFlipper;


    // 터치 이벤트 발생 지점의 x좌표 저장
    float xAtDown;
    float xAtUp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ParallaxFragment.
     */
    Button mButton;
    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCollapsingToolbar.setTitle(getString(getTitle()));

        int color = getResources().getColor(android.R.color.transparent);
        mCoordinatorLayout.setStatusBarBackgroundColor(color);

        mButton = ButterKnife.findById(getActivity(), R.id.date_picker_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // new DatePickerDialog(getActivity(), dpickerListener, year_x, month_x, day_x).show();
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        HomeFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        viewFlipper = ButterKnife.findById(getActivity(), R.id.viewFlipper);
        viewFlipper.setOnTouchListener(this);

        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    ;


    @Override
    protected int getToolbarId() {
        return R.id.toolbar_flexible_space_with_image;
    }

    @Override
    protected int getTitle() {
        return R.string.home;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth + "를 선택하셨습니다";
        // dateTextView.setText(date);
        Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
        //TODO: DB에 날짜 저장해야함
        mButton.setText((monthOfYear + 1) + "/" + dayOfMonth);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        // 터치 이벤트가 일어난 뷰가 ViewFlipper가 아니면 return

        if (v != viewFlipper)
            return false;


        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            xAtDown = event.getX(); // 터치 시작지점 x좌표 저장

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            xAtUp = event.getX(); // 터치 끝난지점 x좌표 저장

            if (xAtUp < xAtDown) {
                // 왼쪽 방향 에니메이션 지정
                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_out));

                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_out));

                // 다음 view 보여줌
                viewFlipper.showNext();

            } else if (xAtUp > xAtDown) {

                // 오른쪽 방향 에니메이션 지정
                viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in));
                viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in));

                // 전 view 보여줌
                viewFlipper.showPrevious();
            }
        }
        return true;
    }
}
