package com.github.aloxc.plugin.restfulvv.restful.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * key value保存自定义配置
 * @author liyh
 */
public class NameAndValue implements Serializable {
    private static final long serialVersionUID = -3013602298251327189L;
    private String name ;
    private String value ;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NameAndValue that = (NameAndValue) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "NameAndValue{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}


