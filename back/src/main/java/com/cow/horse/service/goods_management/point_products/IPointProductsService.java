package com.cow.horse.service.goods_management.point_products;

import com.cow.horse.domain.goods_management.point_products.PointProducts;
import com.cow.horse.qo.goods_management.point_products.QueryPointProducts;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface IPointProductsService  extends IService<PointProducts> {
    Page<PointProducts> queryPageByQo(QueryPointProducts qo);

    List<Map<String, Object>> queryOptionGoods();

    void savePointGoods(PointProducts pointProducts,String token);

    void updatePointGoods(PointProducts pointProducts, String token);

    void del(Long id);
}
