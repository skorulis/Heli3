package com.skorulis.heli3.core;

import forplay.core.GroupLayer;
import static forplay.core.ForPlay.*;

public class Heli3Menu {

	public GroupLayer menuLayer;
	
	public Heli3Menu() {
		menuLayer = graphics().createGroupLayer();
		graphics().rootLayer().add(menuLayer);
	}
	
}
