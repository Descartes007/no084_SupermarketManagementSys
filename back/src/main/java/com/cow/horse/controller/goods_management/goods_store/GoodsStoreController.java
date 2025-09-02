package com.cow.horse.controller.goods_management.goods_store;

import com.cow.horse.common.sercurity.annotation.HasPermisson;
import com.cow.horse.common.web.response.JsonResult;
import com.cow.horse.qo.goods_management.goods_store.QueryGoodsStore;
import com.cow.horse.service.goods_management.goods.IGoodsService;
import com.cow.horse.vo.goods_management.goods_store.GoodsStoreVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/goods_management/goods_store")
public class GoodsStoreController {
    @Autowired
    private IGoodsService goodsService;
    /*查询信息*/
    @HasPermisson("goods_management:goods_store:list")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QueryGoodsStore qo) {
        Page<GoodsStoreVo> page = goodsService.queryPageGoodsStore(qo);
        return JsonResult.success(page);
    }

    @GetMapping("/queryGoodsStoreById")
    public JsonResult queryGoodsStoreById(Long id) {
        GoodsStoreVo vo= goodsService.queryGoodsStoreById(id);
        return JsonResult.success(vo);
    }

    @PostMapping("/updateInventory")
    public JsonResult updateInventory(GoodsStoreVo vo) {
         goodsService.updateInventory(vo);
        return JsonResult.success();
    }

}
