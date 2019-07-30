package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Constants;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.base.util.NumberUtil;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.zt.mif.entity.system.SysUser;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.LoginUtil;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.mif.Services.sysUserService;


@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @RequestMapping("list")
    public JSONObject list(Integer page, Integer limit, String level, String username) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(page, "page为空");
                Funcation.AssertNotNull(limit, "limit为空");

                PageRequest<SysUser> pageRequest = new PageRequest<>();
                pageRequest.setPage(page);
                pageRequest.setLimit(limit);
                SysUser filters = new SysUser();
                filters.setUsername(username);
                filters.setLevel(level);
                pageRequest.setFilters(filters);
                Page<SysUser> page = sysUserService.pageQuery(pageRequest);
                sysUserService.formater(page.getList());

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

                SysUser sysUser = sysUserService.getById(id);
                sysUserService.formater(sysUser);
                JSONObject data = new JSONObject();
                data.put("vo", sysUser);
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("add")
    public JSONObject add(String username, byte[] password, String status, String level, String remark) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(username, "username为空");
                Funcation.AssertNotNull(password, "password为空");
                Funcation.AssertNotNull(status, "status为空");
                Funcation.AssertNotNull(level, "level为空");
                Funcation.AssertNotNull(remark, "remark为空");

                Funcation.AssertIsNull(sysUserService.getByUsername(username), "该用户名已存在");
                SysUser vo = sysUserService.insert(username, password, status, level, remark, LoginUtil.getSysUserId());
                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, byte[] password, String status, String level, String remark) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");

                if ((password == null || password.length == 0) && StringUtil.isEmpty(status)
                        && StringUtil.isEmpty(level) && StringUtil.isEmpty(remark)) {
                    throw new BaseRuntimeException("没有可更新参数");
                }
                sysUserService.update(id, password, status, level, remark, null, LoginUtil.getSysUserId());
            }
        }.result();
    }

    @RequestMapping("del")
    public JSONObject del(Long id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");
                sysUserService.update(id, null, null, null, null, Constants.DELETE_Y, LoginUtil.getSysUserId());
            }
        }.result();
    }

    @RequestMapping("recover")
    public JSONObject recover(Long id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");
                SysUser old = sysUserService.getById(id);
                Funcation.AssertNotNull(old, "此id用户不存在为空");
                if (NumberUtil.equals(old.getIsDelete(),Constants.DELETE_N)){
                    throw new BaseRuntimeException("此id用户未删除，不能恢复");
                }
                sysUserService.update(id, null, null, null, null, Constants.DELETE_N, LoginUtil.getSysUserId());
            }
        }.result();
    }

    @RequestMapping("realDel")
    public JSONObject realDel(Long id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");
                sysUserService.realDel(id);
            }
        }.result();
    }
}
