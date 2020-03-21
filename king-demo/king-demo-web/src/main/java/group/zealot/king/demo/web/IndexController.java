package group.zealot.king.demo.web;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static group.zealot.king.core.zt.dbif.Services.sysIdService;


@Controller
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("id", sysIdService.getId());
        model.addAttribute("sessionId", SecurityUtils.getSubject().getSession().getId());
        return "index";
    }

}
