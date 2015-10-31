package kr.ac.kumoh.railroApplication.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.classes.TripListItem;
import kr.ac.kumoh.railroApplication.classes.UseDB;

public class TripListRVArrayAdapter extends RecyclerView.Adapter<TripListRVArrayAdapter.ViewHolder> implements ItemTouchHelperAdapter {


   /* private String[] mData;
    public TripListRVArrayAdapter(String[] data){
        mData = data;
    }*/
    Context mContext;
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView mTripName;
        TextView mTripDate;
        ImageView mIcon;

        //public TextView mTextView;
        /*
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }*/

        public ViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            mTripName = (TextView)itemView.findViewById(R.id.trip_name);
            mTripDate = (TextView)itemView.findViewById(R.id.trip_date);
            mIcon = (ImageView)itemView.findViewById(R.id.icon);
        }
    }

    List<TripListItem> mTripList;

    public TripListRVArrayAdapter(List<TripListItem> mTripList){
        this.mTripList = mTripList;
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

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
        mContext = view.getContext();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
      //  viewHolder.mTextView.setText(mData[position]);
        viewHolder.mTripName.setText(mTripList.get(position).getTripTitle());
        viewHolder.mTripDate.setText(mTripList.get(position).getTripDate());
        viewHolder.mIcon.setImageResource(mTripList.get(position).getIconId());
    }


    @Override
    public int getItemCount() {
        //return mData.length;
        return mTripList.size();
    }

    @Override
    public void onItemDismiss(int position) {
        UseDB mDB = new UseDB(mContext);
        mDB.Delete(mTripList.get(position).getmDB_Position());
        mTripList.remove(position);

        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mTripList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mTripList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

}
