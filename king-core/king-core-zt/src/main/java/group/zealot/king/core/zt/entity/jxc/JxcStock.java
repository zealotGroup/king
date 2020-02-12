package group.zealot.king.core.zt.entity.jxc;

import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 进销存--库存
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"goodsId"})})
public class JxcStock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private Long goodsId;
    @Column(length = 20)
    private Long totalSize;//累计进货总数
    @Column(length = 20)
    private Long totalSales;//累计销售总数
    @Column(length = 20)
    private Long currentSize;//当前库存总数
    @Column(length = 100)
    private String unit;

    @Column
    private LocalDateTime updateTime;

    @PrePersist
    protected void prePersist() {
        this.updateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 进货调用
     *
     * @param number 进货数量
     */
    public void plus(Long number) {
        this.totalSales += number;
        this.currentSize += number;
    }

    /**
     * 销货调用
     *
     * @param number 销货数量
     */
    public void reduce(Long number) {
        this.totalSales += number;
        this.currentSize -= number;
    }
}