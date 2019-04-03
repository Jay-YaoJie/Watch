package com.tzx.watch.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import com.tzx.watch.Watch.Companion.instance
import com.tzx.watch.filekts.LogUtils
import com.tzx.watch.utils.ToastUtil
import java.text.SimpleDateFormat
import java.util.*


/**
 * author : Jeff  5899859876@qq.com
 * Csdn :https://blog.csdn.net/Jeff_YaoJie
 * Github: https://github.com/Jay-YaoJie
 * Created :  2019-04-01.
 * description ：监听短信广播, 获取短信
 * 参照地址： http://wiki.jikexueyuan.com/project/android-war/19-code-filed.html
 *
 *
 *<intent-filter android:priority="1000">表示:
 *   配置广播接收者:
 *  <receiver android:name=".SMSBroadcastReceiver">
 *     <intent-filter android:priority="1000">
 *         <action android:name="android.provider.Telephony.SMS_RECEIVED"/>
 *     </intent-filter>
 *  </receiver>
 */
class SmsReceiver : BroadcastReceiver() {
    private val mNumber: String? = "13537637108" //需要监听的号码

    val tag = "SmsReceiver "
    override fun onReceive(context: Context?, intent: Intent?) {
        LogUtils.d(tag, "短信来了。。。")
//        sms主要结构：
//
//        _id： 短信序号，如100
//        thread_id：对话的序号，如100，与同一个手机号互发的短信，其序号是相同的
//        address： 发件人地址，即手机号，如+86138138000
//        person： 发件人，如果发件人在通讯录中则为具体姓名，陌生人为null
//        date： 日期，long型，如1346988516，可以对日期显示格式进行设置
//        protocol： 协议0SMS_RPOTO短信，1MMS_PROTO彩信
//        read： 是否阅读0未读，1已读
//        status： 短信状态-1接收，0complete,64pending,128failed
//        type： 短信类型1是接收到的，2是已发出
//        body： 短信具体内容
//        service_center：短信服务中心号码编号

        val cursor = instance.contentResolver!!.query(
            Telephony.Sms.CONTENT_URI,
            null,
            Telephony.Sms.ADDRESS + " = ? AND " + Telephony.Sms.TYPE + " = ?",   //查询某个号码，类型为接收的短信
            arrayOf(mNumber, "1"),
            Telephony.Sms.DATE + " DESC LIMIT 1"  //按时间降序查询1条记录
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                LogUtils.d(tag, cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS)))
                LogUtils.d(tag, cursor.getString(cursor.getColumnIndex(Telephony.Sms.DATE)))
                LogUtils.d(tag, cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY)))
            }
            cursor.close()
        }


        val pdus = intent!!.getExtras().get("pdus") as Array<Any>
        for (pdu in pdus) {
            val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
            val address = smsMessage.displayOriginatingAddress
            val body = smsMessage.messageBody
            val date = smsMessage.timestampMillis
            val timeDate = Date(date)
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val time = simpleDateFormat.format(timeDate)

            LogUtils.d(tag, "短信来自:$address")
            LogUtils.d(tag, "短信内容:$body")
            LogUtils.d(tag, "短信时间:$time")
            ToastUtil.show("短信内容:$body")

            //如果短信来自5556,不再往下传递,一般此号码可以作为短信平台的号码。
//            if ("5556" == sender) {
//                println(" abort ")
//                abortBroadcast()
//            }
        }
    }
}