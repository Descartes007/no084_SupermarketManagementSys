package com.cow.horse.controller.inventory_management.store;

import com.cow.horse.common.sercurity.annotation.HasPermisson;
import com.cow.horse.common.web.response.JsonResult;
import com.cow.horse.qo.inventory_management.store.QueryDetailStorageSituation;
import com.cow.horse.qo.inventory_management.store.QueryStorageSituation;
import com.cow.horse.service.inventory_management.store.IGoodsStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Validated
@RequestMapping("/inventory_management/store/storage_situation")
public class StorageSituationController {
    @Autowired
    private IGoodsStoreService goodsStoreService;

    @HasPermisson("inventory_management:store:storage_situation")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(QueryStorageSituation qo) {
        Map<String, Object> map = goodsStoreService.queryPageStorageSituationByQo(qo);
        return JsonResult.success(map);

    }
    @HasPermisson("inventory_management:store:storage_situation")
    @PostMapping("/queryStoreGoodsByStoreId")
    public JsonResult queryStoreGoodsByStoreId(QueryDetailStorageSituation qo) {
        Map<String, Object> map = goodsStoreService.queryStoreGoodsByStoreId(qo);
        return JsonResult.success(map);

    }

}
