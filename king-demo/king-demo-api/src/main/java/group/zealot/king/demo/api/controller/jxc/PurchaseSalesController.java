package group.zealot.king.demo.api.controller.jxc;

import group.zealot.king.core.zt.entity.jxc.JxcPurchaseSales;
import group.zealot.king.demo.api.config.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/jxc/purchaseSales")
public class PurchaseSalesController extends BaseController<JxcPurchaseSales, Long> {
}
