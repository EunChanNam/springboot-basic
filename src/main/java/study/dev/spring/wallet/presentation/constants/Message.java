package study.dev.spring.wallet.presentation.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Message {

	INPUT_CUSTOMER_ID("등록할 고객 아이디를 입력해주세요 : "),
	INPUT_VOUCHER_ID("등록할 바우처 아이디를 입력해주세요 : "),
	COMPLETE_ADD("지갑 추가가 완료되었습니다."),
	COMPLETE_REMOVE("지갑 제거가 완료되었습니다.");

	private final String value;
}
