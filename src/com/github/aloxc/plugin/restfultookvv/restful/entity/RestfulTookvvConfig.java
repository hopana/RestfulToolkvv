package com.github.aloxc.plugin.restfultookvv.restful.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 配置文件
 * @author liyh
 */
public class RestfulTookvvConfig implements Serializable {
    private static final long serialVersionUID = 7374155593314061203L;
    /**
     * 端口
     */
    private Integer port;

    /**
     * 服务地址
     */
    private String address;

    /**
     * 自定义配置
     */
    private List<NameAndValue> customConfigList;

    /**
     * 环境
     */
    private List<Environment> environmentList;

    /**
     * 是否启动https
     */
    private boolean https;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<NameAndValue> getCustomConfigList() {
        return customConfigList;
    }

    public void setCustomConfigList(List<NameAndValue> customConfigList) {
        this.customConfigList = customConfigList;
    }

    public boolean isHttps() {
        return https;
    }

    public void setHttps(boolean https) {
        this.https = https;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestfulTookvvConfig that = (RestfulTookvvConfig) o;
        return port == that.port &&
                https == that.https &&
                Objects.equals(address, that.address) &&
                Objects.equals(customConfigList, that.customConfigList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, address, customConfigList, https);
    }

    @Override
    public String toString() {
        return "RestfulTookvvConfig{" +
                "port=" + port +
                ", address='" + address + '\'' +
                ", customConfigList=" + customConfigList +
                ", https=" + https +
                '}';
    }
}
