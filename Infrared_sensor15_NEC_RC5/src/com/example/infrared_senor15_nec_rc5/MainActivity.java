package com.example.infrared_senor15_nec_rc5;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener, OnTouchListener{
	private static String LOG = "MainActivity";
	public Button [] bt_num = new Button[10];
	public Button bt_vol_up=null;
	public Button bt_vol_down=null;
	
	public Button bt_ch_up=null;
	public Button bt_ch_down=null;
	
	public Button bt_shut_down=null;
	
	public Button bt_ok=null;
	public Button bt_dir_up=null;
	public Button bt_dir_down=null;
	public Button bt_dir_left=null;
	public Button bt_dir_right=null;
	
	public Button bt_menu=null;
	public Button bt_exit=null;
	
	public Button bt_codec_type=null;
	
	public String str_msg = null;
	
	public Thread tLongPress;
	int codecType=0;
	private LocalService myservice = null;// 绑定的service对象
	Binder binder=null;
	Intent intent = null;

	// 注意，按照数字音频的知识，这个算出来的是一秒钟buffer的大小。
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bt_num[0]=(Button) findViewById(R.id.bt_num0);
		bt_num[1]=(Button) findViewById(R.id.bt_num1);
		bt_num[2]=(Button) findViewById(R.id.bt_num2);
		bt_num[3]=(Button) findViewById(R.id.bt_num3);
		bt_num[4]=(Button) findViewById(R.id.bt_num4);
		bt_num[5]=(Button) findViewById(R.id.bt_num5);
		bt_num[6]=(Button) findViewById(R.id.bt_num6);
		bt_num[7]=(Button) findViewById(R.id.bt_num7);
		bt_num[8]=(Button) findViewById(R.id.bt_num8);
		bt_num[9]=(Button) findViewById(R.id.bt_num9);
		
		for(int i=0;i<10;i++){
			bt_num[i].setOnClickListener(this);
		}

		bt_vol_up=(Button) findViewById(R.id.bt_vol_up);
		bt_vol_down=(Button) findViewById(R.id.bt_vol_down);
		bt_vol_up.setOnClickListener(this);
		bt_vol_down.setOnClickListener(this);
		bt_vol_up.setOnTouchListener(this);
		bt_vol_down.setOnTouchListener(this);
		
		
		bt_ch_up=(Button) findViewById(R.id.bt_ch_up);
		bt_ch_down=(Button) findViewById(R.id.bt_ch_down);
		bt_ch_up.setOnClickListener(this);
		bt_ch_down.setOnClickListener(this);
		
		bt_ok=(Button) findViewById(R.id.bt_ok);
		bt_ok.setOnClickListener(this);
		
		bt_dir_up=(Button) findViewById(R.id.bt_dir_up);
		bt_dir_down=(Button) findViewById(R.id.bt_dir_down);
		bt_dir_left=(Button) findViewById(R.id.bt_dir_left);
		bt_dir_right=(Button) findViewById(R.id.bt_dir_right);
		bt_dir_up.setOnClickListener(this);
		bt_dir_down.setOnClickListener(this);
		bt_dir_left.setOnClickListener(this);
		bt_dir_right.setOnClickListener(this);
		
		bt_menu=(Button) findViewById(R.id.bt_menu);
		bt_exit=(Button) findViewById(R.id.bt_exit);
		bt_menu.setOnClickListener(this);
		bt_exit.setOnClickListener(this);
		
		bt_shut_down=(Button) findViewById(R.id.bt_shut_down);
		bt_shut_down.setOnClickListener(this);
		
		bt_codec_type=(Button) findViewById(R.id.bt_codec_type);
		bt_codec_type.setOnClickListener(this);

		// 开始绑定
		intent = new Intent(MainActivity.this, LocalService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}

	// 连接对象，重写OnserviceDisconnected和OnserviceConnected方法
	public ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.i(LOG, "onServiceDisconnected>>>>>>>>");
			myservice = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(LOG, "onServiceConnected>>>>>>>>");
			myservice = ((LocalService.LocalBinder) service).getService();
			binder=(Binder) service;
			Log.i(LOG, myservice + ">>>>>>>>");
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@SuppressLint("Recycle")
	public void sendMessage2Server(int op,int codecType){
		Log.i(LOG, "op="+op);
		Parcel parcel_data = Parcel.obtain();
		parcel_data.writeInt(codecType);
		parcel_data.writeInt(op);
		Parcel parcel_reply = Parcel.obtain();
		try {
			binder.transact(0, parcel_data, parcel_reply, BIND_AUTO_CREATE);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		parcel_reply.setDataPosition(0);
		String s = parcel_reply.readString();
		Log.i(LOG, "readString="+s);
	}

	@Override
	public void onClick(View arg0) {

		int op = -1;
		
		switch (arg0.getId()) {
		case R.id.bt_num0:
			op = ActionButten.num0;
			break;
		case R.id.bt_num1:
			op = ActionButten.num1;
			break;
		case R.id.bt_num2:
			op = ActionButten.num2;
			break;
		case R.id.bt_num3:
			op = ActionButten.num3;
			break;
		case R.id.bt_num4:
			op = ActionButten.num4;
			break;
		case R.id.bt_num5:
			op = ActionButten.num5;
			break;
		case R.id.bt_num6:
			op = ActionButten.num6;
			break;
		case R.id.bt_num7:
			op = ActionButten.num7;
			break;
		case R.id.bt_num8:
			op = ActionButten.num8;
			break;
		case R.id.bt_num9:
			op = ActionButten.num9;
			break;
		case R.id.bt_shut_down:
			op = ActionButten.shut_down;
			break;
		case R.id.bt_vol_up:
			op = ActionButten.vol_up;
			break;
		case R.id.bt_vol_down:
			op = ActionButten.vol_down;
			break;
		case R.id.bt_ch_up:
			op = ActionButten.ch_up;
			break;
		case R.id.bt_ch_down:
			op = ActionButten.ch_down;
			break;
		case R.id.bt_ok:
			op = ActionButten.ok;
			break;
		case R.id.bt_dir_up:
			op = ActionButten.dir_up;
			break;
		case R.id.bt_dir_down:
			op = ActionButten.dir_down;
			break;
		case R.id.bt_dir_left:
			op = ActionButten.dir_left;
			break;
		case R.id.bt_dir_right:
			op = ActionButten.dir_right;
			break;
		case R.id.bt_menu:
			op = ActionButten.menu;
			break;
		case R.id.bt_codec_type:
			op = ActionButten.change_codec_type;
			if(codecType==0){
				codecType=1;
				bt_codec_type.setText("NEC");
			}else{
				codecType=0;
				bt_codec_type.setText("RC5");
			}
			break;

		}

		
		sendMessage2Server(op,codecType);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		unbindService(conn);
	}
	LongPressThread lpt=null;
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		
		switch (arg0.getId()) {
		case R.id.bt_vol_up:
			Log.i(LOG, "onTouch=bt_vol_up");
			if(arg1.getAction() == MotionEvent.ACTION_DOWN){
				Log.i(LOG, "MotionEvent.ACTION_DOWN");
				lpt=new LongPressThread(ActionButten.vol_up);
				tLongPress=new Thread(lpt);
				tLongPress.start();
			}else if(arg1.getAction() == MotionEvent.ACTION_UP){

				Log.i(LOG, "MotionEvent.ACTION_UP");
				lpt.tStop();
				tLongPress.interrupt();
			}
			break;
		case R.id.bt_vol_down:
			Log.i(LOG, "onTouch=bt_vol_down");
			if(arg1.getAction() == MotionEvent.ACTION_DOWN){
				Log.i(LOG, "MotionEvent.ACTION_DOWN");
				lpt=new LongPressThread(ActionButten.vol_down);
				tLongPress=new Thread(lpt);
				tLongPress.start();
			}else if(arg1.getAction() == MotionEvent.ACTION_UP){

				Log.i(LOG, "MotionEvent.ACTION_UP");
				lpt.tStop();
				tLongPress.interrupt();
			}
			break;


		default:
			break;
		}
		
		return false;
	}
	
	
	public class LongPressThread implements Runnable {
		boolean bRun = true;
		int op=0;
		LongPressThread(int op){
			this.op=op;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (bRun) {
				Log.d("MainActivity", "run end");
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sendMessage2Server(op,codecType);


			}
		}
		
		public void tStop() {
			bRun = false;
		}

		public void tStart() {
			bRun = true;
		}

	}

}
