package group.zealot.king.demo.api.controller;

import group.zealot.king.core.zt.dbif.service.BaseService;
import group.zealot.king.core.zt.entity.system.SysRoleData;
import group.zealot.king.demo.api.config.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.sysRoleDataService;


@RestController
@RequestMapping("/roleData")
public class RoleDataController extends BaseController<SysRoleData, Long> {

    @Override
    protected BaseService<SysRoleData, Long> getBaseService() {
        return sysRoleDataService;
    }
}
