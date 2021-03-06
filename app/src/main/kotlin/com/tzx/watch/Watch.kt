package com.tzx.watch

import android.app.Application
import android.support.multidex.MultiDexApplication
import com.tzx.watch.filekts.DelegatesExt
import org.greenrobot.eventbus.EventBus

/**
 * author : Jeff  5899859876@qq.com
 * Csdn :https://blog.csdn.net/Jeff_YaoJie
 * Github: https://github.com/Jay-YaoJie
 * Created :  2019-04-03.
 * description ： MultiDex
 */
class Watch : Application() {
    //companion静态声类声名对象，相当于static关键
    companion object {
        // 按照我们在Java中一样创建一个单例最简单的方式：
//        private var instance:Application?=null;
//        fun instance()= instance!!;
        // 单例不会是null   所以使用notNull委托
        //var instance: FlashLight by Delegates.notNull()
        // 自定义委托实现单例,只能修改这个值一次.
        var instance: Watch by DelegatesExt.notNullSingleValue<Watch>();

        //创建一个 EventBus 实例对象
        var eBus: EventBus by DelegatesExt.notNullSingleValue<EventBus>();
    }

    override fun onCreate() {
        super.onCreate()
        instance = this;
    }
}