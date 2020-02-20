package group.zealot.king.core.zt.entity.jxc;

import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 进销存--销售记录
 */
@Getter
@Setter
@Entity
@Table
public class JxcSales extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private Long goodsId;//商品ID
    @Column(length = 20)
    private Long custId;//供应商ID
    @Column(length = 20)
    private Long price;//总价格
    @Column(length = 20)
    private Long priceUnitId;//单位
    @Column(length = 20)
    private Long size;//数量
    @Column(length = 20)
    private Long sizeUnitId;//单位

    @Transient
    private String goodsName;
    @Transient
    private String custName;
    @Transient
    private String priceUnitName;
    @Transient
    private String sizeUnitName;

}
