package com.roc.webview;

/**
 * Created by cenxiaozhong on 2017/6/21.
 * source code  https://github.com/Justson/AgentWeb
 */

public interface DownLoadResultListener {


    void success(String path);

    void error(String path, String resUrl, String cause, Throwable e);

}
