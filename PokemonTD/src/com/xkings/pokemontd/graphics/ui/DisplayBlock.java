package com.xkings.pokemontd.graphics.ui;

import com.badlogic.gdx.math.Rectangle;
import com.xkings.core.graphics.Renderable;
import com.xkings.core.logic.Updateable;

/**
 * Created by Tomas on 10/8/13.
 */
abstract class DisplayBlock extends Rectangle implements Renderable, Refresheable {
    protected DisplayBlock() {
    }

    protected DisplayBlock(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    protected DisplayBlock(Rectangle rect) {
        super(rect);
    }
}