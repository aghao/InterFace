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

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button btn,btn1;
	private TextView tv;
	private String result;
	private EditText edt,edt1;
	private String username,password;
	private static final String TAG= "DemoActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
	        .detectDiskReads()
	        .detectDiskWrites()
	        .detectAll()   // or .detectAll() for all detectable problems
	        .penaltyLog()
	        .build());
	     StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
	        .detectLeakedSqlLiteObjects()
	        .detectLeakedClosableObjects()
	        .penaltyLog()
	        .penaltyDeath()
	        .build());
		setContentView(R.layout.activity_main);
		
		tv = (TextView)findViewById(R.id.textview1);
		btn1 = (Button)findViewById(R.id.button1);
		btn = (Button)findViewById(R.id.button);
		edt = (EditText)findViewById(R.id.edittext);
		edt1 = (EditText)findViewById(R.id.edittext1);
		
	btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//���һ��Intent����
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,RegisterPage.class);
				MainActivity.this.startActivity(intent);				
			}
		});
	
	btn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

/////////////////////ͨ�ţ���װ�ڰ�ť��Ӧ�У�
			username = edt.getText().toString();
			password = edt1.getText().toString();
				if(username.equals("") || password.equals("")){
					tv.setText("��¼��Ϣ����:-(��");
					tv.setTextColor(Color.RED);
				}else{
			/*http://hackerday.sinaapp.com/index.php/user/testJSon*/
			String url = "http://hackerday.sinaapp.com/index.php/user/userCheck";
			HttpPost httpRequest = new HttpPost(url);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", username));
			params.add(new BasicNameValuePair("pass", password));


			try {
				HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");
			
				httpRequest.setEntity(httpEntity);
				
				HttpClient httpClient = new DefaultHttpClient();
			
				HttpResponse httpResponse = httpClient.execute(httpRequest);
				int i = httpResponse.getStatusLine().getStatusCode();
				System.out.println(i);
				if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					 result = EntityUtils.toString(httpResponse.getEntity());
					if(!result.equals("false")){
					//Log.i(TAG,"result = "+result);
					Intent intent1 = new Intent();
					intent1.putExtra("user", username);
					intent1.setClass(MainActivity.this,BombPhone.class);
					MainActivity.this.startActivity(intent1);
				//	overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
					}
				}else{
					btn.setText("request error");
					
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
		}
	});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
