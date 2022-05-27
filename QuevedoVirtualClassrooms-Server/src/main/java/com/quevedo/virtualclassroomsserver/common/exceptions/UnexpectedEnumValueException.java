package com.quevedo.virtualclassroomsserver.common.exceptions;

public class UnexpectedEnumValueException extends Exception{
    public UnexpectedEnumValueException(String inputMessage){
        super(inputMessage);
    }
}
