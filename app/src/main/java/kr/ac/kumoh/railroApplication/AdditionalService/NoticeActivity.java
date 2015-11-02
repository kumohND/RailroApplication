package kr.ac.kumoh.railroApplication.AdditionalService;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;

/**
 * Created by sj on 2015-11-02.
 */
public class NoticeActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;


    TextView mNotice1;
    TextView mNotice1_1;
    TextView mNotice2;
    TextView mNotice2_1;

    boolean flag1 = false;
    boolean flag2 = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        ButterKnife.inject(this);
        setupToolbar();

        mNotice1 = (TextView) findViewById(R.id.notice1);
        mNotice1_1 = (TextView) findViewById(R.id.notice1_1);
        mNotice2 = (TextView) findViewById(R.id.notice2);
        mNotice2_1 = (TextView) findViewById(R.id.notice2_1);

        mNotice1.setText("기상청과 함께하는 내일이!");
        mNotice1_1.setText("오늘은 내일로를 떠나는 날!" + "\n" + "이럴수가! 비가 오다니..." + "\n" + "\n"
                + "계획을 짜실 때 날씨, 찾아 보셨나요?" + "\n"
                + "내일이는 계획을 작성할 때 바로바로 해당 지역의 날씨 정보를 제공해준답니다~" + "\n"
                + "바로 기상청에서 API를 친절하게 제공해주기 때문에 가능한 일이에요!" + "\n"
                + "뿐만 아니라 여행 중에도 실시간으로 날씨를 한 눈에 볼 수 있다니?!" + "\n"
                + "내일이와 함께 내일로 여행, 떠날 만 한가요?");
        mNotice2.setText("내일이 v1.0.0이 출시되었습니다!");
        mNotice2_1.setText("안녕하세요? 내일이 개발자 (주)ND입니다." + "\n" + "내일이는 지금 베타 테스트를 통해 안정화를 수행 중 입니다." + "\n"
                + "사용 중 오류나 문의사항 있으시면 고객센터 메뉴를 통해 메뉴를 보내주시길 바래요~" + "\n" + "\n"
                + "내일러들에게 더 좋은 앱을 만들어드리도록 노력할게요! :)");


        mNotice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag1) {
                    mNotice1_1.setVisibility(View.VISIBLE);
                    flag1 = true;
                } else {
                    mNotice1_1.setVisibility(View.GONE);
                    flag1 = false;
                }
            }
        });

        mNotice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag2) {
                    mNotice2_1.setVisibility(View.VISIBLE);
                    flag2 = true;
                } else {
                    mNotice2_1.setVisibility(View.GONE);
                    flag2 = false;
                }
            }
        });
    }

    private void setupToolbar() {
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if (mToolbar == null) {
            //  LOGD(this, "Didn't find a toolbar");
            return;
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setTitle("내일이 공지사항");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
