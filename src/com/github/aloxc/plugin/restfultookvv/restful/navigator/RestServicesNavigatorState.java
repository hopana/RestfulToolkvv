package com.github.aloxc.plugin.restfultookvv.restful.navigator;

import com.intellij.util.xmlb.annotations.Tag;
import org.jdom.Element;

public class RestServicesNavigatorState {
  public boolean showPort = true;

  @Tag("treeState")
  public Element treeState;
}