package group.zealot.king.core.db.serviceimpl.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.util.NumberUtil;
import group.zealot.king.core.db.base.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.system.SysRouteService;
import group.zealot.king.core.zt.entity.system.SysRoute;
import org.springframework.stereotype.Service;

import java.util.List;

import static group.zealot.king.core.db.serviceimpl.ServiceImpls.sysAuthServiceImpl;

@Service
public class SysRouteServiceImpl extends BaseServiceImpl<SysRoute, Long> implements SysRouteService {

    @Override
    public JSONArray getRouteTree(Long sysRoleRouteId) {
        JSONArray jsonArray = new JSONArray();
        //获取当前用户 路由权限
        List<SysRoute> sysRouteList = sysAuthServiceImpl.getSysRoute(sysRoleRouteId);
        //获取所有路有权限
        List<SysRoute> list = getList();

        //一级路由
        list.forEach(sysRoute -> {
            if (sysRoute.getFId() == null) {
                JSONObject item = of(sysRoute);
                item.put("checked", sysRouteList.contains(sysRoute));
                jsonArray.add(item);
            }
        });

        jsonArray.forEach(item -> setChildren((JSONObject) item, list, sysRouteList));
        return jsonArray;
    }

    private void setChildren(JSONObject item, List<SysRoute> list, List<SysRoute> sysRouteList) {
        list.forEach(sysRoute -> {
            if (NumberUtil.equals(sysRoute.getFId(), item.getInteger("id"))) {
                JSONArray children = item.getJSONArray("children");
                JSONObject child = of(sysRoute);
                child.put("checked", sysRouteList.contains(sysRoute));
                children.add(child);
                setChildren(child, list, sysRouteList);
            }
        });
    }

    private JSONObject of(SysRoute sysRoute) {
        JSONObject item = new JSONObject();
        item.put("type", sysRoute.getType());
        item.put("name", sysRoute.getName());
        item.put("id", sysRoute.getId());
        item.put("children", new JSONArray());
        return item;
    }
}
