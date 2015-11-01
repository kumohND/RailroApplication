package kr.ac.kumoh.railroApplication.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import kr.ac.kumoh.railroApplication.R;

/**
 * Created by Jongmin on 2015-10-27.
 */


public class PrintPlaceinfoActivity extends Activity {

    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    private Button rPTRANSIT;
    private Button rCAR;
    private Button rWALK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.placeinfo_main);

        ImageView img = (ImageView)findViewById(R.id.placeImage);

        mListView = (ListView)findViewById(R.id.placeInfo);
        mAdapter = new ListViewAdapter(this);

        mListView.setAdapter(mAdapter);

        rPTRANSIT = (Button)findViewById(R.id.showRoutePTRANSIT);
        rPTRANSIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("daummaps://route?sp=" + Double.toString(getIntent().getExtras().getDouble("cLatitude")) + ","
                        + Double.toString(getIntent().getExtras().getDouble("cLongitude"))
                        + "&ep=" + Double.toString(getIntent().getExtras().getDouble("bLatitude"))
                        + Double.toString(getIntent().getExtras().getDouble("bLongitude")) + "&by=PUBLICTRANSIT"));
                startActivity(intent);
            }
        });

        rCAR = (Button)findViewById(R.id.showRouteCAR);
        rCAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("daummaps://route?sp=" + Double.toString(getIntent().getExtras().getDouble("cLatitude")) + ","
                        + Double.toString(getIntent().getExtras().getDouble("cLongitude"))
                        + "&ep=" + Double.toString(getIntent().getExtras().getDouble("bLatitude"))
                        + Double.toString(getIntent().getExtras().getDouble("bLongitude")) + "&by=CAR"));
                startActivity(intent);
            }
        });

        rWALK = (Button)findViewById(R.id.showRouteWALK);
        rWALK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("daummaps://route?sp=" + Double.toString(getIntent().getExtras().getDouble("cLatitude")) + ","
                        + Double.toString(getIntent().getExtras().getDouble("cLongitude"))
                        + "&ep=" + Double.toString(getIntent().getExtras().getDouble("bLatitude"))
                        + Double.toString(getIntent().getExtras().getDouble("bLongitude")) + "&by=FOOT"));
                startActivity(intent);
            }
        });

        img.setImageDrawable(getDrawable(R.drawable.ic_android));

        if(getIntent().getExtras().getString("ImageURL") != "") {
            new DownloadImageTask((ImageView) findViewById(R.id.placeImage))
                    .execute(getIntent().getExtras().getString("ImageURL"));
        }

        mAdapter.addItem("분류", getIntent().getExtras().getString("Category"));
        mAdapter.addItem("장소명", getIntent().getExtras().getString("Title"));
        mAdapter.addItem("주소", getIntent().getExtras().getString("Address"));
        mAdapter.addItem("전화번호", getIntent().getExtras().getString("Phone"));

    }

    private class ContentHolder {
        public TextView mTitle;
        public TextView mContent;
    }

    private class ImageHolder {
        public ImageView mImage;
    }

    private class ListViewAdapter extends BaseAdapter {

        private Context mContext = null;
        private ArrayList<PlaceinfoDataList> mListData = new ArrayList<PlaceinfoDataList>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int i) {
            return mListData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ContentHolder ch;
            if(view == null) {
                ch = new ContentHolder();

                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.placeinfo_item, null);

                ch.mTitle = (TextView)view.findViewById(R.id.infoTitle);
                ch.mContent = (TextView)view.findViewById(R.id.infoContent);

                view.setTag(ch);
            } else {
                ch = (ContentHolder)view.getTag();
            }

            PlaceinfoDataList mData = mListData.get(i);

            ch.mTitle.setText(mData.mTitle);
            ch.mContent.setText(mData.mContent);

            return view;
        }

        public void addItem(String mTitle, String mContent) {
            PlaceinfoDataList addInfo = null;
            addInfo = new PlaceinfoDataList();

            addInfo.mTitle = mTitle;
            addInfo.mContent = mContent;

            mListData.add(addInfo);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
