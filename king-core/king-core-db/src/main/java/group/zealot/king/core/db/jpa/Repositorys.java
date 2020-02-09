package group.zealot.king.core.db.jpa;

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
}
