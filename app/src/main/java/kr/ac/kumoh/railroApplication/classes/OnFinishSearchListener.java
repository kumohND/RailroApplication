package kr.ac.kumoh.railroApplication.classes;

import java.util.List;

public interface OnFinishSearchListener {
	public void onSuccess(List<Item> itemList);
	public void onFail();
}