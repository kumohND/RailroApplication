package kr.ac.kumoh.railroApplication.classes;

import java.util.Comparator;

/**
 * Created by WooChan on 2015-10-30.
 */
public class NoAscCompare implements Comparator<PlanListItem> {
    @Override
    public int compare(PlanListItem lhs, PlanListItem rhs) {
        return lhs.getTime() < rhs.getTime() ? -1 : lhs.getTime() < rhs.getTime() ? -1:0;
    }
}
