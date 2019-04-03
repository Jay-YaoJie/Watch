package com.tzx.watch.services.phoneCall

import android.content.Context
import android.os.RemoteException
import android.telephony.TelephonyManager
import com.android.internal.telephony.ITelephony
import com.tzx.watch.filekts.LogUtils
import java.lang.reflect.Method
import java.util.concurrent.Executors



/**
 * author : Jeff  5899859876@qq.com
 * Csdn :https://blog.csdn.net/Jeff_YaoJie
 * Github: https://github.com/Jay-YaoJie
 * Created :  2019-04-03.
 * description ：  封装挂断电话接口
 */
object HangUpTelephonyUtil {
    val tag: String = "HangUpTelephonyUtil"
    fun endCall(context: Context): Boolean {
        var callSuccess = false
        val telephonyService = getTelephonyService(context)
        try {
            if (telephonyService != null) {
                callSuccess = telephonyService!!.endCall()
            }
        } catch (e: RemoteException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (callSuccess == false) {
            val eS = Executors.newSingleThreadExecutor()
            eS.execute(Runnable { disconnectCall() })
            callSuccess = true
        }
        return callSuccess
    }

    private fun getTelephonyService(context: Context): ITelephony? {
        val telephonyManager: TelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var clazz: Any? = null
        try {

            clazz = Class.forName(telephonyManager.javaClass.name);
            val method: Method = clazz.javaClass.getDeclaredMethod("getITelephony")
            method.setAccessible(true);
            return method.invoke(telephonyManager) as ITelephony
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.d(tag, e.toString())
        }
        return null;

    }

    private fun disconnectCall(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            runtime.exec("service call phone 5 \n")
        } catch (exc: Exception) {
            exc.printStackTrace()
            return false
        }

        return true
    }

    //// 使用endCall挂断不了，再使用killCall反射调用再挂一次
    fun killCall(context: Context): Boolean {
        try {
            val telephonyManager: TelephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            // Get the getITelephony() method
            val classTelephony: Any = Class.forName(telephonyManager.javaClass.getName())
            val methodGetITelephony = classTelephony.javaClass.getDeclaredMethod("getITelephony")
            // Ignore that the method is supposed to be private
            methodGetITelephony.setAccessible(true);
            // Invoke getITelephony() to get the ITelephony interface
            val telephonyInterface = methodGetITelephony.invoke(telephonyManager)
            // Get the endCall method from ITelephony
            val telephonyInterfaceClass = Class.forName(telephonyInterface.javaClass.getName())
            val methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall")
            // Invoke endCall()
            methodEndCall.invoke(telephonyInterface);

        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.d(tag,e.toString())
        }
        return true
    }
}