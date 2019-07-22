package group.zealot.king.core.db.mybatis.system.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.util.NumberUtil;
import group.zealot.king.core.db.mybatis.base.BaseService;
import group.zealot.king.core.zt.mif.entity.system.*;
import group.zealot.king.core.zt.mif.service.system.SysAuthService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static group.zealot.king.core.db.mybatis.Daos.*;

@Service
public class SysAuthServiceImpl extends BaseService implements SysAuthService {

    @Override
    public List<SysRoleData> getSysRoleData(Long sysUserId) {
        SysAuth vo = new SysAuth();
        vo.setSysUserId(sysUserId);
        List<SysAuth> list = sysAuthDao.getList(vo);

        List<SysRoleData> sysRoleDataList = new ArrayList<>();
        for (SysAuth item : list) {
            if (item.getSysRoleDataId() != null) {
                SysRoleData sysRoleData = sysRoleDataDao.getById(item.getSysRoleDataId());
                sysRoleDataList.add(sysRoleData);
            }
        }
        return sysRoleDataList;
    }

    @Override
    public List<SysRoleRoute> getSysRoleRoute(Long sysUserId) {
        SysAuth vo = new SysAuth();
        vo.setSysUserId(sysUserId);
        List<SysAuth> list = sysAuthDao.getList(vo);

        List<SysRoleRoute> sysRoleRouteList = new ArrayList<>();
        for (SysAuth item : list) {
            if (item.getSysRoleRouteId() != null) {
                SysRoleRoute sysRoleRoute = sysRoleRouteDao.getById(item.getSysRoleRouteId());
                sysRoleRouteList.add(sysRoleRoute);
            }
        }
        return sysRoleRouteList;
    }

    @Override
    public List<SysRoute> getSysRoute(Long sysRoleRouteId) {
        SysAuth vo = new SysAuth();
        vo.setSysRoleRouteId(sysRoleRouteId);
        List<SysAuth> sysAuthList = sysAuthDao.getList(vo);

        List<SysRoute> sysRouteList = new ArrayList<>();
        for (SysAuth item : sysAuthList) {
            if (item.getSysRouteId() != null) {
                SysRoute sysRoute = sysRouteDao.getById(item.getSysRouteId());
                sysRouteList.add(sysRoute);
            }
        }
        return sysRouteList;
    }

    @Override
    public List<SysData> getSysData(Long sysRoleDataId) {
        SysAuth vo = new SysAuth();
        vo.setSysRoleDataId(sysRoleDataId);
        List<SysAuth> list = sysAuthDao.getList(vo);

        List<SysData> sysDataList = new ArrayList<>();
        for (SysAuth item : list) {
            if (item.getSysDataId() != null) {
                SysData sysData = sysDataDao.getById(item.getSysDataId());
                sysDataList.add(sysData);
            }
        }
        return sysDataList;
    }

    @Override
    public JSONArray getRoute(Long sysUserId) {
        JSONArray jsonArray = new JSONArray();
        List<SysRoleRoute> sysRoleRouteList = getSysRoleRoute(sysUserId);
        List<SysRoute> list = new ArrayList<>();

        sysRoleRouteList.forEach(sysRoleRoute -> list.addAll(getSysRoute(sysRoleRoute.getId())));

        list.forEach(sysRoute -> {
            if (sysRoute.getFId() == null) {
                JSONObject item = new JSONObject();
                item.put("name", sysRoute.getName());
                item.put("id", sysRoute.getId());
                item.put("children", new JSONArray());
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
                JSONObject child = new JSONObject();
                child.put("name", sysRoute.getName());
                child.put("id", sysRoute.getId());
                child.put("children", new JSONArray());
                children.add(child);
                setChildren(child, list);
            }
        });
    }
}
