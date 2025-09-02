package com.cow.horse.service.goods_management.goods;

import com.cow.horse.domain.goods_management.goods.Goods;
import com.cow.horse.domain.inventory_management.detail_store_goods.DetailStoreGoods;
import com.cow.horse.domain.inventory_management.notice.NoticeIn;
import com.cow.horse.domain.inventory_management.notice.NoticeOut;
import com.cow.horse.qo.goods_management.goods.QueryGoods;
import com.cow.horse.qo.goods_management.goods_store.QueryGoodsStore;
import com.cow.horse.qo.goods_management.statistic_sale.QueryStatisticSale;
import com.cow.horse.qo.inventory_management.notice.QueryNoticeIn;
import com.cow.horse.qo.inventory_management.notice.QueryNoticeOut;
import com.cow.horse.vo.detail_store_goods.notice.NoticeInNotNormalVo;
import com.cow.horse.vo.goods.GoodsListVo;
import com.cow.horse.vo.goods_management.goods_store.GoodsStoreVo;
import com.cow.horse.vo.statistics.sale_management.SalesStatisticsVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface IGoodsService extends IService<Goods> {
    /**
     * 分页查询出数据
     * @param qo
     * @return
     */
    Page<GoodsListVo> queryPageByQo(QueryGoods qo);

    void saveGoods(Goods goods, String token);

    void upOrdown(Long gid, String state,String token);

    void updateGoods(Goods goods, String token);

    List<Map<String, Object>> selected_goodsAll();

    List<Map<String, Object>> selected_storeAll();


    void returnGoods(DetailStoreGoods detailStoreGoods, String token);

    Page<GoodsStoreVo> queryPageGoodsStore(QueryGoodsStore qo);

    GoodsStoreVo queryGoodsStoreById(Long id);

    void updateInventory(GoodsStoreVo vo);

    Page<NoticeIn> queryPageNoticeIn(QueryNoticeIn qo);

    Page<NoticeOut> queryPageNoticeOut_shelves(QueryNoticeOut qo);

    void saveOut_shelves(DetailStoreGoods detailStoreGoods,String token);

    SalesStatisticsVo queryPageStatisticSaleByQo(QueryStatisticSale qo);

    Page<NoticeInNotNormalVo> queryPageNoticeOut_untreated(QueryNoticeOut qo);

    void resolveOutUntreatedForm(NoticeInNotNormalVo vo, String token);
}
