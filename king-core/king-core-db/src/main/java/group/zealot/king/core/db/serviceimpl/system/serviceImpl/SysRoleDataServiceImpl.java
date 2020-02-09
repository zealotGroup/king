package group.zealot.king.core.db.serviceimpl.system.serviceImpl;

import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.zt.entity.system.SysRoleData;
import group.zealot.king.core.zt.dbif.service.system.SysRoleDataService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.mybatis.Mappers.sysRoleDataDaoMapper;
import static group.zealot.king.core.db.jpa.Repositorys.*;


@Service
public class SysRoleDataServiceImpl extends BaseServiceImpl<SysRoleData, Long> implements SysRoleDataService {

    @Override
    protected BaseMapper<SysRoleData, Long> getBaseMapper() {
        return sysRoleDataDaoMapper;
    }

    @Override
    protected JpaRepository<SysRoleData, Long> getJpaRepository() {
        return sysRoleDataRepository;
    }
}
