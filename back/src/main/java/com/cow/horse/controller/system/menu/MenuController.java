package com.cow.horse.controller.system.menu;

import com.cow.horse.common.sercurity.annotation.HasPermisson;
import com.cow.horse.common.web.response.JsonResult;
import com.cow.horse.domain.system.menu.Menu;
import com.cow.horse.service.system.menu.IMenuService;
import com.cow.horse.qo.system.MenuQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    /**
     * 条件分页查询菜单的信息
     * @param qo
     * @return
     */
    @HasPermisson("system:menu:list")
    @PostMapping("/queryPageByQo")
    public JsonResult queryPageByQo(MenuQuery qo) {
        Page<Menu> page = menuService.queryPageByQo(qo);
        return JsonResult.success(page);
    }
}
