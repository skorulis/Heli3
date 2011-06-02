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
	
	public static float physUnitPerScreenUnit = 1 / 26.666667f;
	
	Helicopter helicopter;
	InputState input;
	private LayerTracker tracker;
	private int width = 600;
	private int height = 480;
	
	@Override
	public void init() {
		frameCalc = new FrameRateCalc();
		world = new HeliWorld(this,width*5,height,physUnitPerScreenUnit);
	
		input = new InputState(256);
		input.setClickLayer(graphics().rootLayer());
		input.setScale(physUnitPerScreenUnit);
		 helicopter = new Helicopter(world.world(),physUnitPerScreenUnit);
		 world.addEntity(helicopter);
		 keyboard().setListener(input);
		 pointer().setListener(input);
		 
		 Building b = new Building(world.world(),80, 200,physUnitPerScreenUnit);
		 b.physics().setScreenPosition(50,height-200);
		 world.addEntity(b);
		 
		 tracker = new LayerTracker(graphics().rootLayer(), helicopter.layer(),width,height);
		 tracker.setPhysScale(physUnitPerScreenUnit);
		 
	}

	@Override
	public void update(float delta) {
	  delta = delta/1000.0f;
		world.update(delta,input);
		tracker.update(delta);
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
