package com.gabriel.stage.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabriel.stage.common.enums.ResultCode;

import java.io.Serializable;

/**
 * @author: Gabriel
 * @date: 2020/1/17 15:00
 * @description 响应体结果封装
 */
public class Result<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private Result(int status) {
        this.status = status;
    }

    private Result(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private Result(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private Result(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    /**
     * 使之不在json序列化结果当中
     *
     * @return boolean boolean
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResultCode.SUCCESS.getCode();
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * Gets msg.
     *
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }


    /**
     * Create by success server response.
     *
     * @param <T> the type parameter
     * @return the server response
     */
    public static <T> Result<T> success() {
        return new Result<T>(ResultCode.SUCCESS.getCode());
    }

    /**
     * Create by success message server response.
     *
     * @param <T> the type parameter
     * @param msg the msg
     * @return the server response
     */
    public static <T> Result<T> successMessage(String msg) {
        return new Result<T>(ResultCode.SUCCESS.getCode(), msg);
    }

    /**
     * Create by success server response.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the server response
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultCode.SUCCESS.getCode(), data);
    }

    /**
     * Create by success server response.
     *
     * @param <T>  the type parameter
     * @param msg  the msg
     * @param data the data
     * @return the server response
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<T>(ResultCode.SUCCESS.getCode(), msg, data);
    }


    /**
     * Create by error server response.
     *
     * @param <T> the type parameter
     * @return the server response
     */
    public static <T> Result<T> error() {
        return new Result<T>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getDesc());
    }

    public static <T> Result<T> error(int errorCode, String errorMessage) {
        return new Result<T>(errorCode, errorMessage);
    }


    /**
     * Create by error message server response.
     *
     * @param <T>          the type parameter
     * @param errorMessage the error message
     * @return the server response
     */
    public static <T> Result<T> errorMessage(String errorMessage) {
        return new Result<T>(ResultCode.ERROR.getCode(), errorMessage);
    }

    /**
     * Create by error code message server response.
     *
     * @param <T>          the type parameter
     * @param errorCode    the error code
     * @param errorMessage the error message
     * @return the server response
     */
    public static <T> Result<T> errorCodeMessage(int errorCode, String errorMessage) {
        return new Result<T>(errorCode, errorMessage);
    }

    /**
     * Create by error server response.
     *
     * @param <T>          the type parameter
     * @param errorMessage the error message
     * @param data         the data
     * @return the server response
     */
    public static <T> Result<T> error(String errorMessage, T data) {
        return new Result<T>(ResultCode.ERROR.getCode(), errorMessage, data);
    }

    /**
     * 返回系统状态码以及相应说明
     *
     * @param ResultCode
     * @param <T>
     * @return
     */
    public static <T> Result<T> response(ResultCode ResultCode) {
        return new Result<T>(ResultCode.getCode(), ResultCode.getDesc());
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
