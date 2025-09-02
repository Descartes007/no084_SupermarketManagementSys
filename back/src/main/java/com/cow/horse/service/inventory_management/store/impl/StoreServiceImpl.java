package com.cow.horse.service.inventory_management.store.impl;

import com.cow.horse.common.exception.BusinessException;
import com.cow.horse.domain.inventory_management.store.Store;
import com.cow.horse.mapper.inventory_management.store.StoreMapper;
import com.cow.horse.service.inventory_management.store.IGoodsStoreService;
import com.cow.horse.service.inventory_management.store.IStoreService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements IStoreService {
    @Autowired
    private IGoodsStoreService goodsStoreService;

    @Override
    public void updateStore(Store store) {
        //判断是否要将状态修改为停用状态
        QueryWrapper<Store> storeQueryWrapper = new QueryWrapper<Store>()
                .eq("name", store.getName())
                .eq("address", store.getAddress())
                .eq("state",store.getState());
        Store one = super.getOne(storeQueryWrapper);
        if (Store.STATE_BAN.equals(store.getState())) {
            Long redisueNum = goodsStoreService.storeUsed(store.getId());
            //要修改为停用状态
            if (redisueNum != null && redisueNum != 0) {
                throw new BusinessException("仓库中存在商品，不能停用仓库");
            }
            if (one!=null){
                super.removeById(one);
            }

        }

        super.updateById(store);
    }

    @Transactional
    @Override
    public void deactivate(Long sid) {
        Long redisueNum = goodsStoreService.storeUsed(sid);
        if (redisueNum != null && redisueNum != 0) {
            throw new BusinessException("仓库中存在商品，不能停用仓库");
        } else {
            UpdateWrapper<Store> wrapper = new UpdateWrapper<Store>()
                    .set("state", Store.STATE_BAN)
                    .eq("id", sid);
            super.update(wrapper);
        }
    }

    @Override
    public Long getResidueNumByGoodsId(Long id) {
        return goodsStoreService.getResidueNumByGoodsId(id);
    }

    @Override
    public void saveStore(Store store) {
        store.setState(Store.STATE_NORMAL);
        QueryWrapper<Store> queryWrapper = new QueryWrapper<Store>().eq("state", Store.STATE_NORMAL)
                .eq("name", store.getName())
                .eq("address", store.getAddress());
        Store one = super.getOne(queryWrapper);
        if (one != null) {
            throw new BusinessException("创建失败，已有相同的仓库");
        }
        super.save(store);
    }
}
