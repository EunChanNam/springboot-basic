package com.weeklyMission.client;

import com.weeklyMission.ConsoleIO;
import com.weeklyMission.controller.VoucherController;
import com.weeklyMission.dto.VoucherResponse;
import java.util.List;
import java.util.NoSuchElementException;

public class Client {

    private final ConsoleIO consoleIOHandler;
    private final VoucherController voucherController;

    public Client(ConsoleIO consoleIOHandler, VoucherController voucherController) {
        this.consoleIOHandler = consoleIOHandler;
        this.voucherController = voucherController;
    }

    public void run(){
        String mode = consoleIOHandler.printSelectMode();
        switch (mode) {
            case "voucher" ->{
                voucherMode();
            }
            case "member" ->{
                memberMode();
            }
            case "exit" ->{
                consoleIOHandler.printExitMessage();
            }
            default -> {
                throw new NoSuchElementException();
            }
        }
    }

    private void voucherMode(){
        String function = consoleIOHandler.printSelectVoucherFunction();
        switch(function){
            case "create" -> {
                String type = consoleIOHandler.printSelectVoucherType();
                VoucherType voucherType = VoucherType.of(type);
                VoucherResponse voucherDto = voucherController.create(
                    voucherType.giveVoucher(consoleIOHandler));
                consoleIOHandler.printSuccessCreate(voucherDto);
            }
            case "list" -> {
                List<VoucherResponse> voucherListDto = voucherController.getVoucherList();
                consoleIOHandler.printSuccessGetAllList(voucherListDto);
            }
            default -> {
                throw new NoSuchElementException();
            }
        }
    }

    private void memberMode(){
        String function = consoleIOHandler.printSelectMemberFunction();
    }

}
