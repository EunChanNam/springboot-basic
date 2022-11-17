package com.programmers.assignment.voucher.engine.controller;

import com.programmers.assignment.voucher.engine.service.MenuService;
import org.springframework.stereotype.Controller;


@Controller
public class MenuController {
    private final MenuService menuService;


    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }


    public String startMenu() {
        return menuService.inputCommand();
    }


    public String createCommand() {
        String discountWay = menuService.createVoucher();
        return discountWay;
    }

    public void listCommand() {
        menuService.showVouchers();
    }

    public void exitCommand() {
        menuService.exitApplication();
    }
}
