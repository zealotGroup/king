package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.zt.dbif.service.jxc.JxcChannelService;
import group.zealot.king.core.zt.entity.jxc.JxcChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.jpa.Repositorys.jxcChannelRepository;
import static group.zealot.king.core.db.mybatis.Mappers.jxcChannelMapper;

@Service
public class JxcChannelServiceImpl extends BaseServiceImpl<JxcChannel, Long> implements JxcChannelService {
}
