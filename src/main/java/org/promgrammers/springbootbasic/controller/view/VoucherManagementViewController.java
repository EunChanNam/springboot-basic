package org.promgrammers.springbootbasic.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VoucherManagementViewController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
