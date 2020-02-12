package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.zt.dbif.service.jxc.JxcGoodsService;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.jpa.Repositorys.*;
import static group.zealot.king.core.db.mybatis.Mappers.*;

@Service
public class JxcGoodsServiceImpl extends BaseServiceImpl<JxcGoods, Long> implements JxcGoodsService {

    @Override
    protected BaseMapper<JxcGoods, Long> getBaseMapper() {
        return jxcGoodsMapper;
    }

    @Override
    protected JpaRepository<JxcGoods, Long> getJpaRepository() {
        return jxcGoodsRepository;
    }
}
