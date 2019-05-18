package group.zealot.king.core.db.mybatis.system.dao.mapper;

import group.zealot.king.core.db.mybatis.base.BaseDao;
import group.zealot.king.core.db.mybatis.system.dao.SysUserDao;
import group.zealot.king.core.zt.mif.entity.system.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public class SysUserDaoMapper extends BaseDao<SysUser, Long> implements SysUserDao {

}
