package group.zealot.king.core.db.jpa;

import group.zealot.king.core.db.jpa.admin.*;
import group.zealot.king.core.db.jpa.jxc.*;
import group.zealot.king.core.db.jpa.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zealot
 * @date 2020/2/9 12:41
 */
@Component
public class Repositorys {
    public static SysAuthRepository sysAuthRepository;
    public static SysDataRepository sysDataRepository;
    public static SysIdRepository sysIdRepository;
    public static SysRoleDataRepository sysRoleDataRepository;
    public static SysRoleRouteRepository sysRoleRouteRepository;
    public static SysRouteRepository sysRouteRepository;
    public static SysUserRepository sysUserRepository;

    public static JxcCustRepository jxcCustRepository;
    public static JxcGoodsLableRepository jxcGoodsLableRepository;
    public static JxcGoodsRepository jxcGoodsRepository;
    public static JxcSalesRepository jxcSalesRepository;
    public static JxcStockRepository jxcStockRepository;
    public static JxcPurchaseRepository jxcPurchaseRepository;
    public static JxcSupplierRepository jxcSupplierRepository;
    public static JxcGoodsPictureRepository jxcGoodsPictureRepository;

    public static AdminUnitRepository adminUnitRepository;
    public static AdminLableRepository adminLableRepository;
    public static AdminPictureRepository adminPictureRepository;

    @Autowired(required = false)
    public void setJxcGoodsPictureRepository(JxcGoodsPictureRepository jxcGoodsPictureRepository) {
        Repositorys.jxcGoodsPictureRepository = jxcGoodsPictureRepository;
    }

    @Autowired(required = false)
    public void setSysAuthRepository(SysAuthRepository sysAuthRepository) {
        Repositorys.sysAuthRepository = sysAuthRepository;
    }

    @Autowired(required = false)
    public void setSysDataRepository(SysDataRepository sysDataRepository) {
        Repositorys.sysDataRepository = sysDataRepository;
    }

    @Autowired(required = false)
    public void setSysIdRepository(SysIdRepository sysIdRepository) {
        Repositorys.sysIdRepository = sysIdRepository;
    }

    @Autowired(required = false)
    public void setSysRoleDataRepository(SysRoleDataRepository sysRoleDataRepository) {
        Repositorys.sysRoleDataRepository = sysRoleDataRepository;
    }

    @Autowired(required = false)
    public void setSysRoleRouteRepository(SysRoleRouteRepository sysRoleRouteRepository) {
        Repositorys.sysRoleRouteRepository = sysRoleRouteRepository;
    }

    @Autowired(required = false)
    public void setSysRouteRepository(SysRouteRepository sysRouteRepository) {
        Repositorys.sysRouteRepository = sysRouteRepository;
    }

    @Autowired(required = false)
    public void setSysUserRepository(SysUserRepository sysUserRepository) {
        Repositorys.sysUserRepository = sysUserRepository;
    }

    @Autowired(required = false)
    public void setJxcCustRepository(JxcCustRepository jxcCustRepository) {
        Repositorys.jxcCustRepository = jxcCustRepository;
    }

    @Autowired(required = false)
    public void setJxcGoodsLableRepository(JxcGoodsLableRepository jxcGoodsLableRepository) {
        Repositorys.jxcGoodsLableRepository = jxcGoodsLableRepository;
    }

    @Autowired(required = false)
    public void setJxcGoodsRepository(JxcGoodsRepository jxcGoodsRepository) {
        Repositorys.jxcGoodsRepository = jxcGoodsRepository;
    }

    @Autowired(required = false)
    public void setJxcSalesRepository(JxcSalesRepository jxcSalesRepository) {
        Repositorys.jxcSalesRepository = jxcSalesRepository;
    }

    @Autowired(required = false)
    public void setJxcStockRepository(JxcStockRepository jxcStockRepository) {
        Repositorys.jxcStockRepository = jxcStockRepository;
    }

    @Autowired(required = false)
    public void setJxcPurchaseRepository(JxcPurchaseRepository jxcPurchaseRepository) {
        Repositorys.jxcPurchaseRepository = jxcPurchaseRepository;
    }

    @Autowired(required = false)
    public void setJxcSupplierRepository(JxcSupplierRepository jxcSupplierRepository) {
        Repositorys.jxcSupplierRepository = jxcSupplierRepository;
    }

    @Autowired(required = false)
    public void setAdminUnitRepository(AdminUnitRepository adminUnitRepository) {
        Repositorys.adminUnitRepository = adminUnitRepository;
    }

    @Autowired(required = false)
    public void setAdminLableRepository(AdminLableRepository adminLableRepository) {
        Repositorys.adminLableRepository = adminLableRepository;
    }

    @Autowired(required = false)
    public void setAdminPictureRepository(AdminPictureRepository adminPictureRepository) {
        Repositorys.adminPictureRepository = adminPictureRepository;
    }
}
