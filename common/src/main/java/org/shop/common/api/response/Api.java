package org.shop.common.api.response;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shop.common.api.error.ErrorCodeIfs;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;

    @Valid
    private T body;

    // Static factory methods
    public static <T> Api<T> ok(T data) {
        var api = new Api<T>();
        api.result = Result.ok();
        api.body = data;
        return api;
    }


    public static Api<Object> error(Result result) {
        var api = new Api<Object>();
        api.result = result;
        return api;
    }

    public static Api<Object> error(ErrorCodeIfs errorCodeIfs) {
        var api = new Api<Object>();
        api.result = Result.error(errorCodeIfs);
        return api;
    }

    public static Api<Object> error(ErrorCodeIfs errorCodeIfs, Throwable tx) {
        var api = new Api<Object>();
        api.result = Result.error(errorCodeIfs, tx);
        return api;
    }

    public static Api<Object> error(ErrorCodeIfs errorCodeIfs, String description) {
        var api = new Api<Object>();
        api.result = Result.error(errorCodeIfs, description);
        return api;
    }



}
