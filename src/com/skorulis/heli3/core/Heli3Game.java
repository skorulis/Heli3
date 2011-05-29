package com.skorulis.heli3.core;

import com.skorulis.forplay.util.FrameRateCalc;
import static forplay.core.ForPlay.*;

import forplay.core.Game;

public class Heli3Game implements Game{

	FrameRateCalc frameCalc;
	HeliWorld world;
	
	@Override
	public void init() {
		frameCalc = new FrameRateCalc();
		world = new HeliWorld();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(float alpha) {
		frameCalc.frame(currentTime());
	}

	@Override
	public int updateRate() {
		return 25;
	}

}
