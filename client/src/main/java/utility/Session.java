package utility;

import java.io.Serializable;

public class Session implements Serializable {
    private final String username;
    private final String password;
    private boolean isAuthorized;

    public Session(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return  username;
    }

    public String getPassword() {
        return password;
    }

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }
}
