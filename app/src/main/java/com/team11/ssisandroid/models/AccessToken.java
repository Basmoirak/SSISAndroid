package com.team11.ssisandroid.models;

import java.io.Serializable;
import java.util.HashMap;

public class AccessToken extends HashMap<String, Object> implements Serializable {

    public AccessToken(String accessToken, String tokenType, int expiresIn){
        put("accessToken", accessToken);
        put("tokenType", tokenType);
        put("expiresIn", expiresIn);
    }

    public AccessToken(String error, String errorDescription){
        put("error", error);
        put("errorDescription", errorDescription);
    }
}

