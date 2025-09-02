package com.cow.horse.controller.inventory_management.store;

import com.cow.horse.common.sercurity.annotation.HasPermisson;
import com.cow.horse.common.web.response.JsonResult;
import com.cow.horse.domain.inventory_management.store.Store;
import com.cow.horse.qo.inventory_management.store.QueryStore;
import com.cow.horse.service.inventory_management.store.IStoreService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/inventory_management/store")
public class StoreController {
    @Autowired
    private IStoreService storeService;
    /*保存仓库信息接口*/
    @HasPermisson("inventory_management:store:save")
    @PostMapping("/save")
    public JsonResult saveStore(Store store){
        storeService.saveStore(store);
        return JsonResult.success();
    }
    /*修改仓库接口*/
    @HasPermisson("inventory_management:store:update")
    @PostMapping("/update")
    public JsonResult updateStore(Store store){
        storeService.updateStore(store);
        return JsonResult.success();
    }
    /*停用仓库*/
    @HasPermisson("inventory_management:store:deactivate")
    @PostMapping("/deactivate")
    public JsonResult deactivate(Long sid){
        storeService.deactivate(sid);
        return JsonResult.success();
    }
    /*查询仓库信息*/
    @HasPermisson("inventory_management:store:list")
    @PostMapping("/list")
    public JsonResult list(QueryStore qo){
        return JsonResult.success(storeService.list( new QueryWrapper<Store>()
                .like(StringUtils.hasText(qo.getName()),"name",qo.getName())
                .eq(StringUtils.hasText(qo.getState()),"state",qo.getState())
        ));
    }

}
