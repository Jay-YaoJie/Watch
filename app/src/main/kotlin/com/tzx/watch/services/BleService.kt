package  com.tzx.watch.services

import android.bluetooth.BluetoothGatt
import android.util.Log
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleGattCallback
import com.clj.fastble.callback.BleNotifyCallback
import com.clj.fastble.callback.BleScanCallback
import com.clj.fastble.callback.BleWriteCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.clj.fastble.utils.HexUtil
import com.tzx.watch.Watch.Companion.instance
import com.tzx.watch.filekts.LogUtils
import java.util.*


/**
 * author : Jeff  5899859876@qq.com
 * Csdn :https://blog.csdn.net/Jeff_YaoJie
 * Github: https://github.com/Jay-YaoJie
 * Created :  2019-03-18.
 * description ：
 */
object BleService {
    val tag: String = "BleService"
    @Synchronized
    fun into() {
        //初始化蓝牙配置
//        BleManager.getInstance()
//                // .enableLog(true)
//                .setReConnectCount(5, 5000)
//                .setSplitWriteNum(20)
//                .setConnectOverTime(10000)
//                .setOperateTimeout(5000);
        BleManager.getInstance().init(instance)
        BleManager.getInstance()
            // .enableLog(true)
            // .setReConnectCount(0, 1000*10)//设置重新连接
            .setSplitWriteNum(20)
            .setConnectOverTime(1000).operateTimeout = 1000//设置超时连接
    }


    /*************扫描蓝牙  连接蓝牙********************/
    /**
     * 扫描蓝牙Ble设备的回调
     */
    var periodScanCallback: BleScanCallback = object : BleScanCallback() {
        override fun onScanStarted(success: Boolean) {
            LogUtils.d(tag, "开始扫描蓝牙。。")
        }

        override fun onScanning(bleDevice: BleDevice) {
            //            Log.d(tag, "正在扫描蓝牙。。" + bleDevice.toString() + "---bleDevice.getName()=" + bleDevice.getName());
            //            bleDevice2 = bleDevice;
            //            //  if (bleDevice != null && bleDevice.getName() != null && bleDevice.getName().contains("KIWY")) {
            //            leDeviceList.add(bleDevice);//保存数据
            //            for (int i = 0; i < bluetoothData.size(); i++) {
            //                //如果已经连接了就不用在查询了
            //                for (int j = 0; j < leDeviceList.size(); j++) {
            //                    if (bluetoothData.get(i).getBleDevice() != null && bluetoothData.get(i).getBleDevice().getMac().contains(leDeviceList.get(j).getMac())) {
            //                        //如果查询相同则不用在保存了
            //                        leDeviceList.remove(j);
            //                    }
            //                }
            //            }
            //            getActivity().runOnUiThread(new Runnable() {
            //                @Override
            //                public void run() {
            //                    deviceAdapter.setList(leDeviceList);
            //                    deviceAdapter.notifyDataSetChanged();
            //                }
            //            });
        }
        //}

        override fun onScanFinished(scanResultList: List<BleDevice>) {
            LogUtils.d(tag, "扫描蓝牙结束。。" + scanResultList.size)
            //            //   leDeviceList = scanResultList;//保存数据
            //            if (bluetoothData == null || bluetoothData.size() <= 0) {
            //                leDeviceList = new ArrayList<>();
            //                Toast.makeText(getActivity(), "Not ScanResul Bluetooth !", Toast.LENGTH_LONG).show();
            //                dialog.dismiss();
            //            } else {
            //                getActivity().runOnUiThread(new Runnable() {
            //                    @Override
            //                    public void run() {
            //                        deviceAdapter.setList(leDeviceList);
            //                        deviceAdapter.notifyDataSetChanged();
            //                    }
            //                });
            //            }

        }
    }

    // 连接、断连、监控连接状态
    fun connectDevice(bleDecice: BleDevice) {
        Thread(Runnable {
            LogUtils.d(tag, bleDecice.toString() + " 连接、断连、监控连接状态 bleDevice1.getMac()=" + bleDecice.mac)
            //与设备连接监听
            BleManager.getInstance().connect(bleDecice.mac, object : BleGattCallback() {
                override fun onStartConnect() {
                    LogUtils.d(tag, "开始连接*****")
                }


                override fun onConnectSuccess(bleDevice: BleDevice?, gatt: BluetoothGatt?, status: Int) {
                    LogUtils.d(tag, "连接成功*****bleDevice1.getScanRecord()=" + bleDecice.scanRecord)
                }

                override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
                    LogUtils.d(tag, "连接出错*****" + exception.toString() + "---mac=" + bleDecice.mac)
                }

                override fun onDisConnected(
                    isActiveDisConnected: Boolean,
                    device: BleDevice?,
                    gatt: BluetoothGatt?,
                    status: Int
                ) {
                    LogUtils.d(tag, "连接关闭*****---position=$bleDecice.mac")
                }
            })
        }).start()
    }

    /*****************************************************************************************/

    /*有两种方式可以接收通知，indicate和notify。indicate和notify的区别就在于，indicate是一定会收到数据，notify有可能会丢失数据。
     indicate底层封装了应答机制，如果没有收到中央设备的回应，会再次发送直至成功*/
    //服务的
    var open_service = UUID.fromString("0000f925-0000-1000-8000-00805f9b34fb")
    //通知
    var open_notify = UUID.fromString("00002692-0000-1000-8000-00805f9b34fb")

    // 注册通知，接收消息
    fun openIndicate(bleDecice: BleDevice) {
        Thread(Runnable {
            BleManager.getInstance().notify(
                bleDecice,
                open_service.toString(), open_notify.toString(),
                object : BleNotifyCallback() {
                    override fun onNotifyFailure(exception: BleException?) {
                        // 打开通知操作失败
                        Log.d(tag, "订阅 uuid_notify 打开通知操作失败" + exception.toString())
                        //断开设备连接
                        BleManager.getInstance().disconnect(bleDecice)
                        //stopNotify(position)//结束通知
                    }

                    override fun onNotifySuccess() {
                        // 打开通知操作成功////发送
                        Log.d(tag, "订阅 uuid_notify 打开通知操作成功")
                    }

                    override fun onCharacteristicChanged(data: ByteArray?) {
                        // 打开通知后，设备发过来的数据将在这里出现
                        Log.d(
                            tag,
                            "订阅 uuid_notify 接收数据---mac=" + bleDecice.mac + "----HexUtil.formatHexString(data)=" + HexUtil.formatHexString(
                                data
                            )
                        )

                    }
                }
            )
        }).start()
    }

    /*
    *
    *   public UUID uuid_service = UUID.fromString("00010203-0405-0607-0809-0a0b0c0d1910");//服务的
        public UUID uuid_notify = UUID.fromString("00010203-0405-0607-0809-0a0b0c0d1911");//通知

        public UUID uuid_service=UUID.fromString("0000f925-0000-1000-8000-00805f9b34fb");//服务的
        public UUID   uuid_notify=UUID.fromString("d44bc439-abfd-45a2-b575-925416129601");//通知
        public UUID uuid_notifyC= UUID.fromString("d44bc439-abfd-45a2-b575-925416129602");//结束通知
        public UUID uuid_chara=UUID.fromString("d44bc439-abfd-45a2-b575-925416129600");//客户端的

    * */
    //服务的
    var stop_service = UUID.fromString("0000f925-0000-1000-8000-00805f9b34fb")
    //通知
    var stop_notify = UUID.fromString("d44bc439-abfd-45a2-b575-925416129601")

    // 关闭indicate,结束接收消息
    fun stopNotify(bleDecice: BleDevice) {
        //结束通知
        Thread(Runnable {
            LogUtils.d(tag, "结束通知mac=" + bleDecice.mac)
            BleManager.getInstance().stopNotify(bleDecice, stop_service.toString(), stop_notify.toString())
        }).start()
    }

    /*****************************************************************************************************/

    /*写  在不扩展MTU和扩展MTU无效的情况下，当要发送超过20个字节的长数据时，需要进行转包。该参数boolean split表示是否使用数据包传递;
默认情况下，write没有boolean split参数的方法将超过20个字节转包给数据。
在onWriteSuccess回调方法上：current表示当前发送的数据包数，并total表示此时的总数据包数据，并justWrite表示刚刚发送的成功数据包。*/
    // 服务的
    var write_service: UUID = UUID.fromString("00001899-0000-1000-8000-00805f9b34fb")
    // 写数据的UUID数据
    var write_chara: UUID = UUID.fromString("00002693-0000-1000-8000-00805f9b34fb")

    //写 数据
    fun bleWrite(bleDecice: BleDevice, data: ByteArray) {
        Thread(Runnable {
            LogUtils.d(tag, "发送指令 data.hashCode()" + data.hashCode())
            BleManager.getInstance()
                .write(bleDecice,
                    write_service.toString(),
                    write_chara.toString(),
                    data, object : BleWriteCallback() {
                        override fun onWriteSuccess(current: Int, total: Int, justWrite: ByteArray?) {
                            //发送数据之后   // 发送数据到设备成功（分包发送的情况下，可以通过方法中返回的参数可以查看发送进度）
                            LogUtils.d(
                                tag,
                                "onWriteSuccessp写入数据成功" + justWrite + "-----" + HexUtil.formatHexString(justWrite, true)
                            )
                        }

                        override fun onWriteFailure(exception: BleException?) {
                            LogUtils.d(tag, "写入数据出错" + exception.toString())
                            stopNotify(bleDecice)//结束通知
                            //断开设备连接
                            BleManager.getInstance().disconnect(bleDecice)

                        }
                    })
        }).start()
    }

    //停止使用，清理蓝牙资源
    fun destroy() {
        BleManager.getInstance().destroy();//停止使用，清理资源
    }
}