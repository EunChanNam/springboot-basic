package com.example.springbootbasic.controller.voucher;

import com.example.springbootbasic.controller.dto.voucher.VoucherDto;
import com.example.springbootbasic.controller.request.CreateVoucherRequest;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import com.example.springbootbasic.service.voucher.JdbcVoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/view/v1/vouchers")
public class ViewVoucherController {

    private final JdbcVoucherService voucherService;

    public ViewVoucherController(JdbcVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String voucherList(Model model) {
        List<Voucher> findAllVouchers = voucherService.findAllVouchers();
        List<VoucherDto> result = findAllVouchers.stream()
                .map(VoucherDto::newInstance)
                .toList();
        model.addAttribute("vouchers", result);
        return "voucher-list";
    }

    @GetMapping("/add")
    public String voucherAddForm() {
        return "voucher-add";
    }

    @GetMapping("/search")
    public String findVoucherById(@RequestParam Long voucherId, Model model, RedirectAttributes re) {
        Optional<Voucher> findVoucher = voucherService.findById(voucherId);
        if (findVoucher.isEmpty()) {
            re.addFlashAttribute("findResult", false);
            return "redirect:";
        }
        VoucherDto voucher = VoucherDto.newInstance(findVoucher.get());
        model.addAttribute("vouchers", voucher);
        return "voucher-list";
    }

    @PostMapping("/add")
    public String voucherAddForm(CreateVoucherRequest request) {
        long discountValue = request.discountValue();
        VoucherType voucherType = VoucherType.of(request.voucherType());
        voucherService.saveVoucher(VoucherFactory.of(discountValue, voucherType,
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now().plusDays(30)));
        return "redirect:";
    }

    @DeleteMapping("/{voucherId}")
    public String deleteVoucher(@PathVariable Long voucherId) {
        voucherService.deleteVoucherById(voucherId);
        return "redirect:";
    }
}
