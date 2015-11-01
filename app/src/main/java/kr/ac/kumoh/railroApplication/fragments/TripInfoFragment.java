package kr.ac.kumoh.railroApplication.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.adapters.TripInfoListRVArrayAdapter;
import kr.ac.kumoh.railroApplication.classes.TripInfoFragInfo;
import kr.ac.kumoh.railroApplication.classes.TripInfoListItem;
import kr.ac.kumoh.railroApplication.fragments.tabs.PlanListTabActivity;
import kr.ac.kumoh.railroApplication.fragments.tabs.TripInfoActivity;
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

    private ArrayList<TripInfoListItem> mTripInfoList;
    private ArrayList<TripInfoFragInfo> mTripInfoFragList;
    private RecyclerView recyclerView;
    private Bitmap mBitmap;

    TextView text;

    Bitmap bmImg;
    BitmapDrawable draw1;
    String data;

    String addr1 ;
    String addr2  ;
    int areacode;
    int contenttypeid;
    int contentid;
    String image;
    double mapx;
    double mapy;
    String title_info;
    String zipcode;
    int index = 1;
    int cnt = 0;

    String title = null;

    public static TripInfoFragment newInstance() {
        return new TripInfoFragment();
    }

    public TripInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (View) inflater.inflate(R.layout.mainlist, container, false);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());

        //setList();
        return super.onCreateView(inflater, container, savedInstanceState);
        // return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  getXmlData();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //리스트 뷰
        setList();

       // text = (TextView)ButterKnife.findById(getActivity(), R.id.TextViewid);

    }

    //리스트 뷰 구성
    private void setList() {

        recyclerView = ButterKnife.findById(getActivity(), R.id.simpleList);
        recyclerView.addOnItemTouchListener(new RecyclerClickListener(getActivity(), new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 리스트 눌러서 액티비티 띄우는 부분
               //Toast.makeText(getActivity(), "Position " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), TripInfoActivity.class);
                intent.putExtra("id",mTripInfoFragList.get(position).getContentId());
                intent.putExtra("star",mTripInfoList.get(position).getmStar());
               intent.putExtra("title",mTripInfoFragList.get(position).getTitle().toString());
                startActivity(intent);
            }
        }));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        InitializeData();
        InitializeAdapter();
    }

    //리스트 정보
    private void InitializeData() {
        mTripInfoList = new ArrayList<>();
        mTripInfoFragList = new ArrayList<>();
        getXmlData();
        String url;

        BitmapDrawable temp=(BitmapDrawable)getResources().getDrawable(R.drawable.ic_android);
        Drawable drawable;

        int Size = mTripInfoFragList.size();
        while(mTripInfoFragList.size()!=cnt){

            drawable = downloadFile(mTripInfoFragList.get(cnt).getImage());

            //Log.d("d", mTripInfoFragList.get(cnt).getImage());

            mTripInfoList.add(new TripInfoListItem(bmImg,
                    mTripInfoFragList.get(cnt).getTitle(),
                    mTripInfoFragList.get(cnt).getAddr1(), 5));
            bmImg = temp.getBitmap();
            cnt++;
        }
    }

    //XML주소에서 태그별로 데이터추출
    void getXmlData(){

        //xml주소 에서 관광지 정보를 추출
        String queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey=IAWLeyO6k7e9XukxBm1HzSMs9IcPrz8jT9gpnefN4UxHGayHQb0fDMCuEMXjwocvlYEViBAPflKAlYqz16g%2Bmg%3D%3D&contentTypeId=12&MobileOS=AND&MobileApp=AppTesting";
        StringBuffer buffer = new StringBuffer();

        try {
            URL url= new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream();  //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );  //inputstream 으로부터 xml 입력받기

            String tag;

            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){

                switch( eventType ){

                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:  //태그별로 안의 정보를 얻어온다.
                        tag= xpp.getName();    //테그 이름 얻어오기
                        if(tag.equals("addr1")) {
                            xpp.next();
                            addr1 = xpp.getText();
                        }
                        else if(tag.equals("addr2")){
                            xpp.next();//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            addr2 = xpp.getText();
                        }
                        else if(tag.equals("areacode")){
                            xpp.next();
                            areacode =Integer.parseInt(xpp.getText());
                        }
                        else if(tag.equals("contenttypeid")){
                            xpp.next();
                            contenttypeid = Integer.parseInt(xpp.getText());
                        }
                        else if(tag.equals("contentid")){
                            xpp.next();
                            contentid = Integer.parseInt(xpp.getText());
                        }
                        else if(tag.equals("firstimage")){
                            xpp.next();
                            image = xpp.getText();
                            //image = null;
                        }
                        else if(tag.equals("mapx")){
                            xpp.next();
                            mapx = Double.parseDouble(xpp.getText());
                        }
                        else if(tag.equals("mapy")){
                            xpp.next();
                            mapy = Double.parseDouble(xpp.getText());
                        }
                        else if(tag.equals("title")){
                            xpp.next();
                            title_info = xpp.getText();
                        }
                        else if(tag.equals("zipcode")){
                            xpp.next();
                            zipcode = xpp.getText();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기

                        if(tag.equals("item")) {
                            mTripInfoFragList.add(new TripInfoFragInfo(addr1, addr2, areacode, contenttypeid, contentid, image, mapx, mapy, title_info, zipcode, index, 0));
                            //Log.d("d", "!!!!!!!!!!!!11");
                            image = "";
                            index += 1;
                        }
                        break;
                }
                eventType= xpp.next();

            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //html 주소 string을 받아 해당 주소의 이미지를 화면에 띄움
    BitmapDrawable downloadFile(String fileUrl)
    {
        URL myFileUrl = null; // URL 타입의 myFileUrl을  NULL로 초기화 시켜줍니다.

        try
        {
            myFileUrl = new URL(fileUrl); //  파라미터로 넘어온 Url을 myFileUrl에 대입합니다.
        }
        catch(MalformedURLException e) // 예외처리를 해줍니다.
        {
            // Todo Auto-generated catch block
            e.printStackTrace();
        }
        try
        {
            // 실질적인 통신이 이루어지는 부분입니다.
            // myFileUrl 로 접속을 시도합니다.
            if(fileUrl.equals("")) return null;

            HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            int length = conn.getContentLength(); // 받아온 컨텐츠의 길이를 length 변수에 저장합니다.
            InputStream is = conn.getInputStream(); // InputStream is 변수에 받아온 InputStream을 저장합니다.

            bmImg = BitmapFactory.decodeStream(is); // 받아온 이미지를 bmImg에 넣어둡니다.
        }
        catch(IOException e) // 예외처리를 해줍니다.
        {
            e.printStackTrace();
        }

        draw1 = new BitmapDrawable(bmImg);

        return draw1;
    }

    public Drawable getDrawableFromBitmap(Bitmap bitmap){
        Drawable d = new BitmapDrawable(bitmap);
        return d;
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
