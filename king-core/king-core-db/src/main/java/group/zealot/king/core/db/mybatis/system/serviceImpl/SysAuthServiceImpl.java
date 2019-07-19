package group.zealot.king.core.db.mybatis.system.serviceImpl;

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
    public List<SysRoleData> getRoleData(Long sysUserId) {
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
    public List<SysRoleRoute> getRoleRoute(Long sysUserId) {
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
    public List<SysRoute> getRoute(Long sysRoleRouteId) {
        SysAuth vo = new SysAuth();
        vo.setSysUserId(sysRoleRouteId);
        List<SysAuth> list = sysAuthDao.getList(vo);

        List<SysRoute> sysRouteList = new ArrayList<>();
        for (SysAuth item : list) {
            if (item.getSysRouteId() != null) {
                SysRoute sysRoute = sysRouteDao.getById(item.getSysRouteId());
                sysRouteList.add(sysRoute);
            }
        }
        return sysRouteList;
    }

    @Override
    public List<SysData> getData(Long sysRoleDataId) {
        SysAuth vo = new SysAuth();
        vo.setSysUserId(sysRoleDataId);
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
}
