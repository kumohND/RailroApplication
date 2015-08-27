package kr.ac.kumoh.railroApplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kr.ac.kumoh.railroApplication.R;

/**
 * Created by sj on 2015-07-09.
 */
public class ListViewAdapter extends BaseAdapter {

    LayoutInflater inflater;

    public ListViewAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        View view = inflater.inflate(R.layout.mainlist, parent, false);
        holder = new ViewHolder(view);

        Picasso.with(inflater.getContext())
                .load("http://lorempixel.com/200/200/sports/" + (position+1))
                .into(holder.image);

        holder.text.setText("This is a text for the image number: "+position);

        return view;
    }

    static class ViewHolder{

        @InjectView(R.id.image_in_item)
        ImageView image;
        @InjectView(R.id.textview_in_item)
        TextView text;

        public ViewHolder(View view){
            ButterKnife.inject(this, view);
        }
    }
}
