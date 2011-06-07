package com.skorulis.heli3.core;

import java.util.ArrayList;
import java.util.Iterator;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import com.skorulis.forplay.entities.Entity;
import com.skorulis.forplay.entities.Event;
import com.skorulis.forplay.util.EntityWorld;
import com.skorulis.forplay.util.InputState;
import com.skorulis.forplay.util.WorldUtil;
import com.skorulis.heli3.components.HeliEntity;

import static forplay.core.ForPlay.*;

import forplay.core.DebugDrawBox2D;
import forplay.core.GroupLayer;

public class HeliWorld extends EntityWorld<HeliEntity> {

	private Heli3Game game;
	
	public HeliWorld(Heli3Game game,float width,float height,float physScale) {
	  super(width,height,new Vec2(0,80*physScale),physScale,true);
	  this.game = game;	 
	}
	
	public void update(float delta,InputState input) {
	  world.step(delta, 10, 10);
	  Iterator<HeliEntity> it = entities.iterator();
    HeliEntity e;
    ArrayList<Event> events;
    while(it.hasNext()) {
      e = it.next();
      events = e.update(delta, input);
      if(!e.alive()) {
        e.layer().parent().remove(e.layer());
        it.remove();
        world.destroyBody(e.body());
      }
      if(events!=null) {
        for(Event event: events) {
          game.processEvent(event);
        }
      }
    }
    entities.addAll(newEntities);
    newEntities.clear();
    
	}
	
	public HeliEntity findEntity(Class type,int team) {
	  Iterator<HeliEntity> it = entities.iterator();
	  HeliEntity e;
	  while(it.hasNext()) {
	    e = it.next();
	    if(e.getClass()==type && e.team()==team) {
	      return e;
	    }
	  }
	  return null;
	}
	
}
