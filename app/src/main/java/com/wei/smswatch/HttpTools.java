package com.wei.smswatch;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class HttpTools {
    public static String url = "http://207.246.104.61:81/data";
    public static  String SendPostMess(String postdata){
        String msg = "";
        try{
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            String data = "data=" + postdata;
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();

                ByteArrayOutputStream message = new ByteArrayOutputStream();
                int len = 0;
                byte buf[] = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    message.write(buf, 0, len);
                }
                is.close();
                message.close();
                msg = new String(message.toByteArray());
                return msg;
            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
