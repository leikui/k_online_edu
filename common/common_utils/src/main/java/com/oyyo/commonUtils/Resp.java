package com.oyyo.commonUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(value = "全局统一返回结果")
public class Resp {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    /**
     * 构造方法私有化，其他不能  new
     */
    private Resp(){}

    public static Resp ok(){
        Resp r = new Resp();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    public static Resp error(){
        Resp r = new Resp();
        r.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
        return r;
    }
    public static Resp setResult(ResultCodeEnum resultCodeEnum){
        Resp r = new Resp();
        r.setSuccess(resultCodeEnum.getSuccess());
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(resultCodeEnum.getMessage());
        return r;
    }

    public Resp success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Resp message(String message){
        this.setMessage(message);
        return this;
    }

    public Resp code(Integer code){
        this.setCode(code);
        return this;
    }

    public Resp data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public Resp data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}