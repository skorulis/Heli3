package com.skorulis.heli3.components;

import com.skorulis.forplay.entities.Entity;
import com.skorulis.forplay.entities.PhysicsComponent;

public interface HeliEntity extends Entity{

  public int team();
  public PhysicsComponent physics();
  
}
