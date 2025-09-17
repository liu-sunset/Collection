package peng.zhi.liu.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(){
        Result result = new Result();
        result.code = 1;
        result.msg = "success";
        return result;
    }

    public static Result success(Object data){
        Result success = Result.success();
        success.data = data;
        return success;
    }

    public static Result error(Object data){
        Result result = new Result();
        result.code = 0;
        result.msg = "error";
        result.data = data;
        return result;
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(){}
}
