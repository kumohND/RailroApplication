package kr.ac.kumoh.railroApplication.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    Bitmap bmImg;
    String data;
    String imageUrl = "http://tong.visitkorea.or.kr/cms/resource/35/1571735_image2_1.jpg";
    String queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey=IAWLeyO6k7e9XukxBm1HzSMs9IcPrz8jT9gpnefN4UxHGayHQb0fDMCuEMXjwocvlYEViBAPflKAlYqz16g%2Bmg%3D%3D&MobileOS=AND&MobileApp=AppTesting";


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

      //  downloadFile(imageUrl);


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
    void downloadFile(String fileUrl)
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
            HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            int length = conn.getContentLength(); // 받아온 컨텐츠의 길이를 length 변수에 저장합니다.
            InputStream is = conn.getInputStream(); // InputStream is 변수에 받아온 InputStream을 저장합니다.

            bmImg = BitmapFactory.decodeStream(is); // 받아온 이미지를 bmImg에 넣어둡니다.
            //imView.setImageBitmap(bmImg); // imView에 이미지를 셋팅합니다.
        }
        catch(IOException e) // 예외처리를 해줍니다.
        {
            e.printStackTrace();
        }
    }
    String getXmlData(){
        StringBuffer buffer = new StringBuffer();
        //한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..

        try {
            URL url= new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream();  //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );  //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기
                        // 첫번째 검색결과
                        if(tag.equals("addr1")){
                            buffer.append("주소 : ");
                            xpp.next();
                            buffer.append(xpp.getText()); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");
                        }     //줄바꿈 문자 추가
                        else if(tag.equals("title")) {
                            buffer.append("이름 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if(tag.equals("firstimage")){
                            imageUrl=xpp.getText();
                        }

                        break;

                    case XmlPullParser.TEXT:
                        //buffer.append(xpp.getText());
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기

                        if(tag.equals("title")) buffer.append("\n"); // 첫번째 검색결과종료..줄바꿈

                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buffer.toString(); //StringBuffer 문자열 객체 반환
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
