package com.cow.horse.controller.inventory_management.notice;

import com.cow.horse.common.sercurity.annotation.HasPermisson;
import com.cow.horse.common.web.response.JsonResult;
import com.cow.horse.domain.inventory_management.detail_store_goods.DetailStoreGoods;
import com.cow.horse.domain.inventory_management.notice.NoticeIn;
import com.cow.horse.domain.inventory_management.notice.NoticeOut;
import com.cow.horse.qo.inventory_management.notice.QueryNoticeIn;
import com.cow.horse.qo.inventory_management.notice.QueryNoticeOut;
import com.cow.horse.service.goods_management.goods.IGoodsService;
import com.cow.horse.vo.detail_store_goods.notice.NoticeInNotNormalVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Validated
@RequestMapping("/inventory_management/detail_store_goods/notice")
public class NoticeController {
    @Autowired
    private IGoodsService goodsService;

    @HasPermisson("inventory_management:detail_store_goods_in:notice:list")
    @PostMapping("/queryPageNoticeIn")
    public JsonResult queryPageNoticeIn(QueryNoticeIn qo) {
        Page<NoticeIn> page = goodsService.queryPageNoticeIn(qo);
        return JsonResult.success(page);
    }

    @HasPermisson("inventory_management:detail_store_goods_out:notice:list")
    @PostMapping("/queryPageNoticeOut_shelves")
    public JsonResult queryPageNoticeOut_shelves(QueryNoticeOut qo) {
        Page<NoticeOut> page = goodsService.queryPageNoticeOut_shelves(qo);
        return JsonResult.success(page);
    }

    @HasPermisson("inventory_management:detail_store_goods_out:notice:saveOut_shelves")
    @PostMapping("/saveOut_shelves")
    public JsonResult saveOut_shelves(DetailStoreGoods detailStoreGoods, HttpServletRequest request) {
        goodsService.saveOut_shelves(detailStoreGoods, (String) request.getHeader("token"));
        return JsonResult.success();
    }

    @HasPermisson("inventory_management:detail_store_goods_out:notice:list")
    @PostMapping("/queryPageNoticeOut_untreated")
    public JsonResult queryPageNoticeOut_untreated(QueryNoticeOut qo) {
        Page<NoticeInNotNormalVo> page = goodsService.queryPageNoticeOut_untreated(qo);
        return JsonResult.success(page);
    }

    @HasPermisson("inventory_management:detail_store_goods_out:notice:resolveOutUntreatedForm")
    @PostMapping("/resolveOutUntreatedForm")
    public JsonResult resolveOutUntreatedForm(NoticeInNotNormalVo vo, HttpServletRequest request) {
        goodsService.resolveOutUntreatedForm(vo, (String) request.getHeader("token"));
        return JsonResult.success();
    }


}
