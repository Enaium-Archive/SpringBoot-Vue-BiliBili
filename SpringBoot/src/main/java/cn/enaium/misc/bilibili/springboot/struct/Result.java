package cn.enaium.misc.bilibili.springboot.struct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author Enaium
 */
public class Result<T> {
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    public static final String TOKEN_ERROR = "token_error";

    @Getter
    private final String status;
    @Getter
    private final String msg;
    @Getter
    private T data;

    public Result(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Result(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
