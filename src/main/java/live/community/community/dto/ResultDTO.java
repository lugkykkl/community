package live.community.community.dto;

import live.community.community.exception.CustomizeErrorCode;
import live.community.community.exception.CustomizeException;
import org.springframework.web.servlet.ModelAndView;

public class ResultDTO {
    private Integer code;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private  String message;

    public static ResultDTO errorof(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static ResultDTO errorof(CustomizeErrorCode errorCode) {
        return errorof(errorCode.getCode(),errorCode.getMessage());
    }
    public static ResultDTO errorof(CustomizeException e) {
        return errorof(e.getCode(),e.getMessage());

    }
    public static ResultDTO okOF(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

}
