package cn.vv.plugin.restfultookvv.restful.common.resolver;

import cn.vv.plugin.restfultookvv.restful.navigation.action.RestServiceItem;

import java.util.List;

public interface ServiceResolver {

    /* 获取module 中所有的服务列表 */
    List<RestServiceItem> findAllSupportedServiceItemsInModule();

    /* 获取project 中所有的服务列表 */
    List<RestServiceItem> findAllSupportedServiceItemsInProject();
}
