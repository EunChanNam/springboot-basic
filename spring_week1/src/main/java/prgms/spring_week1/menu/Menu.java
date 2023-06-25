package prgms.spring_week1.menu;

import java.util.stream.Stream;

public enum Menu {
    INVALID(""),
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    BLACK("black");

    private final String menuName;

    Menu(String menuName) {
        this.menuName = menuName;
    }

    public static Menu findMenuType(String inputText) throws IllegalArgumentException{
        return Stream.of(Menu.values())
                .filter(menu -> menu.menuName.equalsIgnoreCase(inputText))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid input"));
    }
}
