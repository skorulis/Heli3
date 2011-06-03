package com.skorulis.heli3.core;

import java.util.ArrayList;
import java.util.LinkedList;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import com.skorulis.forplay.entities.Entity;
import com.skorulis.forplay.entities.Event;
import com.skorulis.forplay.util.InputState;
import com.skorulis.forplay.util.WorldUtil;

import static forplay.core.ForPlay.*;

import forplay.core.CanvasLayer;
import forplay.core.DebugDrawBox2D;
import forplay.core.GroupLayer;

public class HeliWorld {

	private World world;
	private Heli3Game game;
	private LinkedList<Entity> entities,newEntities;
	private GroupLayer entityLayer;
	
	private static boolean showDebugDraw = true;
  private DebugDrawBox2D debugDraw;
  private float width,height;
	
	public HeliWorld(Heli3Game game,float width,float height,float physScale) {
	  this.game = game;
	  this.width = width*physScale;
	  this.height = height*physScale;
	  Vec2 gravity = new Vec2(0,80*physScale);
	  world = new World(gravity, true);
	  entities = new LinkedList<Entity>();
	  newEntities = new LinkedList<Entity>();
	  entityLayer = graphics().createGroupLayer();
	  graphics().rootLayer().add(entityLayer);
	  WorldUtil.buildBounds(this.width, this.height,world);
	  entityLayer.setScale(1f / Heli3Game.physUnitPerScreenUnit);
	  
	  if(showDebugDraw) {
	    debugDraw = WorldUtil.showDebugDraw(this.width, this.height, physScale, world);
	  } 
	}
	
	public void update(float delta,InputState input) {
	  world.step(delta, 10, 10);
	  java.util.Iterator<Entity> it = entities.iterator();
    Entity e;
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
	
	public void paint(float alpha) {
	  java.util.Iterator<Entity> it = entities.iterator();
	  Entity e;
	  while(it.hasNext()) {
	    e = it.next();
	    e.paint(alpha);
	  }
	  
	  if (showDebugDraw) {
      debugDraw.getCanvas().canvas().clear();
      world.drawDebugData();
    }
	  
	}
	
	public World world() {
	  return world;
	}
	
	public void addEntity(Entity e) {
	  newEntities.add(e);
	  entityLayer.add(e.layer());
	}
	
	public GroupLayer entityLayer() {
	  return entityLayer;
	}
	
}
