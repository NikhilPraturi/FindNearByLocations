package com.example.akhil.smartcityapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by akhil on 31-12-2017.
 */

public class DownloadUrl {

public String readUrl(String myurl)
{
    String data="";
    InputStream inputStream=null;
    HttpURLConnection httpURLConnection=null;

    try {
        URL url = new URL(myurl);
       httpURLConnection= (HttpURLConnection)url.openConnection();
    httpURLConnection.connect();
    inputStream=httpURLConnection.getInputStream();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer=new StringBuffer();
        String line="";
        while ((line=bufferedReader.readLine())!=null)
        {
stringBuffer.append(line);
        }
        data=stringBuffer.toString();
        bufferedReader.close();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
/*finally {
        try {
            inputStream.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        httpURLConnection.disconnect();
    }*/
    return data;
}

}
