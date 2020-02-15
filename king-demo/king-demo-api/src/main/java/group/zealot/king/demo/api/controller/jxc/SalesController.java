package group.zealot.king.demo.api.controller.jxc;

import group.zealot.king.core.zt.entity.jxc.JxcSales;
import group.zealot.king.demo.api.config.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/jxc/sales")
public class SalesController extends BaseController<JxcSales, Long> {
}
