package com.programmers.part1.ui;

import com.programmers.part1.io.Console;
import com.programmers.part1.io.Message;
import com.programmers.part1.member.MemberController;
import com.programmers.part1.order.voucher.VoucherController;
import com.programmers.part1.order.voucher.entity.VoucherType;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.lang.System.exit;


/**
 * Author : Jung
 * 1. 사용자로부터 입출력을 받는 UI 계층으로 지정하였습니다.
 * 2. VoucherController, MemberController 외에도 추가적으로 매핑 받을 수 있게 하였으나,
 * 제가 handler 처리에 미숙해서 리팩터에 실패하였습니다. -> 조언을 받고 싶습니다.
 * 3. Controller 및 도메인에서 에러를 던져서 받았는데 exception 처리 책임이 잘 나뉘어졌는지 궁금합니다.
 */
@Component
public class Client implements Runnable {

    private final Console console;
    private final Message message;
    private final BeanFactory applicationContext;

    public Client(Console console, Message message, BeanFactory applicationContext) {
        this.console = console;
        this.message = message;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {

        MemberController memberController = applicationContext.getBean(MemberController.class);
        VoucherController voucherController = applicationContext.getBean(VoucherController.class);

        while (true) {
            console.write(message.getSTART_PROMPT());

            // 요청 받기
            String request = console.readLine();
            CommandType requestCommandType = CommandType.getCommandType(request);

            /**
             * switch문 밖으로 빼서 처리하는게 일관성을 깨트리는 것같습니다...
             * switch문 안에서 처리할 때 exit() 함수로 강제종료 시키는 방법도 있지만
             * 적합하지 않은 방법이라 적용하지 않았습니다...
             * **/
            if (requestCommandType == CommandType.EXIT) {
                exitApp();
                break;
            }

            switch (requestCommandType) {
                case CREATE -> createVoucher(voucherController);
                case LIST -> getAllVoucher(voucherController);
                case BLACKLIST -> getAllBlackMembers(memberController);
                case NONE -> reInputCommand();
            }
        }
    }

    private void exitApp() {
        console.write(message.getEXIT());
    }

    private void reInputCommand(){
        console.write(message.getRE_INPUT());
    }

    private void createVoucher(VoucherController voucherController) {
        // 1. voucher type 입력
        console.write(message.getVOUCHER_PROMPT());
        VoucherType requestVoucherType = VoucherType.NONE;

        try {
            requestVoucherType = VoucherType.getVoucherType(console.readLine());
            console.write(message.getAMOUNT_PROMPT());
            long amount = Long.parseLong(console.readLine());
            voucherController.create(requestVoucherType, amount);
            console.write(message.getCREATE_SUCCESS());
        } catch (RuntimeException e) {
            /**
             * 1. voucher type이 잘못 입력 되었을때
             * 2. voucherType 검증
             * 3. Fixed 일 경우 0보다 큰값이 입력 되었는지
             * 4. Percent일 경우 amount <=0, 100 < amount 퍼센트 검증
             * **/
            console.write(e.getMessage());
        }
    }

    private void getAllVoucher(VoucherController voucherController) {
        try {
            voucherController.list()
                    .iterator()
                    .forEachRemaining(System.out::println);
        } catch (RuntimeException e) {
            // list 정보가 없을 경우
            console.write(e.getMessage());
        }
    }

    private void getAllBlackMembers(MemberController memberController){
        try {
            memberController.list()
                    .iterator()
                    .forEachRemaining(System.out::println);
        } catch (RuntimeException e) {
            console.write(e.getMessage());
        } catch (IOException e) {
            //list 정보가 없을 경우
            e.printStackTrace();
        }
    }


}
