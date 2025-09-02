package com.cow.horse.controller.goods_management.point_products;

import com.cow.horse.common.sercurity.annotation.HasPermisson;
import com.cow.horse.common.web.response.JsonResult;
import com.cow.horse.domain.goods_management.point_products.PointProducts;
import com.cow.horse.service.goods_management.point_products.IPointProductsService;
import com.cow.horse.qo.goods_management.point_products.QueryPointProducts;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/goods_management/point_products")
public class PointProductController {
    @Autowired
    private IPointProductsService pointProductsService;
    /*查询信息*/
    @HasPermisson("goods_management:point_products:list")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QueryPointProducts qo) {
        Page<PointProducts> page = pointProductsService.queryPageByQo(qo);
        return JsonResult.success(page);
    }

    @GetMapping("/del")
    public JsonResult del(Long id) {
        pointProductsService.del(id);
        return JsonResult.success();
    }
    @GetMapping("/queryOptionGoods")
    public JsonResult queryOptionGoods() {
        List<Map<String,Object>> list=pointProductsService.queryOptionGoods();
        return JsonResult.success(list);
    }

    @PostMapping("/savePointGoods")
    public JsonResult savePointGoods(PointProducts pointProducts, HttpServletRequest request) {
         pointProductsService.savePointGoods(pointProducts,(String) request.getHeader("token"));
        return JsonResult.success();
    }
    @GetMapping("/queryPointGoodsById")
    public JsonResult queryPointGoodsById(Long goodsId) {
        PointProducts pointProducts = pointProductsService.getOne(new QueryWrapper<PointProducts>().eq("goods_id", goodsId));
        return JsonResult.success(pointProducts);
    }

    @PostMapping("/updatePointGoods")
    public JsonResult updatePointGoods(PointProducts pointProducts, HttpServletRequest request) {
        pointProductsService.updatePointGoods(pointProducts,(String) request.getHeader("token"));
        return JsonResult.success();
    }

}
