package com.github.aloxc.plugin.restfulvv.utils;

import com.github.aloxc.plugin.restfulvv.restful.navigation.action.RestServiceItem;
import com.intellij.openapi.actionSystem.DataKey;

import java.util.List;

public class RestServiceDataKeys {

  public static final DataKey<List<RestServiceItem>> SERVICE_ITEMS = DataKey.create("SERVICE_ITEMS");

  private RestServiceDataKeys() {
  }
}
