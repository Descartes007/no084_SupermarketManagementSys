package com.cow.horse.controller.goods_management.statistic_sale;

import com.cow.horse.common.sercurity.annotation.HasPermisson;
import com.cow.horse.common.web.response.JsonResult;
import com.cow.horse.service.goods_management.goods.IGoodsService;
import com.cow.horse.vo.statistics.sale_management.SalesStatisticsVo;
import com.cow.horse.qo.goods_management.statistic_sale.QueryStatisticSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/goods_management/statistic_sale")
public class StatisticSaleController {
    @Autowired
    private IGoodsService goodsService;

    @HasPermisson("goods_management:statistic_sale:list")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QueryStatisticSale qo) {
        SalesStatisticsVo vo = goodsService.queryPageStatisticSaleByQo(qo);
        return JsonResult.success(vo);
    }

}
