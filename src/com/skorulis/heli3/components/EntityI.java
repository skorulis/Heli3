package com.skorulis.heli3.components;

import java.util.ArrayList;

import com.skorulis.forplay.util.InputState;

import forplay.core.Layer;

public interface EntityI {

  public void paint(float alpha);
  public ArrayList<EventI> update(float delta,InputState input);
  public Layer layer();
  public float width();
  public float height();
  
}
