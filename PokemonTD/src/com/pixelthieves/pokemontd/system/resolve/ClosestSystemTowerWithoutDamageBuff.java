package com.pixelthieves.pokemontd.system.resolve;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.pixelthieves.pokemontd.component.TowerTypeComponent;
import com.pixelthieves.pokemontd.component.attack.effects.buff.DamageBuffEffect;
import com.pixelthieves.pokemontd.entity.tower.TowerName;

public class ClosestSystemTowerWithoutDamageBuff extends ClosestSystemTower {

    @Mapper
    ComponentMapper<DamageBuffEffect> damageBufferMapper;

    @Override
    protected boolean isRequirementMet(Entity e) {
        TowerName name = towerTypeMapper.get(e).getTowerType().getName();
        if (name.equals(TowerName.Spell) || name.equals(TowerName.Enchanted) || name.equals(TowerName.Magic)) {
            return false;
        }
        return !damageBufferMapper.has(e);
    }
}
