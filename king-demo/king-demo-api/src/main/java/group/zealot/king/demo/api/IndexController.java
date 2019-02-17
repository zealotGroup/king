package group.zealot.king.demo.api;

import group.zealot.king.core.zt.mybatis.system.service.SysIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @Autowired
    private SysIdService sysIdService;

    @RequestMapping
    public String index() {
        return "id:123";// + sysIdService.getId();
    }
}
