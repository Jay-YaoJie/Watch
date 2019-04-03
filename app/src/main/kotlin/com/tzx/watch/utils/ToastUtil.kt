package  com.tzx.watch.utils

import android.support.annotation.StringRes
import android.widget.Toast
import com.tzx.watch.Watch.Companion.instance

/**
 * author : Jeff  5899859876@qq.com
 * Csdn :https://blog.csdn.net/Jeff_YaoJie
 * Github: https://github.com/Jay-YaoJie
 * Created :  2019-03-15.
 * description ： Toast
 */
object ToastUtil {
    fun show(text: CharSequence?) {
        if (text != null) {
            if (text.length < 10) {
                Toast.makeText(instance, text, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(instance, text, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun show(@StringRes resId: Int) {
        show(instance.getString(resId))
    }

    fun show() {
        //先检查网络是否可以使用
        //        if (App.app!=null&&!CommonUtils.isNetworkAvailable(TussaudSmart.instance)){
        //            show( "当前网络不可用，请稍后重试！");
        //        }else {
        //            show("服务器繁忙，请稍后重试！");
        //
        //        }
    }

}