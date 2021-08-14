package com.wills.spring.example.entity;

/**
 * @ClassName HttpCode
 * @Date 2021/8/13 13:43
 * @Author 王帅
 * @Version 1.0
 * @Description
 */
public class HttpCode {

    private Integer code;
    private Object data;
    private String msg;

    public HttpCode(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static HttpCode ok(){
        return new HttpCode(200, null, "success！");
    }

    public static <T> HttpCode ok(T data){
        return new HttpCode(200, data, "success！");
    }

    public static HttpCode faild(String msg){
        return new HttpCode(500, null, msg);
    }

    public static <T> HttpCode faild(T data,String msg){
        return new HttpCode(500, data, msg);
    }

    public static <T> HttpCode faild(Integer code,T data,String msg){
        return new HttpCode(code, data, msg);
    }

    @Override
    public String toString() {
        return "HttpCode{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
