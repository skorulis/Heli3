package com.skorulis.heli3.entities;


import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.skorulis.forplay.entities.Entity;
import com.skorulis.forplay.entities.Event;
import com.skorulis.forplay.entities.PhysicsComponent;
import com.skorulis.forplay.util.AnimatedImage;
import com.skorulis.forplay.util.InputState;
import com.skorulis.heli3.components.EntityImageManager;

import forplay.core.Layer;

public class Helicopter implements Entity{

  private static final int WIDTH = 30;
  private static final int HEIGHT = 15;
  private int left = -10;
  private int top = -5;
  private EntityImageManager imageMan;
  
  private PhysicsComponent physics;
  private AnimatedImage image;
  private float firerate=0.2f;
  private float cooldown;
  
  
  public Helicopter(World world,float physScale) {
    String[] images = new String[] {"images/helicopter.png","images/helicopter2.png"};
    physics = new PhysicsComponent(BodyType.DYNAMIC,physScale);
    physics.setFixtureDef(getFixtureDef());
    physics.createBody(world);
    physics.body().setTransform(new Vec2(150*physScale,50*physScale), 0);
    
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
    image.layer().setTranslation(physics.x(), physics.y());
    image.layer().setRotation(physics.body().getAngle());
  }

  @Override
  public ArrayList<Event> update(float delta,InputState input) {
    image.update(delta);
    if(input.keyDown('W')) {
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
    if(input.mouseDown() && cooldown <=0) {
      Bullet b = new Bullet(physics.x(), physics.y(),input.mouseDir(physics.body().getPosition()),  physics.body().getWorld(),physics.physScale());
      ArrayList<Event> ret = new ArrayList<Event>();
      ret.add(b);
      cooldown = firerate;
      return ret;
    }
    
    return null;
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
  
  public Body body() {
    return physics.body();
  }
  
}
