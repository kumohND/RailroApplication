package kr.ac.kumoh.railroApplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.classes.TripInfoListItem;

public class TripInfoListRVArrayAdapter extends RecyclerView.Adapter<TripInfoListRVArrayAdapter.ViewHolder> {


   /* private String[] mData;
    public TripListRVArrayAdapter(String[] data){
        mData = data;
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {
        //   CardView cv;
        TextView mTripName;
        TextView mTripLocale;
        ImageView mBackground;

        ImageView mStarImg1;
        ImageView mStarImg2;
        ImageView mStarImg3;
        ImageView mStarImg4;
        ImageView mStarImg5;


        //public TextView mTextView;
        /*
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }*/


        public ViewHolder(View itemView) {
            super(itemView);
            // cv = (CardView)itemView.findViewById(R.id.cv);
            mTripName = (TextView) itemView.findViewById(R.id.trip_title);
            mTripLocale = (TextView) itemView.findViewById(R.id.trip_locale);
            mBackground = (ImageView) itemView.findViewById(R.id.background_img);
            mBackground.setScaleType(ImageView.ScaleType.FIT_XY);

            mStarImg1 = (ImageView) itemView.findViewById(R.id.star_5);
            mStarImg2 = (ImageView) itemView.findViewById(R.id.star_4);
            mStarImg3 = (ImageView) itemView.findViewById(R.id.star_3);
            mStarImg4 = (ImageView) itemView.findViewById(R.id.star_2);
            mStarImg5 = (ImageView) itemView.findViewById(R.id.star_1);

/*
            switch (mStar) {
                case 1:
                    mStarImg = (ImageView) itemView.findViewById(R.id.star_5);
                    mStarImg.setImageResource(R.drawable.ic_android);
                case 2:
                    mStarImg = (ImageView) itemView.findViewById(R.id.star_4);
                    mStarImg.setImageResource(R.drawable.ic_android);
                case 3:
                    mStarImg = (ImageView) itemView.findViewById(R.id.star_3);
                    mStarImg.setImageResource(R.drawable.ic_android);
                case 4:
                    mStarImg = (ImageView) itemView.findViewById(R.id.star_2);
                    mStarImg.setImageResource(R.drawable.ic_android);
                case 5:
                    break;
            }
*/

            // mStarImg = (ImageView) itemView.findViewById(R.id.star_5);

            //mStarImg.setBackground(R.drawable.ic_phone);
        }
    }


    List<TripInfoListItem> mTripInfoList;

    public TripInfoListRVArrayAdapter(List<TripInfoListItem> mTripInfoList) {
        this.mTripInfoList = mTripInfoList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        //    View view = LayoutInflater
        //           .from(viewGroup.getContext())
        //           .inflate(android.R.layout.simple_list_item_1,viewGroup,false);

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_tripinfo, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //  viewHolder.mTextView.setText(mData[position]);
        viewHolder.mBackground.setImageResource(mTripInfoList.get(position).getTripImg());
        viewHolder.mTripName.setText(mTripInfoList.get(position).getTripTitle());
        viewHolder.mTripLocale.setText(mTripInfoList.get(position).getTripLocale());
        //viewHolder.mStar = mTripInfoList.get(position).getmStar();
        switch (mTripInfoList.get(position).getmStar()) {
            case 5:
                viewHolder.mStarImg1.setImageResource(R.drawable.ic_star_white);
            case 4:
                viewHolder.mStarImg2.setImageResource(R.drawable.ic_star_white);
            case 3:
                viewHolder.mStarImg3.setImageResource(R.drawable.ic_star_white);
            case 2:
                viewHolder.mStarImg4.setImageResource(R.drawable.ic_star_white);
            case 1:
                viewHolder.mStarImg5.setImageResource(R.drawable.ic_star_white);
                break;
            case 0:
                break;
        }

    }


    @Override
    public int getItemCount() {
        //return mData.length;
        return mTripInfoList.size();
    }

}
