package com.android.smartlink.assist;

/**
 * User: LIUWEI
 * Date: 2017-10-16
 * Time: 18:52
 */
public interface RequestCallback<T>
{
    void onResponse(T t);

    void onError(Throwable throwable);
}
