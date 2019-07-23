package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.core.db.mybatis.base.BaseMapper;
import group.zealot.king.core.db.mybatis.base.BaseServiceImpl;
import group.zealot.king.core.zt.mif.entity.system.SysRoleData;
import group.zealot.king.core.zt.mif.service.system.SysRoleDataService;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.mybatis.Daos.sysRoleDataDao;


@Service
public class SysRoleDataServiceImpl extends BaseServiceImpl<SysRoleData, Long> implements SysRoleDataService {

    @Override
    protected BaseMapper<SysRoleData, Long> getBaseMapper() {
        return sysRoleDataDao;
    }
}
