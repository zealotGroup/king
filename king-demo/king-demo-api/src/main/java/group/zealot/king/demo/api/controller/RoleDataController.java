package group.zealot.king.demo.api.controller;

import group.zealot.king.core.zt.mif.entity.system.SysRoleData;
import group.zealot.king.core.zt.mif.service.BaseService;
import group.zealot.king.demo.api.config.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.mif.Services.*;


@RestController
@RequestMapping("/roleData")
public class RoleDataController extends BaseController<SysRoleData> {

    @Override
    protected BaseService<SysRoleData> getBaseService() {
        return sysRoleDataService;
    }
}
