package com.skorulis.heli3.java;

import com.skorulis.heli3.core.Heli3Game;

import forplay.core.ForPlay;
import forplay.java.JavaAssetManager;
import forplay.java.JavaPlatform;

public class Heli3GameJava {

  public static void main(String[] args) {
    JavaAssetManager assets = JavaPlatform.register().assetManager();
    assets.setPathPrefix("src/com/skorulis/heli3/resources");
    ForPlay.run(new Heli3Game());
  }
  
}
