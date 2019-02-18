package group.zealot.king.demo.api.controller;

import group.zealot.king.core.zt.mybatis.system.service.SysIdService;
import group.zealot.king.core.zt.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @Autowired
    private SysIdService sysIdService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping
    public String index() {
        redisUtil.redisTemplate().opsForValue().setIfAbsent("size", 0);
        Long size = redisUtil.redisTemplate().opsForValue().increment("size");
        return "size:" + size;// + sysIdService.getId();
    }
}
