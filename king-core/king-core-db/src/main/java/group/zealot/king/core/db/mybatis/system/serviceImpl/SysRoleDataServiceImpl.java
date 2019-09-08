package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.core.db.mybatis.base.BaseDao;
import group.zealot.king.core.db.mybatis.base.BaseServiceAbs;
import group.zealot.king.core.zt.mif.entity.system.SysData;
import group.zealot.king.core.zt.mif.entity.system.SysRoleData;
import group.zealot.king.core.zt.mif.service.system.SysRoleDataService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static group.zealot.king.core.db.mybatis.Daos.sysRoleDataDao;


@Service
public class SysRoleDataServiceImpl extends BaseServiceAbs<SysRoleData > implements SysRoleDataService {

    @Override
    protected BaseDao<SysRoleData, Long> getBaseDao() {
        return sysRoleDataDao;
    }

    @Override
    protected SysRoleData getE(Long primaryKey ) {
        SysRoleData vo = new SysRoleData();
        vo.setId(primaryKey);
        return vo;
    }
}
