package com;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.ext.WSPasswordCallback;

public class ServerPasswordCallback implements CallbackHandler {

    private static final Map<String, String> USERS = new HashMap<>();

    static {
        USERS.put("Dhruv", "welcome123"); // username → password
        USERS.put("Admin", "admin123");
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            WSPasswordCallback pc = (WSPasswordCallback) callback;
            String expected = USERS.get(pc.getIdentifier());
            if (expected != null && expected.equals(pc.getPassword())) {
                return; // success
            }
            throw new SecurityException("Invalid Username or Password");
        }
    }
}

