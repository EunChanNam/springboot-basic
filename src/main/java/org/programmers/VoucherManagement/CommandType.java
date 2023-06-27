package org.programmers.VoucherManagement;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CommandType {
    EXIT("exit"),
    CREATE("create"),
    LIST("list");

    private static final Map<String,CommandType> COMMAND_TYPE_MAP =
            Collections.unmodifiableMap(Arrays.stream(values()).collect(Collectors.toMap(CommandType::getType, Function.identity())));
    private final String type;

    CommandType(String type){
        this.type = type;
    }

    private String getType() {
        return type;
    }

    public boolean isExit(){
        return this.equals(CommandType.EXIT);
    }

    public boolean isCreate(){
        return this.equals(CommandType.CREATE);
    }

    public boolean isList(){
        return this.equals(CommandType.LIST);
    }

    public static CommandType from(String type){
        if(COMMAND_TYPE_MAP.containsKey(type)){
            return COMMAND_TYPE_MAP.get(type);
        }
        throw new IllegalAccessError(MessageFormat.format("{0}에 해당하는 Command가 존재하지 않습니다.\n",type));
    }
}
