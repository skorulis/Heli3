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
	  buildBounds(this.width, this.height);
	  entityLayer.setScale(1f / Heli3Game.physUnitPerScreenUnit);
	  
	  showDebugDraw();
	}
	
	private void showDebugDraw() {
	  if (showDebugDraw) {
	    int wid = (int) (width / Heli3Game.physUnitPerScreenUnit);
	    int hgt = (int) (height / Heli3Game.physUnitPerScreenUnit);
      CanvasLayer canvasLayer =
          graphics().createCanvasLayer(wid,hgt);
      graphics().rootLayer().add(canvasLayer);
      debugDraw = new DebugDrawBox2D();
      debugDraw.setCanvas(canvasLayer);
      debugDraw.setFlipY(false);
      debugDraw.setStrokeAlpha(150);
      debugDraw.setFillAlpha(75);
      debugDraw.setStrokeWidth(2.0f);
      debugDraw.setFlags(DebugDraw.e_shapeBit | DebugDraw.e_jointBit | DebugDraw.e_aabbBit);
      debugDraw.setCamera(0, 0, 1f / Heli3Game.physUnitPerScreenUnit);
      world.setDebugDraw(debugDraw);
    }
	}
	
	private void buildBounds(float width,float height) {
	  float buffer = 10;
	  Body ground = world.createBody(new BodyDef());
    PolygonShape groundShape = new PolygonShape();
    groundShape.setAsEdge(new Vec2(0, height), new Vec2(width, height));
    ground.createFixture(groundShape, 0.0f);
    
    Body right = world.createBody(new BodyDef());
    PolygonShape rightShape = new PolygonShape();
    rightShape.setAsEdge(new Vec2(width, 0), new Vec2(width, height));
    right.createFixture(rightShape, 0.0f);
    
    Body left = world.createBody(new BodyDef());
    PolygonShape leftShape = new PolygonShape();
    leftShape.setAsEdge(new Vec2(0, 0), new Vec2(0, height));
    left.createFixture(leftShape, 0.0f);
    
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
