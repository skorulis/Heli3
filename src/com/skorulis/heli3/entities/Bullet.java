package com.skorulis.heli3.entities;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.skorulis.forplay.entities.Entity;
import com.skorulis.forplay.entities.EntityImageManager;
import com.skorulis.forplay.entities.Event;
import com.skorulis.forplay.entities.PhysicsComponent;
import com.skorulis.forplay.util.InputState;
import com.skorulis.heli3.components.HeliEntity;
import com.skorulis.heli3.core.EventTypes;

import forplay.core.ImageLayer;
import forplay.core.Layer;
import static forplay.core.ForPlay.*;

public class Bullet implements HeliEntity,Event{

  private ImageLayer image;
  private PhysicsComponent physics;
  private float sensorTime= 0.4f;
  private float timeAlive;
  private EntityImageManager imageMan;
  private static final int RADIUS = 8; 
  private float life;
  private int team;
  
  public Bullet(HeliEntity source,Vec2 vel,float physScale) {
    physics = new PhysicsComponent(BodyType.DYNAMIC,physScale);
    imageMan = new EntityImageManager(this);
    image = graphics().createImageLayer(assetManager().getImage("images/bullet.png"));
    image.image().addCallback(imageMan);
    
    
    physics.setFixtureDef(getFixtureDef());
    physics.createBody(source.body().getWorld());
    physics.body().setTransform(new Vec2(source.body().getPosition().x,source.body().getPosition().y), 0);
    physics.body().setLinearVelocity(new Vec2(vel.x*200*physScale,vel.y*200*physScale));
    physics.body().getFixtureList().m_userData = this;
    life = 5;
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
  public ArrayList<Event> update(float delta, InputState input) {
	timeAlive+=delta;
	life-=delta;
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
  
  public boolean alive() {
    return life > 0;
  }
  
  public Body body() {
    return physics.body();
  }
  
  public PhysicsComponent physics() {
    return physics;
  }

  @Override
  public int team() {
    return team;
  }

}
