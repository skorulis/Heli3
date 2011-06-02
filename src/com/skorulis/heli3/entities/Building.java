package com.skorulis.heli3.entities;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.skorulis.forplay.util.InputState;
import com.skorulis.heli3.components.EntityI;
import com.skorulis.heli3.components.EventI;
import com.skorulis.heli3.components.PhysicsComponent;

import forplay.core.CanvasLayer;
import forplay.core.Layer;
import static forplay.core.ForPlay.*;

public class Building implements EntityI{
  
  CanvasLayer canvas;
  PhysicsComponent physics;
  
  public Building(World world,int width,int height,float physScale) {
    canvas = graphics().createCanvasLayer(width, height);
    physics = new PhysicsComponent(BodyType.STATIC,physScale);
    physics.setFixtureDef(getFixtureDef());
    physics.createBody(world);
    
    //canvas.setScale(physScale);
    canvas.canvas().setFillColor(0xFF003388);
    canvas.canvas().fillRect(0, 0, width*physScale, height*physScale);
    
    
  }
  
  public FixtureDef getFixtureDef() {
    FixtureDef fixtureDef = new FixtureDef();    
    PolygonShape polygonShape = new PolygonShape();
    Vec2[] polygon = new Vec2[4];
    polygon[0] = new Vec2(0, 0);
    polygon[1] = new Vec2(width(), 0);
    polygon[2] = new Vec2(width(), height());
    polygon[3] = new Vec2(0, height());
    polygonShape.set(polygon, polygon.length);
    fixtureDef.shape = polygonShape;
    fixtureDef.friction = 0.4f;
    return fixtureDef;
  }

  @Override
  public void paint(float alpha) {
    canvas.setTranslation(physics.x(), physics.y());
  }
  
  public PhysicsComponent physics() {
	  return physics;
  }

  @Override
  public ArrayList<EventI> update(float delta, InputState input) {
    return null;
  }

  @Override
  public Layer layer() {
    return canvas;
  }

  @Override
  public float width() {
    return canvas.canvas().width()*physics.physScale();
  }

  @Override
  public float height() {
    return canvas.canvas().height()*physics.physScale();
  }

}
