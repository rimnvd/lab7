package utility;

import java.io.Serializable;


public class Response implements Serializable {
    private final String message;
    private static final long serialVersionUID = 31L;

    public Response(String message) {
        this.message = message;
    }

    public Response() {
        this.message = "";
    }

    public String getResponse() {
        return message;
    }
}
