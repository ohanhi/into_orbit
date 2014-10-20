package com.ohanhi.into_orbit;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by ohan on 17.10.2014.
 */
public abstract class Goal {

    private boolean achieved = false;

    public boolean achieve() {
        if (achieved) return false;

        this.achieved = true;
        return true;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public abstract void drawToRenderer(ShapeRenderer renderer);

}
