package com.cow.horse.service.inventory_management.store;

import com.cow.horse.domain.inventory_management.store.GoodsStore;
import com.cow.horse.qo.inventory_management.store.QueryDetailStorageSituation;
import com.cow.horse.qo.inventory_management.store.QueryStorageSituation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface IGoodsStoreService extends IService<GoodsStore> {


    Long storeUsed(Long id);

    Long getResidueNumByGoodsId(Long id);

    void goodsInStore(Long goodsId, Long goodsNum,Long storeId);

    void goodsOutStore(Long goodsId, Long goodsNum, Long storeId);

    Map<String, Object> queryPageStorageSituationByQo(QueryStorageSituation qo);

    Map<String, Object> queryStoreGoodsByStoreId(QueryDetailStorageSituation qo);
}
