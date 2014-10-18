package com.ohanhi.into_orbit;

/**
 * Created by ohan on 17.10.2014.
 */
public class Goal {

    private boolean achieved = false;

    public boolean achieve() {
        if (achieved) return false;

        this.achieved = true;
        return true;
    }

    public boolean isAchieved() {
        return achieved;
    }

}
