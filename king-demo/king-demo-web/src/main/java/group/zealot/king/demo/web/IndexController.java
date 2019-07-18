package group.zealot.king.demo.web;

import group.zealot.king.core.shiro.exception.ShiroException;
import group.zealot.king.core.shiro.realm.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static group.zealot.king.core.zt.mif.Services.*;

@Controller
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ShiroService shiroService;

    @RequestMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("id", sysIdService.getId());
        model.addAttribute("sessionId", SecurityUtils.getSubject().getSession().getId());
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login(Model model, String username, String password) {
        model.addAttribute("id", sysIdService.getId());
        try {
            if (!shiroService.isAuthenticated()) {
                shiroService.login(username, password);
            }
            model.addAttribute("sessionId", SecurityUtils.getSubject().getSession().getId());
        } catch (ShiroException e) {
            logger.error("登录异常", e);
        }
        return "index";
    }

    /**
     * shiro 配置了logout，此方法不会进入
     */
    @Deprecated
    @RequestMapping(value = "/logout")
    public String logout(Model model) {
        try {
            if (!shiroService.isAuthenticated()) {
                shiroService.logout();
            }
            model.addAttribute("id", sysIdService.getId());
            model.addAttribute("sessionId", SecurityUtils.getSubject().getSession().getId());
        } catch (ShiroException e) {
            logger.error("登录异常", e);
        }

        return "index";
    }
}
