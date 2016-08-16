package example.com.expressapp.searchinformation.model;

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
 * Created by lxs on 2016/5/17.
 */
public class getLadingInfoBack implements iGetLadingBack{
    @Override
    public String getLadingInfo(String adminGuid,String startTime) throws Exception
    {
        String result;
        HttpPost httpRequest=new HttpPost("http://"+ adminGUID.ipAddress+":8080/Express/GetLadMsgMethod");
        //创建参数
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("adminGUID", adminGuid));
        params.add(new BasicNameValuePair("startTime", startTime));
        //params.add(new BasicNameValuePair("flag","0"));
        try {
            //对提交数据进行编码
            httpRequest.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse httpResponse=(new DefaultHttpClient()).execute(httpRequest);
            //获取响应服务器的数据
            if (httpResponse.getStatusLine().getStatusCode()==200) {
                result = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
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
