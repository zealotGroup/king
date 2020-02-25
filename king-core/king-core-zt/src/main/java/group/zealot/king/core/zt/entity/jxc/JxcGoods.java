package group.zealot.king.core.zt.entity.jxc;

import group.zealot.king.core.zt.entity.BaseEntity;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import group.zealot.king.core.zt.entity.admin.AdminPicture;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 进销存--商品
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class JxcGoods extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String name;
    @Column
    private BigDecimal price;
    @Column(length = 20)
    private Long priceUnitId;
    @Column(length = 20)
    private Long sizeUnitId;

    @Transient
    private List<AdminLable> lableList;//页面展示字段
    @Transient
    private List<Long> lableIds;//mybatis筛选条件
    @Transient
    private String lableId;//前端接受数组
    @Transient
    private List<AdminPicture> pictureList;//页面展示字段

    @Transient
    private String priceUnitName;
    @Transient
    private String sizeUnitName;
}
