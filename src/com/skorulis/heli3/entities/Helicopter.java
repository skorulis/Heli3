package com.skorulis.heli3.entities;


import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.skorulis.forplay.util.InputState;
import com.skorulis.heli3.components.AnimatedImage;
import com.skorulis.heli3.components.EntityI;
import com.skorulis.heli3.components.EventI;
import com.skorulis.heli3.components.PhysicsComponent;

import static forplay.core.ForPlay.*;

import forplay.core.Layer;

public class Helicopter implements EntityI{

  private PhysicsComponent physics;
  private AnimatedImage image;
  private float firerate=0.2f;
  private float cooldown;
  
  private int left = -10;
  private int top = -5;
  
  public Helicopter(World world) {
    String[] images = new String[] {"images/helicopter.png","images/helicopter2.png"};
    image = new AnimatedImage(images);
    physics = new PhysicsComponent(BodyType.DYNAMIC);
    physics.setFixtureDef(getFixtureDef());
    physics.createBody(world);
  }
  
  public FixtureDef getFixtureDef() {
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = PhysicsComponent.getRect(left, top, 20, 10);
    fixtureDef.density = 0.4f;
    fixtureDef.friction = 0.1f;
    fixtureDef.restitution = 0.05f;
    return fixtureDef;
  }

  @Override
  public void paint(float alpha) {
    image.layer().setTranslation(physics.x()+left, physics.y()+top);
  }

  @Override
  public ArrayList<EventI> update(float delta,InputState input) {
    image.update(delta);
    if(input.keyDown('W')) {
      physics.move(new Vec2(0,-370));
    }
    if(input.keyDown('A')) {
      physics.move(new Vec2(-100,0));
    }
    if(input.keyDown('D')) {
      physics.move(new Vec2(100,0));
    }
    cooldown-=delta;
    if(input.mouseDown() && cooldown <=0) {
      Bullet b = new Bullet(physics.x(), physics.y(),input.mouseDir(physics.body().getPosition()),  physics.body().getWorld());
      ArrayList<EventI> ret = new ArrayList<EventI>();
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
  
}
