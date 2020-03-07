package group.zealot.king.core.zt.entity.wx;

import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 进销存--客户
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"openid"})})
public class WxUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String openid;
    @Column(length = 50)
    private String nickName;
    @Column(length = 200)
    private String avatarUrl;
    @Column(length = 200)
    private String phoneNumber;
}
