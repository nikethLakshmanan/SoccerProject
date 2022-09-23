package com.example.finalproject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public interface AsyncResponse {
    void processFinish(int action, Map<String,Object> context, JSONObject output) ;
}
