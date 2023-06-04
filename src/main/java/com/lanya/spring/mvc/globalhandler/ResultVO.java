package com.lanya.spring.mvc.globalhandler;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guanqi
 * @version 1.0
 * @date 2020/12/22 13:53
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "返回结果的格式工具类")
public class ResultVO<T> implements Serializable {

    /**
     * 操作成功默认code
     */
    public static final int DEFAULT_SUCCESS_CODE = 0;
    /**
     * 操作失败默认code
     */
    public static final int DEFAULT_ERROR_CODE = -1;

    /**
     * 操作成功默认msg
     */
    private static final String DEFAULT_SUCCESS_MSG = "操作成功";
    /**
     * 操作失败默认msg
     */
    private static final String DEFAULT_ERROR_MSG = "操作失败";

    @ApiModelProperty(notes = "状态吗")
    private Integer code;

    @ApiModelProperty(notes = "提示信息")
    private String msg;

    /**
     * 用于接收任意类型数据，便于返回到前台
     */
    @ApiModelProperty(notes = "数据集合")
    private T data;



    public static ResultVO ok() {
        return ResultVO.builder().code(DEFAULT_SUCCESS_CODE).msg(DEFAULT_SUCCESS_MSG).build();
    }

    public static<T> ResultVO<T> ok(T data) {

        ResultVO<T> result = new ResultVO<>();
        result.setCode(DEFAULT_SUCCESS_CODE);
        result.setMsg(DEFAULT_SUCCESS_MSG);
        result.setData(data);

        return result;
    }


    public static ResultVO error(String msg, int code) {
        return ResultVO.builder().code(code).msg(msg).build();
    }

    public static ResultVO error(String msg) {
        return ResultVO.builder().code(DEFAULT_ERROR_CODE).msg(msg).build();
    }

    public static ResultVO error() {
        return ResultVO.builder().code(DEFAULT_ERROR_CODE).msg(DEFAULT_ERROR_MSG).build();
    }

    public static ResultVO error(ErrorEnum error) {
        return ResultVO.builder().code(error.getCode()).msg(error.getMsg()).build();
    }








}
