package com.dhlk.impls;

import java.util.concurrent.Callable;

/**
 * Content:短信收发类接口
 * Author:jpdong
 * Date:2020/3/3
 */
public interface ISMSCallBack {
    /**
     * 发送成功
     *
     * @param runnable 异步
     */
    void success(Runnable runnable);

    /**
     * 发送成功
     *
     * @param callable 同步
     */
    void success(Callable callable);

    /**
     * 发送失败
     *
     * @param runnable 异步
     */

    Throwable failure(Runnable runnable);

    /**
     * 发送失败
     *
     * @param callable 同步
     */
    Throwable failure(Callable callable);
}
