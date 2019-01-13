package com.ctrip.framework.cs;

/**
 * Created by jiang.j on 2017/2/8.
 */
public class OwnerJudge implements AppInfo.StatusSource {
    private static OwnerJudge ownerStatusSource = new OwnerJudge();
    boolean isNormal = true;

    private OwnerJudge() {

    }

    public static OwnerJudge getInstance() {
        return ownerStatusSource;
    }

    public void toNormal() {
        isNormal = true;
    }

    public void toAbnormal() {
        isNormal = false;
    }

    @Override
    public boolean normal() {
        return isNormal;
    }
}
