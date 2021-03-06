package group.zealot.king.core.zt.entity.jxc;

import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 进销存--供应商
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class JxcSupplier extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String name;
    @Column(length = 50)
    private String phoneNumber;
    @Column(length = 200)
    private String address;
}
