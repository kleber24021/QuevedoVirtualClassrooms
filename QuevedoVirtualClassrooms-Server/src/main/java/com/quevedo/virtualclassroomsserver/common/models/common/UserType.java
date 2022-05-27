package com.quevedo.virtualclassroomsserver.common.models.common;

import com.quevedo.virtualclassroomsserver.common.exceptions.UnexpectedEnumValueException;

public enum  UserType {
    STUDENT(2, "STUDENT"),
    TEACHER(1, "TEACHER");

    private final int val;
    private final String stringVal;

    UserType(int val, String stringVal){
        this.val = val;
        this.stringVal = stringVal;
    }

    public int getVal(){
        return this.val;
    }

    public String getStringVal(){
        return this.stringVal;
    }

    public static UserType getEnumFromValue(String value) throws UnexpectedEnumValueException{
        switch (value){
            case "STUDENT":
                return UserType.STUDENT;
            case "TEACHER":
                return UserType.TEACHER;
            default:
                throw new UnexpectedEnumValueException("Not supported value");
        }
    }

    public static UserType getEnumFromValue(int value) throws UnexpectedEnumValueException{
        switch (value){
            case 1:
                return UserType.TEACHER;
            case 2:
                return UserType.STUDENT;
            default:
                throw new UnexpectedEnumValueException("Not supported value");
        }
    }

}
