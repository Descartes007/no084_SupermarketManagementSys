package com.cow.horse.data_job;

import com.cow.horse.common.redis.constants.RedisKeys;
import com.cow.horse.common.redis.service.RedisTemplateService;
import com.cow.horse.domain.goods_management.goods_category.GoodsCategory;
import com.cow.horse.service.goods_management.goods_category.IGoodsCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CacheDataJob {
    @Autowired
    private RedisTemplateService redisTemplateService;
    @Autowired
    private IGoodsCategoryService goodsCategoryService;

    @Scheduled(cron = "0 0 1 * * ?") //每天凌晨1点执行一次
    public void cache_category(){
        System.out.println("被执行。。。。");
        QueryWrapper<GoodsCategory> wrapper = new QueryWrapper<GoodsCategory>()
                .eq("state", GoodsCategory.STATE_NORMAL);
        List<GoodsCategory> list = goodsCategoryService.list(wrapper);
        if (list==null ||list.size()<=0){
            return;
        }
        String cacheKey = RedisKeys.GOODS_CATEGORY.join();
        for (GoodsCategory goodsCategory : list) {
            redisTemplateService.setCacheMapValue(cacheKey,goodsCategory.getId().toString(),goodsCategory);
        }
    }
}
