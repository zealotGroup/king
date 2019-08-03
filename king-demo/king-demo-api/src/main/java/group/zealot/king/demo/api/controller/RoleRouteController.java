package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Constants;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.base.util.NumberUtil;
import group.zealot.king.core.zt.mif.entity.system.SysRoleRoute;
import group.zealot.king.core.zt.mif.entity.system.SysUser;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.LoginUtil;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.mif.Services.*;


@RestController
@RequestMapping("/roleRoute")
public class RoleRouteController extends BaseController {

    @RequestMapping("list")
    public JSONObject list(Integer page, Integer limit) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(page, "page为空");
                Funcation.AssertNotNull(limit, "limit为空");

                PageRequest<SysRoleRoute> pageRequest = new PageRequest<>();
                pageRequest.setPage(page);
                pageRequest.setLimit(limit);
                SysRoleRoute filters = new SysRoleRoute();
                pageRequest.setFilters(filters);
                Page<SysRoleRoute> page = sysRoleRouteService.pageQuery(pageRequest);
                sysRoleRouteService.formater(page.getList());

                JSONObject data = new JSONObject();
                data.put("total", page.getCount());
                data.put("list", page.toJSONArray());
                resultJson.set(data);
            }
        }.result();
    }


    @RequestMapping("get")
    public JSONObject get(Long id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");

                SysRoleRoute sysRoleRoute = sysRoleRouteService.getById(id);
                sysRoleRouteService.formater(sysRoleRoute);
                JSONObject data = new JSONObject();
                data.put("vo", sysRoleRoute);
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("add")
    public JSONObject add(String username, byte[] password, String status, String level, String remark,
                          Long roleDataId, Long roleRouteId) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(username, "username为空");
                Funcation.AssertNotNull(password, "password为空");
                Funcation.AssertNotNull(status, "status为空");
                Funcation.AssertNotNull(level, "level为空");
                Funcation.AssertNotNull(remark, "remark为空");
                Funcation.AssertNotNull(roleDataId, "roleDataId为空");
                Funcation.AssertNotNull(roleRouteId, "roleRouteId为空");

                Funcation.AssertIsNull(sysUserService.getByUsername(username), "该用户名已存在");
                SysUser vo = sysUserService.insert(username, password, status, level, remark, roleDataId, roleRouteId, LoginUtil.getSysUserId());
                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, byte[] password, String status, String level, String remark,
                             Long roleDataId, Long roleRouteId) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");
                SysUser oldSysUser = sysUserService.getById(id);
                Funcation.AssertNotNull(oldSysUser, "该ID用户不存在");

                sysUserService.update(id, password, status, level, remark, null, roleDataId, roleRouteId, LoginUtil.getSysUserId());
            }
        }.result();
    }

    @RequestMapping("del")
    public JSONObject del(Long id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");
                SysUser oldSysUser = sysUserService.getById(id);
                Funcation.AssertNotNull(oldSysUser, "该ID用户不存在");

                sysUserService.update(id, null, null, null, null, Constants.DELETE_Y, null
                        , null, LoginUtil.getSysUserId());
            }
        }.result();
    }

    @RequestMapping("recover")
    public JSONObject recover(Long id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");
                SysUser oldSysUser = sysUserService.getById(id);
                Funcation.AssertNotNull(oldSysUser, "此id用户不存在为空");
                if (NumberUtil.equals(oldSysUser.getIsDelete(), Constants.DELETE_N)) {
                    throw new BaseRuntimeException("此id用户未删除，不能恢复");
                }
                sysUserService.update(id, null, null, null, null, Constants.DELETE_N, null, null, LoginUtil.getSysUserId());
            }
        }.result();
    }

    @RequestMapping("realDel")
    public JSONObject realDel(Long id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");
                SysUser oldSysUser = sysUserService.getById(id);
                Funcation.AssertNotNull(oldSysUser, "此id用户不存在为空");
                sysUserService.realDel(id);
            }
        }.result();
    }
}
