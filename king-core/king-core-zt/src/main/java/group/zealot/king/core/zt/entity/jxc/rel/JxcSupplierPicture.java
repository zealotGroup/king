package group.zealot.king.core.zt.entity.jxc.rel;

import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 进销存--标签
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"supplierId", "pictureId"})})
public class JxcSupplierPicture extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private Long supplierId;
    @Column(length = 20)
    private Long pictureId;
}
