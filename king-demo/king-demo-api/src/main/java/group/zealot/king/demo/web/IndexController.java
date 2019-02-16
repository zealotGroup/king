package group.zealot.king.demo.web;

import group.zealot.king.core.zt.mybatis.system.service.SysIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    private SysIdService sysIdService;

    @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("id", sysIdService.getId());
        return "index";
    }
}
