package example.com.expressapp.history.model;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import example.com.expressapp.adminGUID;

/**
 * Created by xyj64 on 2016/8/11.
 */
public class HistoryInfoPostBack implements iHistoryInfoPostBack {
    @Override
    public String getHistoryInfo(String adminGUID, String startTime, String endTime) {
        String result;
        HttpPost httpRequest=new HttpPost("http://"+ example.com.expressapp.adminGUID.ipAddress+":8080/Express/GetHisMsgMethod");
        //创建参数
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("adminGUID", adminGUID));
        params.add(new BasicNameValuePair("startTime", startTime));
        params.add(new BasicNameValuePair("endTime", endTime));
        //params.add(new BasicNameValuePair("flag","0"));
        try {
            //对提交数据进行编码
            httpRequest.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse httpResponse=(new DefaultHttpClient()).execute(httpRequest);
            //获取响应服务器的数据
            if (httpResponse.getStatusLine().getStatusCode()==200) {
                //利用字节数组流和包装的绑定数据
                byte[] data =new byte[128];
                //先把从服务端来的数据转化成字节数组
                data= EntityUtils.toByteArray((HttpEntity)httpResponse.getEntity());
                //再创建字节数组输入流对象
                ByteArrayInputStream bais = new ByteArrayInputStream(data);
                //绑定字节流和数据包装流
                DataInputStream dis = new DataInputStream(bais);
                //将字节数组中的数据还原成原来的各种数据类型，代码如下：
                result=new String(dis.readUTF());
                dis.close();
                bais.close();
                return result;
            }
            else
                return "Connected error!\nPlease check the network connections";
        } catch(ClientProtocolException e){
            e.printStackTrace();
            return e.toString();
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
            return e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
        //return "Connected error!\nPlease check the network connections";
        //return "Username or password is not correct.\nPlease try again.";
    }
}
