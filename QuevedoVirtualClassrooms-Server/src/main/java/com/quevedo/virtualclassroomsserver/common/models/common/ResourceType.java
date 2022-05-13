package com.quevedo.virtualclassroomsserver.common.models.common;

public enum ResourceType {
    VIDEO(1, "VIDEO"),
    IMAGE(2,"IMAGE"),
    URL(3, "URL");

    private int value;
    private String stringValue;

    ResourceType(int value, String stringValue){
        this.value = value;
        this.stringValue = stringValue;
    }

    public static ResourceType getTypeByString(String inputString){
        ResourceType toReturn;
        switch (inputString){
            case "VIDEO":
                toReturn = ResourceType.VIDEO;
                break;
            case "IMAGE":
                toReturn = ResourceType.IMAGE;
                break;
            case "URL":
                toReturn = ResourceType.URL;
                break;
            default:
                toReturn = null;
        }
        return toReturn;
    }

    public static ResourceType getTypeByInt(int inputInt){
        switch (inputInt){
            case 1:
                return ResourceType.VIDEO;
            case 2:
                return ResourceType.IMAGE;
            case 3:
                return ResourceType.URL;
            default:
                return null;
        }
    }
    public int getValue(){
        return value;
    }

    public String getStringValue(){ return stringValue; }
}
