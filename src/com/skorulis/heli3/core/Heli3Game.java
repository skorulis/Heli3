package com.skorulis.heli3.core;


import org.jbox2d.common.Vec2;

import com.skorulis.forplay.util.FrameRateCalc;
import com.skorulis.forplay.util.InputState;
import com.skorulis.heli3.components.EntityI;
import com.skorulis.heli3.components.EventI;
import com.skorulis.heli3.entities.Building;
import com.skorulis.heli3.entities.Helicopter;

import static forplay.core.ForPlay.*;

import forplay.core.Game;

public class Heli3Game implements Game{

	FrameRateCalc frameCalc;
	HeliWorld world;
	
	Helicopter helicopter;
	InputState input;
	private int width = 600;
	private int height = 480;
	
	@Override
	public void init() {
		frameCalc = new FrameRateCalc();
		world = new HeliWorld(this,width,height);
	
		input = new InputState(256);
		 helicopter = new Helicopter(world.world());
		 world.addEntity(helicopter);
		 keyboard().setListener(input);
		 pointer().setListener(input);
		 
		 Building b = new Building(world.world(),80, 200);
		 b.physics().body().setTransform(new Vec2(50,height-b.height()), 0);
		 world.addEntity(b);
	}

	@Override
	public void update(float delta) {
	  delta = delta/1000.0f;
		world.update(delta,input);
		
	}

	@Override
	public void paint(float alpha) {
		frameCalc.frame(currentTime());
		world.paint(alpha);
		
	}

	@Override
	public int updateRate() {
		return 25;
	}

  public void processEvent(EventI event) {
    if(event.getType()==EventTypes.NEW_ENTITY) {
      world.addEntity((EntityI)event);
    }
  }

}
