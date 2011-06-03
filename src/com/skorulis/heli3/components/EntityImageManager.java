package com.skorulis.heli3.components;

import com.skorulis.forplay.entities.Entity;

import forplay.core.Image;
import forplay.core.ImageLayer;
import forplay.core.ResourceCallback;
import static forplay.core.ForPlay.*;

public class EntityImageManager implements ResourceCallback<Image>{

  private Entity entity;
  
  public EntityImageManager(Entity entity) {
    this.entity = entity;
  }

  @Override
  public void done(Image image) {
    ImageLayer layer = (ImageLayer) entity.layer();
    layer.setWidth(image.width());
    layer.setHeight(image.height());
    layer.setOrigin(image.width() / 2f, image.height() / 2f);
    layer.setScale(entity.width() / image.width(), entity.height() / image.height());
  }

  @Override
  public void error(Throwable err) {
    log().debug("ERROR " + err);
  }
  
}
