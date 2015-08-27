package kr.ac.kumoh.railroApplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kr.ac.kumoh.railroApplication.R;
import kr.ac.kumoh.railroApplication.classes.PlanListItem;

public class PlanListRVArrayAdapter extends RecyclerView.Adapter<PlanListRVArrayAdapter.ViewHolder> {
/*
    private @DrawableRes int[] mData;

    public PlanListRVArrayAdapter(@DrawableRes int[] data){
        mData = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.music_card, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.mImageView.getContext();
        Picasso.with(context)
                .load(mData[position])
                .resizeDimen(R.dimen.image_width,R.dimen.image_height)
                .centerCrop()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = ButterKnife.findById(itemView, R.id.imageView);
        }
    }
    */

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mPlanTitle;
        TextView mPlanDetail;
        TextView mTime;
        int mTagColor;
        TextView mAMPM;

        public ViewHolder(View itemView) {
            super(itemView);
            mPlanTitle = (TextView) itemView.findViewById(R.id.plan_title);
            mPlanDetail = (TextView) itemView.findViewById(R.id.plan_detail);
            mTime = (TextView) itemView.findViewById(R.id.time_text);
            mTagColor = R.color.titleTextColor;
            mAMPM = (TextView) itemView.findViewById(R.id.am_pm);
        }
    }

    List<PlanListItem> mPlanList;

    public PlanListRVArrayAdapter(List<PlanListItem> mPlanList) {
        this.mPlanList = mPlanList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mPlanTitle.setText(mPlanList.get(position).getPlanTitle());
        viewHolder.mPlanDetail.setText(mPlanList.get(position).getPlanDetail());

        int mSetTime = mPlanList.get(position).getTime();
        if( mSetTime>0 && mSetTime<10 ){
            viewHolder.mAMPM.setText("am");
            viewHolder.mTime.setText("0"+Integer.toString(mSetTime));
        }
        else if( mSetTime>=10 && mSetTime<12 ){
            viewHolder.mAMPM.setText("am");
            viewHolder.mTime.setText(Integer.toString(mSetTime));
        }
        else if(mSetTime == 12){
            viewHolder.mAMPM.setText("pm");
            viewHolder.mTime.setText(Integer.toString(mSetTime));
        }
        else if( mSetTime>12 && mSetTime<22 ){
            viewHolder.mAMPM.setText("pm");
            viewHolder.mTime.setText("0"+Integer.toString(mSetTime-12));
        }
        else if( mSetTime>=22 && mSetTime<24){
            viewHolder.mAMPM.setText("pm");
            viewHolder.mTime.setText(Integer.toString(mSetTime-12));
        }
        else if( mSetTime == 24 ){
            viewHolder.mAMPM.setText("am");
            viewHolder.mTime.setText("0");
        }
        //    viewHolder.mTime.setText(Integer.toString(mPlanList.get(position).getTime()));
        viewHolder.mTagColor = R.color.titleTextColor;
    }


    @Override
    public int getItemCount() {
        return mPlanList.size();
    }
}
