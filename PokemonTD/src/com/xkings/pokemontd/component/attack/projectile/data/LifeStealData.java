package com.xkings.pokemontd.component.attack.projectile.data;

import com.xkings.pokemontd.component.attack.EffectName;

/**
 * Created by Tomas on 10/13/13.
 */
public class LifeStealData extends EffectData {
    private final float duration;

    public LifeStealData(float duration) {
        super();
        this.duration = duration;
    }

    public float getDuration() {
        return duration;
    }

    public String getEffectDescription(EffectName effectName, float speed, float damage) {
        return super.getEffectDescription(effectName, speed, damage)+ "temporary steal " + (int) (damage * 100) + "% of creeps health for " + duration + "s.";
    }
}
