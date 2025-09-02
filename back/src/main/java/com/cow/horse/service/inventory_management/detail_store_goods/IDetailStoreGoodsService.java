package com.cow.horse.service.inventory_management.detail_store_goods;

import com.cow.horse.domain.inventory_management.detail_store_goods.DetailStoreGoods;
import com.cow.horse.qo.inventory_management.detail_store_goods.QueryDetailStoreGoods;
import com.cow.horse.qo.inventory_management.detail_store_goods.QueryDetailStoreGoodsOut;
import com.cow.horse.vo.detail_store_goods.DetailStoreGoodsOutVo;
import com.cow.horse.vo.detail_store_goods.DetailStoreGoodsVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface IDetailStoreGoodsService extends IService<DetailStoreGoods> {


    void saveIn(DetailStoreGoods detailStoreGoods, String token);

    Page<DetailStoreGoodsVo> queryPageByQoIn(QueryDetailStoreGoods qo);

    void delIn(String cn);

    Page<DetailStoreGoodsOutVo> queryPageByQoOut(QueryDetailStoreGoodsOut qo);

    Map<String ,Object> initOutOptions();

    List<Map<String,Object>> changeOutGoods(Long gid);

    List<Map<String,Object>> changeOutStore(Long storeId);

    DetailStoreGoodsOutVo queryOutGoods(Long goodsId, Long storeId);

    void saveOut(DetailStoreGoods detailStoreGoods, String token);
}
