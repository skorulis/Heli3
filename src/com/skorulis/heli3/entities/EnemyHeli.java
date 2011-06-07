package com.skorulis.heli3.entities;

import java.util.ArrayList;
import com.skorulis.forplay.entities.Event;
import com.skorulis.forplay.util.InputState;
import com.skorulis.heli3.components.HeliAIComponent;
import com.skorulis.heli3.core.HeliWorld;

public class EnemyHeli extends Helicopter {

  private HeliAIComponent ai;
  
  public EnemyHeli(HeliWorld world, float physScale,int team) {
    super(world, physScale,team);
    ai = new HeliAIComponent(world,this);
    firerate = 0.7f;
  }
  
  public ArrayList<Event> update(float delta,InputState input) {
    image.update(delta);
    Bullet b = ai.update(delta);
    physics.body().setAngularVelocity(0);
    cooldown-=delta;
    if(b!=null) {
      ArrayList<Event> ret = new ArrayList<Event>();
      ret.add(b);
      return ret;
    }
    return null;
  }

  
  
}
