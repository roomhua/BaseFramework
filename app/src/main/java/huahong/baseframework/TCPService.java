package huahong.baseframework;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * Created by admin on 2017/8/4.
 * TCP连接
 */

public class TCPService extends Service{

    private final String IP = "119.23.234.159";
    private final int PORT = 8080;


    private String xsf_nsrsbh,appid,IDENTITYDATA,HEARTBEATDATA;

    /**是否停止任务*/
    private boolean mStart;

    private Socket mSocket;

    private boolean connect = false;

    private int mReconnectionTime;


    private Thread mRedaThread = new Thread(new Runnable() {
        @Override
        public void run() {

            reconnect();

            mHeartbeatThread.start();

            while (mStart) {
                try {
                    reconnect();
                    readData();
                } catch (Exception e) {
                    e.printStackTrace();

                    connect = false;
                    Log.e("mRedaThread",e.getMessage());
                }

            }

        }
    });



    private Thread mHeartbeatThread = new Thread(new Runnable() {
        @Override
        public void run() {

            while (mStart) {

                try {

                    SystemClock.sleep(20000);
                    sendData(HEARTBEATDATA);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("mHeartbeatThread",e.getMessage());
                    connect = false;

                }

            }
        }
    });


    @Override
    public IBinder onBind(Intent intent) {
        return new MyIBinder();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        connect = false;
        mStart = true;
        mReconnectionTime = 100;
        initData();

        mRedaThread.start();

    }

    private void initData() {

//        Prefs prefs = Prefs.getInstance(this);
//        xsf_nsrsbh = prefs.getString(Config.NSRSBH);
//        appid = Config.APPID;
        IDENTITYDATA = "{\"type\":101,\"xsf_nsrsbh\":\""+xsf_nsrsbh+"\"," +
                "\"appid\":\""+ appid +"\"}";
        HEARTBEATDATA = "{\"type\":102,\"appid\":\""+ appid +"\"}";
    }


    @Override
    public void onDestroy() {
        mStart = false;
        connect = false;
        disConnect();

        super.onDestroy();
    }

    /***
     * 连接socket
     * @return
     */
    private  synchronized void connect() {
        if (!mStart) {
            return;
        }


        try {
            mSocket = new Socket();
            mSocket.setReuseAddress(true);
            mSocket.setSoLinger(true,0);
            mSocket.connect(new InetSocketAddress(IP, PORT),5000);
            connect = true;
            sendData(IDENTITYDATA);
        } catch (Exception e) {
            e.printStackTrace();
            connect = false;
        }

        Log.e("socket connect",connect+"");
    }


    /**
     * 断开连接
     */
    private void disConnect() {
        if (mSocket != null) {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 发送数据
     * @param data
     * @throws IOException
     */
    private  void sendData(String data) throws IOException {
        if (mSocket == null && !connect && !mStart) {
            return;
        }


        Log.e("data" ,"------------->" + data);

        OutputStream outputStream = mSocket.getOutputStream();

        if (mSocket.isConnected()) {
            outputStream.write(new String(data.getBytes(),"UTF-8").getBytes());

            outputStream.flush();
        }else {
            Log.e("http", "socket disconnected, not write");
            connect = false;
        }
    }


    /**
     * 读数据
     */
    private synchronized void readData() {
        if (mSocket == null && !connect && !mStart) {
            return;
        }


        try {
            InputStream is = mSocket.getInputStream();



            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024 * 30);

            byte[] buf = new byte[1024 * 30];

            int read = is.read(buf);

            if (read < 0) {
                return;
            }

            bos.write(buf,0,read);
            bos.flush();



            String sb = new String(bos.toByteArray(),"UTF-8");

            Log.e("readData--->",sb.toString());

            if (mListener != null && sb.length() > 0) {
                mListener.getData(sb.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();

            connect = false;
        }



    }


    /**
     * 重新连接scoket
     */
    private void reconnect() {
        while (!connect && mStart) {
            SystemClock.sleep(mReconnectionTime);
            if (mReconnectionTime == 100) {
                mReconnectionTime = 5000;
            }
            connect();
        }
    }


    public interface TransferDataListener {

        void getData(String data);
    }

    private TransferDataListener mListener;


    public class MyIBinder extends Binder {

        public void  setTransferDataListener(TransferDataListener transferDataListener) {
            mListener = transferDataListener;
        }


    }

}
