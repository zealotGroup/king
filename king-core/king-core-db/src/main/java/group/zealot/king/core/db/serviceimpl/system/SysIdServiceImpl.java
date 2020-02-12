package group.zealot.king.core.db.serviceimpl.system;

import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.zt.entity.system.SysId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import group.zealot.king.core.zt.dbif.service.system.SysIdService;
import org.springframework.transaction.annotation.Transactional;


import static group.zealot.king.core.db.mybatis.Mappers.*;
import static group.zealot.king.core.db.jpa.Repositorys.*;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@Service
public class SysIdServiceImpl extends BaseServiceImpl<SysId, Long> implements SysIdService {

    @Transactional(propagation = NOT_SUPPORTED)
    public Long getId() {
        SysId sysId = new SysId();
        insert(sysId);
        return sysId.getId();
    }
}
