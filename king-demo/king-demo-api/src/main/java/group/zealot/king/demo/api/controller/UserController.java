package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.zt.mif.entity.system.SysUser;
import group.zealot.king.demo.api.config.BaseController;
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
                Funcation.NotNull(page, "page为空");
                Funcation.NotNull(limit, "limit为空");

                PageRequest<SysUser> pageRequest = new PageRequest<>();
                pageRequest.setPage(page);
                pageRequest.setLimit(limit);
                SysUser filters = new SysUser();
                filters.setUsername(username);
                filters.setLevel(level);
                pageRequest.setFilters(filters);
                Page<SysUser> page = sysUserService.pageQuery(pageRequest);

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
                Funcation.NotNull(id, "id为空");

                SysUser sysUser = sysUserService.getById(id);
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
                Funcation.NotNull(username, "username为空");
                Funcation.NotNull(password, "password为空");
                Funcation.NotNull(status, "status为空");
                Funcation.NotNull(level, "level为空");
                Funcation.NotNull(remark, "remark为空");

                Funcation.IsNull(sysUserService.getByUsername(username),"该用户名已存在");


                SysUser sysUser = sysUserService.getById(id);
                JSONObject data = new JSONObject();
                data.put("vo", sysUser);
                resultJson.set(data);
            }
        }.result();
    }
}
