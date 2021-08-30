package org.prgrms.orderApp.presentation.commandOperator;

import org.prgrms.orderApp.presentation.commandOperator.pages.*;
import org.prgrms.orderApp.presentation.commandOperator.util.CheckInvalid;
import org.prgrms.orderApp.presentation.commandOperator.util.MonguDbMainPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandOperator {

    @Autowired
    private MainPage mainPage;

    @Autowired
    private MonguDbPage monguDbPage;

    @Autowired
    private OrderPage orderPage;

    @Autowired
    private VoucherPage voucherPage;

    @Autowired
    private CheckInvalid checkInvalid;

    @Autowired
    private CustomerPage customerPage;

    public void CMDAppStart(){
        int exit_flag = -1;
        String userSelectedMenu ;
        // 본 프로그램 안내
        mainPage.introduceApp();

        try {
            do {
                Thread.sleep(500);
                switch (mainPage.selectedMainMenu()) {
                    case "CREATE_VOUCHER":
                        voucherPage.createVoucher();
                        break;
                    case "VOUCHER_LIST":
                        voucherPage.showVouchers();
                        break;
                    case "CREATE_ORDER":
                        orderPage.createOrder();
                        break;
                    case "ORDER_LIST" :
                        orderPage.showOrders();
                        break;
                    case "CREATE_COLLECTION":

                        userSelectedMenu = monguDbPage.selectedMonguMainMenu();
                        checkInvalid.checkInteger(userSelectedMenu);
                        switch (MonguDbMainPage.getMenuName(Integer.parseInt(userSelectedMenu))){
                            case "COLLECTION_CREATE" :
                                monguDbPage.createCollection();
                                break;
                            case "COLLECTION_DROP" :
                                monguDbPage.dropCollection();
                                break;
                            default:
                                mainPage.SelectedInvalidMenuNumber();
                        }
                        break;
                    case "CUSTOMER_BLACK_LIST":
                            customerPage.showAllBlackList();
                        break;
                    case "EXIT":
                        if (mainPage.exit().equals("yes")) {
                            exit_flag = 1;
                        }
                        break;
                    default:
                        mainPage.selectedInvalidMenu();
                        break;
                }
            }while(exit_flag == -1);

        }catch(Exception e){
            mainPage.apologizeMessage();
            System.out.println(e);
        }finally {
            mainPage.sayBye();
        }
    }

}
