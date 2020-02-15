package group.zealot.king.core.zt.entity.jxc;

import group.zealot.king.core.zt.entity.BaseEntity;
import group.zealot.king.core.zt.entity.jxc.enums.PurchaseSalesTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 进销存--销售记录
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class JxcSales extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String name;
    @Column(length = 20)
    private Long price;//价格
    @Column(length = 20)
    private Long size;//数量
    @Column(nullable = false, length = 2)
    private PurchaseSalesTypeEnum type;//进货、售货
    @Column(length = 20)
    private Long channelId;//渠道
}
