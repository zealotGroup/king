package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.zt.dbif.service.jxc.JxcLableService;
import group.zealot.king.core.zt.entity.jxc.JxcLable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.jpa.Repositorys.jxcLableRepository;
import static group.zealot.king.core.db.mybatis.Mappers.jxcLableMapper;

@Service
public class JxcLableServiceImpl extends BaseServiceImpl<JxcLable, Long> implements JxcLableService {
}
