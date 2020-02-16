package group.zealot.king.core.zt.entity.jxc;

import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 进销存--采购记录
 */
@Getter
@Setter
@Entity
@Table
public class JxcPurchase extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private Long goodsId;//商品ID
    @Column(length = 20)
    private Long supplierId;//供应商ID
    @Column(length = 20)
    private Long price;//总价格
    @Column(length = 20)
    private Long priceUnitId;//单位
    @Column(length = 20)
    private Long size;//数量
    @Column(length = 20)
    private Long sizeUnitId;//单位
}