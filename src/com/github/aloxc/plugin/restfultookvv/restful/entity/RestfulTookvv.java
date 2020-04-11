package com.github.aloxc.plugin.restfultookvv.restful.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * RestfulTookvv
 * @author liyh
 */
public class RestfulTookvv implements Serializable {
    private static final long serialVersionUID = -4409316467501719038L;
    private RestfulTookvvConfig config;

    private Map<String, List<RestfulTookvvUserCase>> userCaseMap ;

    public RestfulTookvvConfig getConfig() {
        return config;
    }

    public void setConfig(RestfulTookvvConfig config) {
        this.config = config;
    }

    public Map<String, List<RestfulTookvvUserCase>> getUserCaseMap() {
        return userCaseMap;
    }

    public void setUserCaseMap(Map<String, List<RestfulTookvvUserCase>> userCaseMap) {
        this.userCaseMap = userCaseMap;
    }
}
