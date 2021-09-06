package com.talkdesk.aggregate.exception;

public class InvalidParameterException extends java.security.InvalidParameterException {

    private String parameterName;
    private Object parameterValue;

    public InvalidParameterException(String parameterName, Object parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;

    }
    private String buildMessage(){
        return String.format("The parameter [%s] is not valid with actual value of [%s]",
                this.parameterName, this.parameterValue);
    }

    @Override
    public String getMessage() {
        return buildMessage();
    }
}
