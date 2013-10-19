package com.inter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.inter.R;
import com.inter.BombPhone.MyBDLocationListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BombPhone extends Activity {

	private Button tbtn, swbtn;
	private Toast toast;
	private TextView tv, tv1;
	private Drawable dr;
	private int available = 0;
	private String result, result1, username;
	private double latitude, longitude;
	private LocationClient mLocationClient;
	private MyBDLocationListener mBDLocationListener;
	private static final String TAG = "DemoActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
		setContentView(R.layout.bombphone);

		swbtn = (Button) findViewById(R.id.swbutton);
		tbtn = (Button) findViewById(R.id.tance);
		tv = (TextView) findViewById(R.id.textview);
		tv1 = (TextView) findViewById(R.id.textview1);
		// dr = this.getResources().getDrawable(R.drawable.fuck);

		tbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// ////////////////////////////////////////////////////////////

				// ///////////////////通信（封装在按钮响应中）
				/* http://hackerday.sinaapp.com/index.php/user/testJSon */
				if (available == 1) {
					
					Intent intent = getIntent();
					
					username = intent.getStringExtra("user");
					
					String url = "http://hackerday.sinaapp.com/index.php/position/getGFriends";
					HttpPost httpRequest = new HttpPost(url);
					
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("name", username));
					params.add(new BasicNameValuePair("lat", "" + latitude));
					params.add(new BasicNameValuePair("long", "" + longitude));
					params.add(new BasicNameValuePair("model",android.os.Build.MODEL));
					params.add(new BasicNameValuePair("radius", "1500"));
					params.add(new BasicNameValuePair("app", ""));
					try {
						HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");

						httpRequest.setEntity(httpEntity);

						HttpClient httpClient = new DefaultHttpClient();

						HttpResponse httpResponse = httpClient.execute(httpRequest);

						if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
							result = EntityUtils.toString(httpResponse.getEntity());
							// Log.i(TAG,"result = "+result)
							Intent intent1 = new Intent();
							intent1.putExtra("res", result);
							intent1.setClass(BombPhone.this,FindGfriend.class);
							BombPhone.this.startActivity(intent1);
							//tv.setText(result);
							if (result.equals("true")) {
							}
						} else {
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
				} else {
					toast = Toast.makeText(getApplicationContext(), "请先打开开关！",Toast.LENGTH_SHORT);
					LinearLayout toastView = (LinearLayout) toast.getView();
					toast.show();
				}
			}
		});

		swbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Log.i("xxx", available + "");
				/*if (available == 0) {
					//imgView.setBackgroundResource(R.drawable.switch_on);
					available = 1;
					toast = Toast.makeText(getApplicationContext(),
							"开始找好基友喽:-)！", Toast.LENGTH_SHORT);
					toast.show();
				} else {
					//imgView.setBackgroundResource(R.drawable.switch_off);
					available = 0;
					toast = Toast.makeText(getApplicationContext(),
							"又要寂寞了:-(！", Toast.LENGTH_SHORT);
					toast.show();
				}*/
				// ///////////////////通信（封装在按钮响应中）
				
				available^=1;
				//Toast
				if(available == 0){
					toast = Toast.makeText(getApplicationContext(), "机友都不理我...", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					LinearLayout toastView = (LinearLayout) toast.getView();
					ImageView imageCodeProject = new ImageView(getApplicationContext());
					imageCodeProject.setImageResource(R.drawable.no);
					toastView.addView(imageCodeProject, 0);
					toast.show();
				}else{
					toast = Toast.makeText(getApplicationContext(), "去找机友喽...", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					LinearLayout toastView = (LinearLayout) toast.getView();
					ImageView imageCodeProject = new ImageView(getApplicationContext());
					imageCodeProject.setImageResource(R.drawable.yes);
					toastView.addView(imageCodeProject, 0);
					toast.show();
				}
				
				Intent intent3 = getIntent();
				username = intent3.getStringExtra("user");
				String url = "http://hackerday.sinaapp.com/index.php/position/canbeFound";
				HttpPost httpRequest = new HttpPost(url);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("name", username));
				params.add(new BasicNameValuePair("state", "" + available));
				try {
					HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");

					httpRequest.setEntity(httpEntity);

					HttpClient httpClient = new DefaultHttpClient();

					HttpResponse httpResponse = httpClient.execute(httpRequest);

					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						result1 = EntityUtils.toString(httpResponse.getEntity());
						Log.i(TAG, "result4 = ");
						// Log.i(TAG,"result = "+result);
						if (result1.equals("true")) {

						} else {
							// btn2.setText("信息不能为空！");
						}

					} else {
						// btn2.setText("信息不能为空！");

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
			// imgbtn.setBackgroundDrawable(dr);
		});
		// ////////////////////////////////////////////////////////////

		mLocationClient = new LocationClient(this.getApplicationContext());

		mBDLocationListener = new MyBDLocationListener();
		mLocationClient.registerLocationListener(mBDLocationListener);

		LocationClientOption option = new LocationClientOption();

		// 需要地址信息，设置为其他任何值（string类型，且不能为null）时，都表示无地址信息。
		option.setAddrType("all");
		// 设置是否返回POI的电话和地址等详细信息。默认值为false，即不返回POI的电话和地址信息。
		option.setPoiExtraInfo(false);

		// 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
		option.setProdName("当前位置");

		// 打开GPS，使用gps前提是用户硬件打开gps。默认是不打开gps的。
		option.setOpenGps(false);

		// 定位的时间间隔，单位：ms
		// 当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。
		// option.setScanSpan(1000);

		// 查询范围，默认值为500，即以当前定位位置为中心的半径大小。
		option.setPoiDistance(500);
		// 禁用启用缓存定位数据
		option.disableCache(true);

		// 坐标系类型，百度手机地图对外接口中的坐标系默认是bd09ll
		option.setCoorType("bd0911");

		// 设置最多可返回的POI个数，默认值为3。由于POI查询比较耗费流量，设置最多返回的POI个数，以便节省流量。
		option.setPoiNumber(3);

		// 设置定位方式的优先级。
		// 即使有GPS，而且可用，也仍旧会发起网络请求。这个选项适合对精确坐标不是特别敏感，但是希望得到位置描述的用户。
		option.setPriority(LocationClientOption.NetWorkFirst);

		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	final class MyBDLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			Log.e(TAG, "---------onReceiveLocation()---------");

			if (location == null) {
				Log.e(TAG,
						"---------onReceiveLocation------location is NULL----");
				return;
			}

			int type = location.getLocType();
			Log.i(TAG, "当前定位采用的类型是：type = " + type);

			String coorType = location.getCoorType();
			Log.i(TAG, "坐标系类型:coorType = " + coorType);

			// 判断是否有定位精度半径
			if (location.hasRadius()) {
				// 获取定位精度半径，单位是米
				float accuracy = location.getRadius();
				Log.i(TAG, "accuracy = " + accuracy);
			}

			if (location.hasAddr()) {
				// 获取反地理编码。 只有使用网络定位的情况下，才能获取当前位置的反地理编码描述。
				String address = location.getAddrStr();
				Log.i(TAG, "address = " + address);
			}

			/*
			 * String province = location.getProvince(); // 获取省份信息 String city =
			 * location.getCity(); // 获取城市信息 String district =
			 * location.getDistrict(); // 获取区县信息
			 * 
			 * Log.i(TAG, "province = " + province); Log.i(TAG, "city = " +
			 * city); Log.i(TAG, "district = " + district);
			 */
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			Log.i(TAG, "latitude = " + latitude);
			Log.i(TAG, "longitude = " + longitude);
			tv.setText("您当前位置的纬度是" + latitude + "度");
			tv1.setText("您当前位置的经度是" + longitude + "度");
		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {

			Log.e(TAG, "---------onReceivePoi()---------");

			if (poiLocation == null) {
				Log.e(TAG, "---------onReceivePoi------location is NULL----");
				return;
			}

			if (poiLocation.hasPoi()) {
				String poiStr = poiLocation.getPoi();
				Log.i(TAG, "poiStr = " + poiStr);

			}

			if (poiLocation.hasAddr()) {
				// 获取反地理编码。 只有使用网络定位的情况下，才能获取当前位置的反地理编码描述。
				String address = poiLocation.getAddrStr();
				Log.i(TAG, "address = " + address);
			}
		}

	}
}
