package com.example.navigationapp.util;

/**
 * Created by 占大帅 on 2017/1/17.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
