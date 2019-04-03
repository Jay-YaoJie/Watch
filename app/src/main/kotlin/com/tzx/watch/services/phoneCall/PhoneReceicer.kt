package com.tzx.watch.services.phoneCall

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import com.tzx.watch.filekts.LogUtils
import com.tzx.watch.utils.ToastUtil


/**
 * author : Jeff  5899859876@qq.com
 * Csdn :https://blog.csdn.net/Jeff_YaoJie
 * Github: https://github.com/Jay-YaoJie
 * Created :  2019-04-01.
 * description ：Android来电监听和去电监听
 * 参考资料   http://www.cnblogs.com/haowenbiao/archive/2012/08/15/2639579.html
 */
class PhoneReceicer : BroadcastReceiver() {
    val tag: String = "PhoneReceicer"
    override fun onReceive(context: Context?, intent: Intent?) {
        LogUtils.d(tag, "action" + intent!!.action)
        //如果是去电
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            val phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
            LogUtils.d(tag, "call OUT:" + phoneNumber);
        } else {
            //查了下android文档，貌似没有专门用于接收来电的action,所以，非去电即来电.
            //如果我们想要监听电话的拨打状况，需要这么几步 :
            /*
              * 第一：获取电话服务管理器TelephonyManager manager = this.getSystemService(TELEPHONY_SERVICE);
    * 第二：通过TelephonyManager注册我们要监听的电话状态改变事件。manager.listen(new MyPhoneStateListener(),
    * PhoneStateListener.LISTEN_CALL_STATE);这里的PhoneStateListener.LISTEN_CALL_STATE就是我们想要
    * 监听的状态改变事件，初次之外，还有很多其他事件哦。
    * 第三步：通过extends PhoneStateListener来定制自己的规则。将其对象传递给第二步作为参数。
    * 第四步：这一步很重要，那就是给应用添加权限。android.permission.READ_PHONE_STATE
            */
            val tm = context!!.getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager
            tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE)
            //设置一个监听器
        }
    }

   // PhoneStateListener的onCallStateChanged方法监听来电状态
    val listener: PhoneStateListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, incomingNumber: String) {
            //注意，方法必须写在super方法后面，否则incomingNumber无法获取到值。
            super.onCallStateChanged(state, incomingNumber)
            when (state) {
                TelephonyManager.CALL_STATE_IDLE -> LogUtils.d(tag, "挂断")
                TelephonyManager.CALL_STATE_OFFHOOK -> LogUtils.d(tag, "接听")
                TelephonyManager.CALL_STATE_RINGING ->{
                    //输出来电号码
                    LogUtils.d(tag, "响铃:来电号码$incomingNumber")
                    ToastUtil.show("响铃:来电号码$incomingNumber")
                }
            }
        }
    }

}