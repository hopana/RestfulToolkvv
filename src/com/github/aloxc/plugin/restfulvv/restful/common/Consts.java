package com.github.aloxc.plugin.restfulvv.restful.common;


import com.github.aloxc.plugin.restfulvv.restful.method.HttpMethod;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
import java.awt.*;

/**
 * 常量类
 * @author liyh
 */
public interface Consts {

    class METHOD {
        public static Icon get(HttpMethod method) {
            if (method == null) {
                return METHOD.UNDEFINED;
            }
            if (method.equals(HttpMethod.GET)) {
                return METHOD.GET;
            }else if(method.equals(HttpMethod.POST)) {
                return METHOD.POST;
            } else if (method.equals(HttpMethod.PUT) || method.equals(HttpMethod.PATCH)) {
                return METHOD.PUT;
            }else if(method.equals(HttpMethod.DELETE)) {
                return METHOD.DELETE;
            }
            return null;
        }


        public static Icon GET = IconLoader.getIcon("/icons/method/g.png"); // 16x16 GREEN
        // post put patch
        public static Icon PUT = IconLoader.getIcon("/icons/method/p2.png"); // 16x16 ORANGE
        public static Icon POST = IconLoader.getIcon("/icons/method/p.png"); // 16x16 BLUE
        public static Icon PATCH = IconLoader.getIcon("/icons/method/p3.png"); // 16x16 GRAY
        public static Icon DELETE = IconLoader.getIcon("/icons/method/d.png"); // 16x16 RED
        public static Icon UNDEFINED = IconLoader.getIcon("/icons/method/undefined.png"); // 16x16 GRAY
        // OPTIONS HEAD
    }

    Icon MODULE = IconLoader.getIcon("/icons/moduleNode.png"); // 16x16
    Icon SERVICE = IconLoader.getIcon("/icons/restvv.png"); // 16x16
    Color MAIN_COLOR = new Color(255,162,45);

}
