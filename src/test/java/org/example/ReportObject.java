package org.example;

public class ReportObject {
    private String status, errorMessage, methodName;

    public ReportObject(String status, String errorMessage, String methodName) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.methodName = methodName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
