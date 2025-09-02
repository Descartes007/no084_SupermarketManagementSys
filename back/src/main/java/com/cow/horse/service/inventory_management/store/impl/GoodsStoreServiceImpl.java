package com.cow.horse.service.inventory_management.store.impl;

import com.cow.horse.common.exception.BusinessException;
import com.cow.horse.common.redis.service.RedisTemplateService;
import com.cow.horse.domain.goods_management.goods.Goods;
import com.cow.horse.domain.inventory_management.store.GoodsStore;
import com.cow.horse.domain.inventory_management.store.Store;
import com.cow.horse.mapper.inventory_management.store.GoodsStoreMapper;
import com.cow.horse.qo.inventory_management.store.QueryDetailStorageSituation;
import com.cow.horse.qo.inventory_management.store.QueryStorageSituation;
import com.cow.horse.service.goods_management.goods.IGoodsService;
import com.cow.horse.vo.statistics.storage_situation.DetailStorageSituationVo;
import com.cow.horse.vo.statistics.storage_situation.StorageSituationVo;
import com.cow.horse.service.inventory_management.detail_store_goods.IDetailStoreGoodsService;
import com.cow.horse.service.inventory_management.store.IGoodsStoreService;
import com.cow.horse.service.inventory_management.store.IStoreService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Transactional
public class GoodsStoreServiceImpl extends ServiceImpl<GoodsStoreMapper, GoodsStore> implements IGoodsStoreService {
    @Autowired
    private GoodsStoreMapper goodsStoreMapper;
    @Autowired
    private IDetailStoreGoodsService detailStoreGoodsService;
    @Autowired
    private RedisTemplateService redisTemplateService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IStoreService storeService;

    @Override
    public Long storeUsed(Long id) {
        return goodsStoreMapper.storeUsed(id);
    }

    @Override
    public Long getResidueNumByGoodsId(Long id) {
        return goodsStoreMapper.getResidueNumByGoodsId(id);
    }

//    @Override
//    public void downGoodsByGid(Long gid, String token) {
//        Employee employee = JSONObject.parseObject(redisTemplateService.getCacheObject(token), Employee.class);
//        QueryWrapper<GoodsStore> wrapper = new QueryWrapper<GoodsStore>()
//                .eq("goods_id", gid);
//        List<GoodsStore> list = super.list(wrapper);
//        if (list != null && list.size() > 0) {
//            DetailStoreGoods detailStoreGoods = new DetailStoreGoods();
//            detailStoreGoods.setCn(IdWorker.getIdStr());
//            detailStoreGoods.setCreateby(employee.getNickName());
//            detailStoreGoods.setCreateid(employee.getId());
//            detailStoreGoods.setCreateTime(new Date());
//            detailStoreGoods.setGoodsId(gid);
//            Goods goods = goodsService.getById(gid);
//            detailStoreGoods.setGoodsName(goods.getName());
//            detailStoreGoods.setGoodsPrice(goods.getPurchashPrice());
//            detailStoreGoods.setInfo("下架商品处理");
//            detailStoreGoods.setGoodsNum(goods.getResidueNum());
//            detailStoreGoods.setState(DetailStoreGoods.STATE_DOWN);
//            detailStoreGoods.setState1(DetailStoreGoods.STATE1_NORMAL);
//            detailStoreGoods.setType(DetailStoreGoods.TYPE_OUT);
//            detailStoreGoodsService.goodsDown(detailStoreGoods);
//            UpdateWrapper<GoodsStore> wrapper1 = new UpdateWrapper<>();
//            wrapper1.eq("goods_id", gid);
//            wrapper1.set("residue_num", 0);
//            super.update(wrapper1);
//        }
//    }

    @Override
    public void goodsInStore(Long goodsId, Long goodsNum, Long storeId) {
        QueryWrapper<GoodsStore> wrapper = new QueryWrapper<GoodsStore>().eq("goods_id", goodsId).eq("store_id", storeId);
        GoodsStore goodsStore1 = super.getOne(wrapper);
        if (goodsStore1 != null) {
            goodsStoreMapper.goodsInStore(goodsId, goodsNum, storeId);
        } else {
            GoodsStore goodsStore = new GoodsStore();
            goodsStore.setGoodsId(goodsId);
            goodsStore.setInNum(goodsNum);
            goodsStore.setResidueNum(goodsNum);
            goodsStore.setStoreId(storeId);
            Store one = storeService.getById(storeId);
            goodsStore.setStoreName(one.getName());
            super.save(goodsStore);
        }
    }

    @Override
    public void goodsOutStore(Long goodsId, Long goodsNum, Long storeId) {
        goodsStoreMapper.goodsOutStore(goodsId, goodsNum, storeId);
    }

    @Override
    public Map<String, Object> queryPageStorageSituationByQo(QueryStorageSituation qo) {
        HashMap<String, Object> map = new HashMap<>();
        Long totalStoreNum=goodsStoreMapper.totalStoreNum();
        map.put("totalStoreNum",totalStoreNum!=null?totalStoreNum:0L);
        Page<GoodsStore> goodsStorePage = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<GoodsStore> goodsStoreQueryWrapper = new QueryWrapper<GoodsStore>().select("store_id,store_name,SUM(residue_num) residue_num")
                .like(StringUtils.hasText(qo.getName()), "store_name", qo.getName())
                .groupBy("store_id", "store_name");
        super.page(goodsStorePage,goodsStoreQueryWrapper);
        Page<StorageSituationVo> situationVoPage = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        List<StorageSituationVo> vos = new ArrayList<>();
        for (GoodsStore record : goodsStorePage.getRecords()) {
            StorageSituationVo vo = new StorageSituationVo();
            vo.setStoreId(record.getStoreId());
            vo.setStoreName(record.getStoreName());
            vo.setResidueNum(record.getResidueNum());
            vos.add(vo);
        }
        situationVoPage.setRecords(vos);
        situationVoPage.setTotal(goodsStorePage.getTotal());
        map.put("page",situationVoPage);
        return map;
    }

    @Override
    public Map<String, Object> queryStoreGoodsByStoreId(QueryDetailStorageSituation qo) {
        Map<String, Object> map = new HashMap<>();
        Long totalStoreNum1=goodsStoreMapper.getTotalStoreNum1(qo.getStoreId());
        map.put("totalStoreNum1",totalStoreNum1);//该仓库的存储量

        QueryWrapper<GoodsStore> wrapper = new QueryWrapper<GoodsStore>()
                .gt("residue_num",0)
                .eq("store_id", qo.getStoreId());
        List<GoodsStore> list = super.list(wrapper);
        Set<Long> goodsIds = new HashSet<>();
        for (GoodsStore goodsStore : list) {
            goodsIds.add(goodsStore.getGoodsId());
        }
        if (goodsIds.size()<=0){
            throw new BusinessException("该仓库没有存放任何的商品");
        }
        List<Goods> goodsList = goodsService.listByIds(goodsIds);
        List<Map<String, Object>> optionsStoreGoods = new ArrayList<>();
        for (Goods goods : goodsList) {
            Map<String, Object> options = new HashMap<>();
            options.put("id",goods.getId());
            options.put("name",goods.getName());
            optionsStoreGoods.add(options);
        }
        map.put("optionsStoreGoods",optionsStoreGoods);//选择框
        Page<GoodsStore> goodsStorePage = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<GoodsStore> goodsStoreQueryWrapper = new QueryWrapper<GoodsStore>()
                .eq("store_id",qo.getStoreId())
                .gt("residue_num",0)
                .eq(qo.getId()!=null,"goods_id", qo.getId());
        super.page(goodsStorePage,goodsStoreQueryWrapper);

        Page<DetailStorageSituationVo> voPage = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        voPage.setTotal(goodsStorePage.getTotal());
        List<DetailStorageSituationVo> detailStorageSituationVoList = new ArrayList<>();
        for (GoodsStore record : goodsStorePage.getRecords()) {
            DetailStorageSituationVo vo = new DetailStorageSituationVo();
            vo.setGoodsId(record.getGoodsId());
            vo.setResidueNum(record.getResidueNum());
            vo.setPercentage(totalStoreNum1);
            vo.setGoodsName(goodsService.getById(record.getGoodsId()).getName());
            detailStorageSituationVoList.add(vo);
        }
        voPage.setRecords(detailStorageSituationVoList);
        map.put("vos",voPage);//表格数据
        return map;
    }
}
