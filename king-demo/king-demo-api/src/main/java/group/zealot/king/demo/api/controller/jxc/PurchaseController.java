package group.zealot.king.demo.api.controller.jxc;

import group.zealot.king.core.zt.entity.jxc.JxcPurchase;
import group.zealot.king.demo.api.config.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/jxc/purchase")
public class PurchaseController extends BaseController<JxcPurchase, Long> {
}
