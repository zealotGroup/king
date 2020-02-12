package group.zealot.king.core.db.serviceimpl.system;

import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.zt.entity.system.SysRoleData;
import group.zealot.king.core.zt.dbif.service.system.SysRoleDataService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.mybatis.Mappers.sysRoleDataMapper;
import static group.zealot.king.core.db.jpa.Repositorys.*;


@Service
public class SysRoleDataServiceImpl extends BaseServiceImpl<SysRoleData, Long> implements SysRoleDataService {
}
