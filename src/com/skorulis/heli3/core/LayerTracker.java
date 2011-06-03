package com.skorulis.heli3.core;

import forplay.core.GroupLayer;
import forplay.core.Layer;
import forplay.core.Transform;

import static forplay.core.ForPlay.*;

public class LayerTracker {

  private Layer container;
  private Layer tracking;
  private int screenWidthHalf;
  private int screenHeightHalf;
  private float physScale = 1.0f;
  
  public LayerTracker(Layer container,Layer tracking,int screenWidth,int screenHeight) {
    this.container = container;
    this.tracking = tracking;
    this.screenWidthHalf = screenWidth/2;
    this.screenHeightHalf = screenHeight/2;
  }
  
  public void update(float delta) {
    Transform transform = tracking.transform();
    float transX = -transform.tx()/physScale + screenWidthHalf;
    float transY = -transform.ty()/physScale + screenHeightHalf; 
    container.setTranslation(transX,transY);
  }
  
  public void setPhysScale(float physScale) {
    this.physScale = physScale;
  }
  
}
