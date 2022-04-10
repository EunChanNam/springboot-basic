package org.prgrms.kdt.command.controller;

import static org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils.qualifiedBeanOfType;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.domain.CommandType;
import org.prgrms.kdt.command.domain.Input;
import org.prgrms.kdt.command.domain.Output;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class CommandController implements ApplicationRunner {

  private final Input input;
  private final Output output;
  private final ApplicationContext context;

  public CommandController(
      Input input,
      Output output,
      ApplicationContext context) {
    this.input = input;
    this.output = output;
    this.context = context;
  }

  @Override
  public void run(ApplicationArguments args) {
    var commandType = CommandType.INIT;
    while (commandType != CommandType.EXIT) {
      output.printMenu();
      commandType = CommandType.of(input.read());
      output.printLine(command(commandType).execute());
    }
  }

  private Command command(CommandType commandType) {
    return qualifiedBeanOfType(context, Command.class, commandType.name());
  }
}