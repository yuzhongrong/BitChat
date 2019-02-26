package com.cjwsc.idcm.base;

import com.cjwsc.idcm.net.exception.ResponseThrowable;

/**
 * 作者：yzr
 * 电话：licnese
 * 邮箱：licnese@qq.com
 * 版本号：1.0
 * 类描述：  定义View中需要实现的方法
 * 备注消息：
 * 修改时间：2016/11/8 下午4:44
 * //extends BaseProgressView<T>
 **/
public interface BaseView<T> extends BaseProgressView<T> {
    //    提示错误消息
    void showErrorWithStatus(ResponseThrowable throwable);
}
