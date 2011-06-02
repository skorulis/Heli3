package com.skorulis.heli3.entities;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.skorulis.forplay.util.InputState;
import com.skorulis.heli3.components.EntityI;
import com.skorulis.heli3.components.EntityImageManager;
import com.skorulis.heli3.components.EventI;
import com.skorulis.heli3.components.PhysicsComponent;
import com.skorulis.heli3.core.EventTypes;

import forplay.core.ImageLayer;
import forplay.core.Layer;
import static forplay.core.ForPlay.*;

public class Bullet implements EntityI,EventI{

  private ImageLayer image;
  private PhysicsComponent physics;
  private float sensorTime= 0.4f;
  private float timeAlive;
  private EntityImageManager imageMan;
  private static final int RADIUS = 8; 
  
  public Bullet(float x,float y,Vec2 vel,World world,float physScale) {
    physics = new PhysicsComponent(BodyType.DYNAMIC,physScale);
    imageMan = new EntityImageManager(this);
    image = graphics().createImageLayer(assetManager().getImage("images/bullet.png"));
    image.image().addCallback(imageMan);
    
    
    physics.setFixtureDef(getFixtureDef());
    physics.createBody(world);
    physics.body().setTransform(new Vec2(x,y), 0);
    physics.body().setLinearVelocity(new Vec2(vel.x*200*physScale,vel.y*200*physScale));
    
    //log().debug("VEL " + physics.body().getLinearVelocity());
  }
  
  public FixtureDef getFixtureDef() {
    FixtureDef fixtureDef = new FixtureDef();
    CircleShape circleShape = new CircleShape();
    circleShape.m_radius = RADIUS*physics.physScale();
    fixtureDef.shape = circleShape;
    fixtureDef.density = 0.4f;
    fixtureDef.friction = 0.00f;
    fixtureDef.restitution = 0.35f;
    fixtureDef.isSensor = true;
    circleShape.m_p.set(0, 0);
    return fixtureDef;
  }
  
  @Override
  public void paint(float alpha) {
    image.setTranslation(physics.x(), physics.y());
  }

  @Override
  public ArrayList<EventI> update(float delta, InputState input) {
	timeAlive+=delta;
	if(timeAlive > sensorTime) {
		this.physics.body().getFixtureList().setSensor(false);
	}
    return null;
  }

  @Override
  public Layer layer() {
    return image;
  }

  @Override
  public int getType() {
    return EventTypes.NEW_ENTITY;
  }

  @Override
  public float width() {
    return RADIUS*2*physics.physScale();
  }

  @Override
  public float height() {
    return RADIUS*2*physics.physScale();
  }

}
