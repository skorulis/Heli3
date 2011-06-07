package com.skorulis.heli3.components;

import org.jbox2d.common.Vec2;

import com.skorulis.heli3.core.Heli3Game;
import com.skorulis.heli3.core.HeliWorld;
import com.skorulis.heli3.entities.Bullet;
import com.skorulis.heli3.entities.EnemyHeli;
import com.skorulis.heli3.entities.Helicopter;

import static forplay.core.ForPlay.*;

public class HeliAIComponent {

  private EnemyHeli heli;
  private HeliEntity target;
  private HeliWorld world;
  
  public HeliAIComponent(HeliWorld world, EnemyHeli heli) {
    this.world = world;
    this.heli = heli;
  }
  
  public Bullet update(float delta) {
    if(target!=null) {
      if(target.physics().y() < heli.physics().y()) {
        heli.physics().move(new Vec2(0,-50*heli.physics().physScale()*delta));
      }
      if(target.physics().x() < heli.physics().x()) {
        heli.physics().moveScale(new Vec2(-10*delta,0));
      }
      if(target.physics().x() > heli.physics().x()) {
        heli.physics().moveScale(new Vec2(+10*delta,0));
      }
      if(target.physics().dist(heli.physics()) < 15) {
        return heli.fire(target.physics().body().getPosition());
      }
      
      
    } else {
      target = findTarget();
    }
    return null;
  }
  
  public HeliEntity findTarget() {
    return world.findEntity(Helicopter.class, Heli3Game.TEAM_HUMAN);
  }
  
}
