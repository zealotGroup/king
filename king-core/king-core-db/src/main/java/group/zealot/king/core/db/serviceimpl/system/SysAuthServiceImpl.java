package group.zealot.king.core.db.serviceimpl.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.util.NumberUtil;
import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.zt.entity.system.*;
import group.zealot.king.core.zt.dbif.service.system.SysAuthService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static group.zealot.king.core.db.serviceimpl.ServiceImpls.*;

@Service
public class SysAuthServiceImpl extends BaseServiceImpl<SysAuth, Long> implements SysAuthService {


    @Override
    public SysAuth getSysAuthRoleData(Long sysUserId) {
        SysAuth vo = new SysAuth();
        vo.setSysUserId(sysUserId);
        List<SysAuth> authList = getList(vo);

        for (SysAuth item : authList) {
            if (item.getSysRoleDataId() != null) {
                return item;
            }
        }
        throw new BaseRuntimeException("数据异常，此用户不存在数据角色");
    }

    @Override
    public SysAuth getSysAuthRoleRoute(Long sysUserId) {
        SysAuth vo = new SysAuth();
        vo.setSysUserId(sysUserId);
        List<SysAuth> list = getList(vo);

        for (SysAuth item : list) {
            if (item.getSysRoleRouteId() != null) {
                return item;
            }
        }
        throw new BaseRuntimeException("数据异常，此用户不存在路由角色");
    }

    @Override
    public SysRoleData getSysRoleData(Long sysUserId) {
        SysAuth sysAuth = getSysAuthRoleData(sysUserId);
        return sysRoleDataServiceImpl.getById(sysAuth.getSysRoleDataId());
    }

    @Override
    public SysRoleRoute getSysRoleRoute(Long sysUserId) {
        SysAuth sysAuth = getSysAuthRoleRoute(sysUserId);
        return sysRoleRouteServiceImpl.getById(sysAuth.getSysRoleRouteId());
    }

    @Override
    public List<SysRoute> getSysRoute(Long sysRoleRouteId) {
        SysAuth vo = new SysAuth();
        vo.setSysRoleRouteId(sysRoleRouteId);
        List<SysAuth> sysAuthList = getList(vo);
        List<SysRoute> sysRouteList = sysRouteServiceImpl.getList();

        List<SysRoute> list = new ArrayList<>();
        sysAuthList.forEach(sysAuth -> {
            if (sysAuth.getSysRouteId() != null) {
                sysRouteList.forEach(sysRoute -> {
                    if (NumberUtil.equals(sysAuth.getSysRouteId(), sysRoute.getId())) {
                        list.add(sysRoute);
                    }
                });
            }
        });
        return list;
    }

    @Override
    public List<SysData> getSysData(Long sysRoleDataId) {
        SysAuth vo = new SysAuth();
        vo.setSysRoleDataId(sysRoleDataId);
        List<SysAuth> sysAuthList = getList(vo);
        List<SysData> sysRouteList = sysDataServiceImpl.getList();

        List<SysData> list = new ArrayList<>();
        sysAuthList.forEach(sysAuth -> {
            if (sysAuth.getSysDataId() != null) {
                sysRouteList.forEach(sysData -> {
                    if (NumberUtil.equals(sysAuth.getSysDataId(), sysData.getId())) {
                        list.add(sysData);
                    }
                });
            }
        });
        return list;
    }

    @Override
    public JSONArray getRoute(Long sysUserId) {
        JSONArray jsonArray = new JSONArray();
        //获取当前用户 路由角色
        SysRoleRoute sysRoleRoute = getSysRoleRoute(sysUserId);
        //获取当前用户 路由权限
        List<SysRoute> list = getSysRoute(sysRoleRoute.getId());

        list.forEach(sysRoute -> {
            if (sysRoute.getFId() == null) {
                JSONObject item = of(sysRoute);
                jsonArray.add(item);
            }
        });

        jsonArray.forEach(item -> setChildren((JSONObject) item, list));
        return jsonArray;
    }

    private void setChildren(JSONObject item, List<SysRoute> list) {
        list.forEach(sysRoute -> {
            if (NumberUtil.equals(sysRoute.getFId(), item.getInteger("id"))) {
                JSONArray children = item.getJSONArray("children");
                JSONObject child = of(sysRoute);
                children.add(child);
                setChildren(child, list);
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
