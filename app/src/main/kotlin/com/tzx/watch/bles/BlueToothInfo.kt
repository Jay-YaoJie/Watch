package  com.tzx.watch.bles

import com.clj.fastble.data.BleDevice

/**
 * author : Jeff  5899859876@qq.com
 * Csdn :https://blog.csdn.net/Jeff_YaoJie
 * Github: https://github.com/Jay-YaoJie
 * Created :  2019-03-15.
 * description ：description ：蓝牙连接对象
 * 打包，解析，基本数据对象处理
 */
object BlueToothInfo {
    lateinit var bleDevice: BleDevice;//保存的蓝牙数据对象
    var isConnected: Boolean = false;//保存连接状态
    //连接的蓝牙名称里包含字符（或 过滤其他不包含些昵称）
    val bleName: String = ""
    ////可以断开蓝牙  false 不可以断开，true可以断开  可以断开就不用在连接了
    var canDisconnect: Boolean = false


    /**
     * 封装数据对象， 返回组装好的数据对象，
     *
     * @param cmd
     * @param srcdat
     * @return
     */
    fun packetData(byte: ByteArray) {
        //data:{-78,-94,20,19,3,15,16}
    }

    /**
     * 开始记步，，，这里记录步数
     *  解析数据对象
     */
    fun startTaking() {

    }

    /**
     * 这里是获得同步数据的，获得历史数据对象
     *  解析数据对象
     */
    fun synchrodata() {

    }


}