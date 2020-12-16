package group.zealot.king.core.zt.entity.jxc;

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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}), @UniqueConstraint(columnNames = {"openid"})})
public class JxcCust extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String name;
    @Column(length = 50)
    private String phoneNumber;
    @Column(length = 200)
    private String address;

    //微信数据
    @Column(length = 200)
    private String openid;
    @Column(length = 50)
    private String nickName;
    @Column(length = 200)
    private String avatarUrl;

    @Transient
    private String sessionKey;
}
