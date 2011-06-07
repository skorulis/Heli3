package com.skorulis.heli3.entities;


import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.skorulis.forplay.entities.Entity;
import com.skorulis.forplay.entities.EntityImageManager;
import com.skorulis.forplay.entities.Event;
import com.skorulis.forplay.entities.PhysicsComponent;
import com.skorulis.forplay.util.AnimatedImage;
import com.skorulis.forplay.util.InputState;
import com.skorulis.heli3.components.HeliEntity;
import com.skorulis.heli3.core.HeliWorld;

import forplay.core.Layer;

public class Helicopter implements HeliEntity{

  protected static final int WIDTH = 30;
  protected static final int HEIGHT = 15;
  protected static final int left = -10;
  protected static final int top = -5;
  protected EntityImageManager imageMan;
  
  protected PhysicsComponent physics;
  protected AnimatedImage image;
  protected float firerate=0.2f;
  protected float cooldown;
  protected int team;
  
  public Helicopter(HeliWorld world,float physScale,int team) {
    this.team = team;
    String[] images = new String[] {"images/helicopter.png","images/helicopter2.png"};
    physics = new PhysicsComponent(BodyType.DYNAMIC,physScale);
    physics.bodyDef().position = new Vec2(width()/2,height()/2);
    physics.setFixtureDef(getFixtureDef());
    physics.createBody(world.world());
    
    
    Building b = (Building) world.findEntity(Building.class, team);
    
    
    physics.body().setTransform(new Vec2(b.physics().x(),b.physics().y()), 0);
    
    imageMan = new EntityImageManager(this);
    image = new AnimatedImage();
    image.setImages(images, imageMan);
  }
  
  public FixtureDef getFixtureDef() {
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = PhysicsComponent.getRect(left*physics.physScale(), top*physics.physScale(), width(), height());
    fixtureDef.density = 0.4f;
    fixtureDef.friction = 0.1f;
    fixtureDef.restitution = 0.05f;
    return fixtureDef;
  }

  @Override
  public void paint(float alpha) {
    physics.stdPaint(alpha, layer());
  }

  @Override
  public ArrayList<Event> update(float delta,InputState input) {
    image.update(delta);
    if(input.keyDown('W') ) {
      physics.move(new Vec2(0,-50*physics.physScale()*delta));
    }
    if(input.keyDown('A')) {
      physics.move(new Vec2(-10*physics.physScale()*delta,0));
    }
    if(input.keyDown('D')) {
      physics.move(new Vec2(10*physics.physScale()*delta,0));
    }
    physics.body().setAngularVelocity(0);
    cooldown-=delta;
    if(input.mouseDown()) {
      Bullet b =fire(input.pointer());
      if(b!=null) {
        ArrayList<Event> ret = new ArrayList<Event>();
        ret.add(b);
        return ret;
      } 
    }
    return null;
  }
  
  //Attempt to fire a bulllet
  public Bullet fire(Vec2 position) {
    if(cooldown > 0) {
      return null;
    }
    Vec2 dir =position.sub(physics.body().getPosition());dir.normalize();
    Bullet b = new Bullet(this,dir, physics.physScale());
    cooldown = firerate;
    return b;
  }

  @Override
  public Layer layer() {
    return image.layer();
  }

  @Override
  public float width() {
    return WIDTH*physics.physScale();
  }

  @Override
  public float height() {
    return HEIGHT*physics.physScale();
  }
  
  public boolean alive() {
    return true;
  }
  
  public PhysicsComponent physics() {
    return physics;
  }
  
  public float physScale() {
    return physics.physScale();
  }
  
  public Body body() {
    return physics.body();
  }

  @Override
  public int team() {
    return team;
  }
  
}
