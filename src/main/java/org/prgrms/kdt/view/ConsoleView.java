package org.prgrms.kdt.view;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.prgrms.kdt.enums.Command;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;
import org.prgrms.kdt.model.dto.VoucherDTO;
import org.springframework.stereotype.Component;

@Component
public class ConsoleView implements InputView, OutputView {

	private final TextIO textIo;

	public ConsoleView() {
		this.textIo = TextIoFactory.getTextIO();
	}

	private static void printCommandDescriptions() {
		Arrays.stream(Command.values())
			.forEach(
				command -> {
					String commandDescription = MessageFormat.format(
						"Type {0} {1}", command.name(), command.getDescription()
					);

					System.out.println(commandDescription);
				}
			);

	}

	private static void printVoucherIdxDescription() {
		Arrays.stream(VoucherType.values())
			.forEach(
				voucherType -> {
					String voucherIdxDescription = MessageFormat.format(
						"{0}: {1}", voucherType.name(), voucherType.getVoucherIdx()
					);
					System.out.println(voucherIdxDescription);
				}
			);
	}

	@Override
	public int getAmount() {
		return this.textIo.newIntInputReader()
			.read("amount input: ");
	}

	@Override
	public Command getCommand() {
		String commandString = this.textIo.newStringInputReader()
			.withInputTrimming(true)
			.withMinLength(1)
			.read("command input: ")
			.toUpperCase();

		return Command.valueOf(commandString);
	}

	@Override
	public VoucherType getVoucherType() {
		int voucherTypeIdx = this.textIo.newIntInputReader()
			.withMinVal(1)
			.read("voucher type idx input: ");

		return VoucherType.valueOf(voucherTypeIdx);
	}

	@Override
	public void displayCommandGuideMessage() {
		System.out.println("=== Voucher Program ===");
		printCommandDescriptions();
	}

	@Override
	public void displayExitMessage() {
		System.out.println("종료 되었습니다.");
	}

	@Override
	public void displayCreateVoucherMessage() {
		printVoucherIdxDescription();
		System.out.println("번호를 입력 해주세요: ");
	}

	@Override
	public void displayAmountErrorMessage() {
		System.out.println("입력 된 할인 값 범위가 잘 못 되었습니다.");
	}

	@Override
	public void displayVoucherList(List<VoucherDTO> voucherDTOS) {
		for (VoucherDTO voucherDTO : voucherDTOS) {
			printVoucherDTO(voucherDTO);
		}
	}

	@Override
	public void displayCommandErrorMessage() {
		System.out.println("잘 못된 명령어 입니다.");
	}

	public void printVoucherDTO(VoucherDTO voucherDTO) {
		Amount amount = voucherDTO.getAmount();
		String voucherDataString = MessageFormat.format("voucher id: {0} voucher type: {1} voucher amount: {2}",
			voucherDTO.getVoucherId(), voucherDTO.getVoucherType(), amount.getAmount()
		);

		System.out.println(voucherDataString);
	}
}
