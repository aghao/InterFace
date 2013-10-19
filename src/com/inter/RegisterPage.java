package com.inter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterPage extends Activity{
	private TextView myTextView = null;
	private Button btn2;
	private EditText edt2,edt3;
	private String username1,password1,result;
	private static final String TAG= "DemoActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		btn2 = (Button)findViewById(R.id.button2);
		edt2 = (EditText)findViewById(R.id.edittext2);
		edt3 = (EditText)findViewById(R.id.edittext3);
		
	btn2.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
/////////////////////通信（封装在按钮响应中）
	username1 = edt2.getText().toString();
	password1 = edt3.getText().toString();
	/*http://hackerday.sinaapp.com/index.php/user/testJSon*/
	String url = "http://hackerday.sinaapp.com/index.php/user/userRegister";
	HttpPost httpRequest = new HttpPost(url);

	List<NameValuePair> params = new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair("name", username1));
	params.add(new BasicNameValuePair("pass", password1));


	try {
		HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");
	
		httpRequest.setEntity(httpEntity);
		
		HttpClient httpClient = new DefaultHttpClient();
	
		HttpResponse httpResponse = httpClient.execute(httpRequest);
		
		if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			 result = EntityUtils.toString(httpResponse.getEntity());
		//		btn2.setText(result);
			Log.i(TAG,"result = "+result);
			if(result.equals("true")){
				//生成一个Intent对象
				Intent intent = new Intent();
				intent.setClass(RegisterPage.this,MainActivity.class);
				RegisterPage.this.startActivity(intent);	
			}else{
				btn2.setText("信息不能为空！");
			}
				
		}else{
			btn2.setText("request error");
			
		}

	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		}
	});
		
	}

}
