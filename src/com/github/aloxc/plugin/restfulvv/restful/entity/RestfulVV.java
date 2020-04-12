package com.github.aloxc.plugin.restfulvv.restful.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * RestfulVV
 * @author liyh
 */
public class RestfulVV implements Serializable {
    private static final long serialVersionUID = -4409316467501719038L;
    private RestfulVVConfig config;

    private Map<String, List<RestfulVVUserCase>> userCaseMap ;

    public RestfulVVConfig getConfig() {
        return config;
    }

    public void setConfig(RestfulVVConfig config) {
        this.config = config;
    }

    public Map<String, List<RestfulVVUserCase>> getUserCaseMap() {
        return userCaseMap;
    }

    public void setUserCaseMap(Map<String, List<RestfulVVUserCase>> userCaseMap) {
        this.userCaseMap = userCaseMap;
    }
}
