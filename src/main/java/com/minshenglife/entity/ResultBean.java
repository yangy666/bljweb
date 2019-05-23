package com.minshenglife.entity;

/**
 * @author luqingyun
 */
public class ResultBean {

    /**
     * 返回类
     */
    private Object object;

    private String message;

    /**
     * 返回状态
     * true:成功
     * false:失败
     */
    private boolean status;

    public ResultBean() {
        this.status = true;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setFailure() {
        this.status = false;
    }
}
