package com.xkings.pokemontd.system.autonomous;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector3;
import com.xkings.core.component.PositionComponent;
import com.xkings.pokemontd.Health;
import com.xkings.pokemontd.Player;
import com.xkings.pokemontd.Treasure;
import com.xkings.pokemontd.component.*;
import com.xkings.pokemontd.entity.MoneyInfo;
import com.xkings.pokemontd.entity.StaticObject;
import com.xkings.pokemontd.entity.creep.Creep;
import com.xkings.pokemontd.entity.creep.CreepAbilityType;
import com.xkings.pokemontd.entity.creep.CreepType;
import com.xkings.pokemontd.entity.datatypes.StaticObjectType;
import com.xkings.pokemontd.map.Path;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tomas on 10/4/13.
 */
public class DeathSystem extends EntityProcessingSystem {

    private final Player player;
    @Mapper
    ComponentMapper<PositionComponent> positionMapper;
    @Mapper
    ComponentMapper<HealthComponent> healthMapper;
    @Mapper
    ComponentMapper<TreasureComponent> treasureMapper;
    @Mapper
    ComponentMapper<CreepAbilityComponent> creepAbilityMapper;
    @Mapper
    ComponentMapper<PathComponent> pathMapper;
    @Mapper
    ComponentMapper<WaveComponent> waveMapper;
    @Mapper
    ComponentMapper<CreepTypeComponent> creepTypeMapper;

    public DeathSystem(Player player) {
        super(Aspect.getAspectForAll(HealthComponent.class, TreasureComponent.class, CreepAbilityComponent.class));
        this.player = player;
    }

    @Override
    protected void process(Entity e) {
        Health health = healthMapper.get(e).getHealth();
        if (!health.isAlive()) {
            switch (creepAbilityMapper.get(e).getCreepAbilityType()) {
                case RESURRECT:
                    resurrect(e, 4000);
                    break;
                case SPAWN:
                    spawn(e);
                    break;
                default:
                    earn(e);
                    die(e);
            }
        }
    }

    private static final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();

    private void resurrect(final Entity e, int delayMs) {
        final Vector3 position = positionMapper.get(e).getPoint();
        final Path path = pathMapper.get(e).getPath();
        final WaveComponent waveComponent = waveMapper.get(e);
        final CreepType creepType = creepTypeMapper.get(e).getCreepType();
        die(e);
        final Entity grave = StaticObject.registerStaticObject(world, StaticObjectType.GRAVE, position.x, position.y);

        Runnable task = new Runnable() {
            public void run() {
                die(grave);
                Creep.registerCreep(world, path, waveComponent, creepType, CreepAbilityType.NORMAL, position.x,
                        position.y);
            }
        };
        worker.schedule(task, delayMs, TimeUnit.MILLISECONDS);
    }

    private void spawn(Entity e) {

    }


    private void earn(Entity e) {
        Vector3 position = positionMapper.get(e).getPoint();
        Treasure treasure = treasureMapper.get(e).getTreasure();
        MoneyInfo.registerMoneyInfo(world, treasure.getGold(), position.x, position.y);
        treasure.transferTo(player.getTreasure());
    }

    private void die(Entity e) {
        e.deleteFromWorld();
    }


}
