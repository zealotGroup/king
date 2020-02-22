package group.zealot.king.core.zt.entity.jxc;

import group.zealot.king.core.zt.entity.BaseEntity;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 进销存--商品
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "sizeUnitId"})})
public class JxcGoods extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String name;
    @Column(length = 20)
    private Long price;
    @Column(length = 2)
    private Long priceUnitId;
    @Column(length = 2)
    private Long sizeUnitId;

    @Transient
    private List<AdminLable> lableList;
    @Transient
    private String priceUnitName;
    @Transient
    private String sizeUnitName;
    @Transient
    private List<Long> lableIds;
    @Transient
    private String lableId;//前端接受数组
}
