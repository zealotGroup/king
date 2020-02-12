package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.zt.dbif.service.jxc.JxcGoodsLableService;
import group.zealot.king.core.zt.entity.jxc.JxcGoodsLable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.jpa.Repositorys.jxcGoodsLableRepository;
import static group.zealot.king.core.db.mybatis.Mappers.jxcGoodsLableMapper;

@Service
public class JxcGoodsLableServiceImpl extends BaseServiceImpl<JxcGoodsLable, Long> implements JxcGoodsLableService {

    @Override
    protected BaseMapper<JxcGoodsLable, Long> getBaseMapper() {
        return jxcGoodsLableMapper;
    }

    @Override
    protected JpaRepository<JxcGoodsLable, Long> getJpaRepository() {
        return jxcGoodsLableRepository;
    }
}
