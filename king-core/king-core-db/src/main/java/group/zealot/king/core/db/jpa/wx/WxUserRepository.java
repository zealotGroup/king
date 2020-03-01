package group.zealot.king.core.db.jpa.wx;

import group.zealot.king.core.zt.entity.wx.WxUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zealot
 * @date 2019/11/23 11:52
 */
public interface WxUserRepository extends JpaRepository<WxUser, Long> {
}
