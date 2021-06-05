package utility;

import commands.CommandCode;

import java.io.Serializable;
import java.util.ArrayList;


public class Response implements Serializable {
    private static final long serialVersionUID = 31L;
    private String message;
    private CommandCode commandCode;
    private ArrayList<String> result;

    public Response(CommandCode commandCode, String message) {
        this.message = message;
        this.commandCode = commandCode;
        this.result = null;
    }

    public Response(CommandCode commandCode) {
        this.commandCode = commandCode;
        this.message = "";
        this.result = null;
    }

    public Response(CommandCode commandCode, ArrayList<String> result) {
        this.commandCode = commandCode;
        this.result = result;
        this.message = "";
    }

    public ArrayList<String> getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public CommandCode getCommandCode() {
        return commandCode;
    }
}
