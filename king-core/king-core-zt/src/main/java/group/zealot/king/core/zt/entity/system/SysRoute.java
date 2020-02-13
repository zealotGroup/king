package group.zealot.king.core.zt.entity.system;

import group.zealot.king.base.util.NumberUtil;
import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class SysRoute extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String name;
    @Column(length = 2, columnDefinition = "int default 0")
    private RouteTypeEnum type;//0 表示菜单 1 表示按钮等页面内权限
    @Column(length = 4, columnDefinition = "int default 0")
    private Integer seq;//数字越小，越靠前（0最小）
    @Column(length = 20)
    private Long fId;

    @Override
    public boolean equals(Object o) {
        if (o instanceof SysRoute) {
            return NumberUtil.equals(((SysRoute) o).getId(), this.id);
        }
        return false;
    }
}
