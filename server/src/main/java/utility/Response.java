package utility;

import commands.ResultCode;

import java.io.Serializable;
import java.util.ArrayList;


public class Response implements Serializable {
    private static final long serialVersionUID = 31L;
    private String message;
    private ResultCode resultCode;
    private ArrayList<String> result;

    public Response(ResultCode resultCode, String message) {
        this.message = message;
        this.resultCode = resultCode;
        this.result = null;
    }

    public Response(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.message = "";
        this.result = null;
    }

    public Response(ResultCode resultCode, ArrayList<String> result) {
        this.resultCode = resultCode;
        this.result = result;
        this.message = "";
    }

    public ArrayList<String> getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public ResultCode getCommandCode() {
        return resultCode;
    }
}
