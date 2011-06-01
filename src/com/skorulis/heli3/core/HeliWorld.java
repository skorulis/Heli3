package com.skorulis.heli3.core;

import java.util.ArrayList;
import java.util.LinkedList;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import com.skorulis.forplay.util.InputState;
import com.skorulis.heli3.components.EntityI;
import com.skorulis.heli3.components.EventI;
import static forplay.core.ForPlay.*;

import forplay.core.GroupLayer;

public class HeliWorld {

	private World world;
	private Heli3Game game;
	private LinkedList<EntityI> entities,newEntities;
	private GroupLayer entityLayer;
	
	public HeliWorld(Heli3Game game,int width,int height) {
	  this.game = game;
	  Vec2 gravity = new Vec2(0,80);
	  world = new World(gravity, true);
	  entities = new LinkedList<EntityI>();
	  newEntities = new LinkedList<EntityI>();
	  entityLayer = graphics().createGroupLayer();
	  graphics().rootLayer().add(entityLayer);
	  buildBounds(width, height);
	}
	
	private void buildBounds(int width,int height) {
	  float buffer = 10;
	  Body ground = world.createBody(new BodyDef());
    PolygonShape groundShape = new PolygonShape();
    groundShape.setAsEdge(new Vec2(0, height), new Vec2(width, height));
    ground.createFixture(groundShape, 0.0f);
	}
	
	public void update(float delta,InputState input) {
	  world.step(delta, 10, 10);
	  java.util.Iterator<EntityI> it = entities.iterator();
    EntityI e;
    ArrayList<EventI> events;
    while(it.hasNext()) {
      e = it.next();
      events = e.update(delta, input);
      if(events!=null) {
        for(EventI event: events) {
          game.processEvent(event);
        }
      }
    }
    entities.addAll(newEntities);
    newEntities.clear();
    
	}
	
	public void paint(float alpha) {
	  java.util.Iterator<EntityI> it = entities.iterator();
	  EntityI e;
	  while(it.hasNext()) {
	    e = it.next();
	    e.paint(alpha);
	  }
	}
	
	public World world() {
	  return world;
	}
	
	public void addEntity(EntityI e) {
	  newEntities.add(e);
	  entityLayer.add(e.layer());
	}
	
}
