package org.prgrms.kdt.voucher.view;

import org.prgrms.kdt.voucher.model.Menu;
import org.prgrms.kdt.voucher.dto.VoucherDTO;
import org.prgrms.kdt.voucher.model.VoucherType;

import java.util.List;
import java.util.Optional;

public interface Output {

    void showMenu(Menu[] menuList);
    void showVoucherTypes(VoucherType[] voucherTypes);
    void showVoucherList(List<VoucherDTO> voucherDtoList);
    void printError(String errorMessage);
    void printMessage(String message);
}
