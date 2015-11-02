package kr.ac.kumoh.railroApplication.adapters;

/**
 * Created by sj on 2015-10-30.
 */
public interface ItemTouchHelperAdapter {
     boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
