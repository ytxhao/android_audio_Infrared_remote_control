package com.example.infrared_senor15_nec_rc5;

import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class LocalService extends Service {
	private static final String TAG = "LocalService";

	private IBinder binder = new LocalService.LocalBinder();
	int op = -1;
	int codecType=0;
	int miniCodecType=0;
	boolean isClick = false;
	public Thread keyDeamon;
	public KeyDaemonRunnable kdr;
	public AudioManager audioMgr = null; // Audio管理器，用了控制音量
	public int maxVolume=0;

	/*
	 * int audioSampleRate = 44100; int audioBufSize = 100; byte[] audio_cmd =
	 * null; byte[] audio_data_byte = new byte[audioBufSize]; float[]
	 * audio_data_float = new float[audioBufSize]; boolean msgIsSending = false;
	 * AudioTrack msgAT;
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onBind");
		return binder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG, "onCreate");
		audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		// 获取最大音乐音量       
        maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume,AudioManager.FLAG_PLAY_SOUND);  
		kdr = new KeyDaemonRunnable();
		keyDeamon = new Thread(kdr);
		keyDeamon.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		boolean tlive;
		Log.i(TAG, "onDestroy");
		tlive = keyDeamon.isAlive();
		Log.i(TAG, "1 tlive=" + tlive);
		kdr.daemonStop();
		keyDeamon.interrupt();
		tlive = keyDeamon.isAlive();
		Log.i(TAG, "2 tlive=" + tlive);
		while (tlive) {

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tlive = keyDeamon.isAlive();
		}
		Log.i(TAG, "3 tlive=" + tlive);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);

	}

	// 定义内容类继承Binder
	public class LocalBinder extends Binder {
		// 返回本地服务
		LocalService getService() {
			return LocalService.this;
		}

		@Override
		protected boolean onTransact(int code, Parcel data, Parcel reply,
				int flags) throws RemoteException {
			// TODO Auto-generated method stub
			codecType = data.readInt();// 取出从Activity传进来的parcel的值
			op = data.readInt();
			// System.out.println("---------->"+s+op);
			Log.i(TAG, "codecType=" + codecType + " op=" + op);
			reply.writeString("哈哈");// 为parcel2添加一个值
			isClick = true;
			return super.onTransact(code, data, reply, flags);
		}

	}

	public class KeyDaemonRunnable implements Runnable {
		boolean bRun = true;
		SendSoundDaemonRunnable ssdr = new SendSoundDaemonRunnable();
		public Thread SSDeamon;
		boolean ssdLive = true;
		boolean ssdDead = false;
		int cmd_mode=0;
		int cmdKeyCode=0;
		KeyDaemonRunnable() {
			bRun = true;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			while (bRun) {
//				while (!isClick && bRun == true) {
//					try {
//						Thread.sleep(2);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
				if (isClick) {
					isClick = false;
					switch (op) {
					case ActionButten.num0:

						cmd_mode = MiniCmd.getCmdMode(ActionButten.num0);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.num0,
								miniCodecType);

						break;
					case ActionButten.num1:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.num1);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.num1,
								miniCodecType);

						break;
					case ActionButten.num2:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.num2);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.num2,
								miniCodecType);

						break;
					case ActionButten.num3:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.num3);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.num3,
								miniCodecType);

						break;
					case ActionButten.num4:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.num4);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.num4,
								miniCodecType);

						break;
					case ActionButten.num5:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.num5);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.num5,
								miniCodecType);

						break;
					case ActionButten.num6:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.num6);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.num6,
								miniCodecType);

						break;
					case ActionButten.num7:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.num7);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.num7,
								miniCodecType);

						break;
					case ActionButten.num8:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.num8);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.num8,
								miniCodecType);

						break;
					case ActionButten.num9:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.num9);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.num9,
								miniCodecType);

						break;

					case ActionButten.vol_up:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.vol_up);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.vol_up,
								miniCodecType);

						break;
					case ActionButten.vol_down:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.vol_down);
						cmdKeyCode = MiniCmd.getCmdKeyCode(
								ActionButten.vol_down, miniCodecType);

						break;
					case ActionButten.ch_up:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.ch_up);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.ch_up,
								miniCodecType);

						break;
					case ActionButten.ch_down:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.ch_down);
						cmdKeyCode = MiniCmd.getCmdKeyCode(
								ActionButten.ch_down, miniCodecType);

						break;
					case ActionButten.shut_down:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.shut_down);
						cmdKeyCode = MiniCmd.getCmdKeyCode(
								ActionButten.shut_down, miniCodecType);

						break;

					case ActionButten.ok:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.ok);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.ok,
								miniCodecType);

						break;

					case ActionButten.dir_up:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.dir_up);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.dir_up,
								miniCodecType);

						break;
					case ActionButten.dir_down:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.dir_down);
						cmdKeyCode = MiniCmd.getCmdKeyCode(
								ActionButten.dir_down, miniCodecType);

						break;
					case ActionButten.dir_left:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.dir_left);
						cmdKeyCode = MiniCmd.getCmdKeyCode(
								ActionButten.dir_left, miniCodecType);

						;
						break;
					case ActionButten.dir_right:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.dir_right);
						cmdKeyCode = MiniCmd.getCmdKeyCode(
								ActionButten.dir_right, miniCodecType);

						break;
					case ActionButten.menu:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.menu);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.menu,
								miniCodecType);

						break;
					case ActionButten.exit:
						cmd_mode = MiniCmd.getCmdMode(ActionButten.exit);
						cmdKeyCode = MiniCmd.getCmdKeyCode(ActionButten.exit,
								miniCodecType);

						break;
					case ActionButten.change_codec_type:
						if (codecType == 0) {
							miniCodecType = MinicodecType.typeRC5;
						} else {
							miniCodecType = MinicodecType.typeNEC;
						}

						continue;
						// break;

					default:
						break;
					}
					ssdr.setDecode_mode_and_data(cmd_mode, cmdKeyCode,miniCodecType);
					SSDeamon = new Thread(ssdr);
					SSDeamon.start();
					
					while (SSDeamon.isAlive()) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					ssdr.stopCmd();
					
				}
			}

			ssdr.daemonStop();
			SSDeamon.interrupt();
			ssdLive = SSDeamon.isAlive();
			Log.i(TAG, "2 ssdLive=" + ssdLive);
			while (ssdLive) {

				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ssdLive = SSDeamon.isAlive();
			}

		}

		public void daemonStop() {
			bRun = false;

		}

		public void daemonStart() {
			bRun = true;
		}

		public void stopCmd() {
			Log.i("Msg", "stopCmd");

		}
	}

	public class SendSoundDaemonRunnable implements Runnable {
		boolean bRun = true;
		int i = 0;

	 int audioSampleRate = 44100;
	 int decode_mode=0;
	 int decode_data=0;
	 int miniCodecType=0;
     

		byte[] audio_cmd = null;
		boolean msgIsSending = false;
		AudioTrack msgAT;
		
		double y = 0;
		public int msgMinBufferSize = AudioTrack.getMinBufferSize(
				audioSampleRate,
				 AudioFormat.CHANNEL_OUT_STEREO,//双声道
				AudioFormat.ENCODING_PCM_8BIT);// 一个采样点8比特-1个字节
		int tmp1=msgMinBufferSize*2;
		byte[] audio_data_byte = new byte[tmp1];

	//	NECCodec necCodec=new NECCodec(msgMinBufferSize);
		
		MiniCodec miniCodec=new MiniCodec(msgMinBufferSize);
		
		public int getDecode_mode() {
		return decode_mode;
	}

	public void setDecode_mode_and_data(int decode_mode,int decode_data ,int miniCodecType) {
		this.decode_mode = decode_mode;
		this.decode_data = decode_data;
		this.miniCodecType=miniCodecType;
	//	this.decode_mode = 0;
	//	this.decode_data = 0x50af17e8;
	}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			startCmd();
			Log.i(TAG, "SendSoundDaemonRunnable>>>>>>");
		}

		public void daemonStop() {
			bRun = false;
			if (msgAT != null) {
				msgAT.stop();
				msgAT.release();
				msgAT = null;
			}
		}

		public void daemonStart() {
			bRun = true;
		}

		public void startCmd() {
			
			// 创建AudioTrack
			msgAT = new AudioTrack(
					AudioManager.STREAM_MUSIC,// 音乐声
					audioSampleRate,
					AudioFormat.CHANNEL_OUT_STEREO,// 双声道
					AudioFormat.ENCODING_PCM_8BIT,
					msgMinBufferSize,
					AudioTrack.MODE_STREAM);

			msgIsSending = true;


			msgAT.setStereoVolume(1.0f, 1.0f);// 设置当前音量大小
			msgAT.play();
			int j = 1;
			while (j > 0 && bRun == true) {
				j--;
				Log.i("Msg", "msgMinBufferSize=" + msgMinBufferSize + " j=" + j);
			// msgAT.write(audio_data_byte, 0, audio_data_byte.length);

			//	msgAT.write(rc5Codec.encode(decode_data,decode_mode), 0, msgMinBufferSize);
				msgAT.write(miniCodec.miniEncode(miniCodecType,decode_data,decode_mode), 0, miniCodec.getDataSize(miniCodecType));
				msgAT.flush();
			}

		}

		public void stopCmd() {
			Log.i("Msg", "stopCmd");
			if (msgAT != null) {
				msgAT.stop();
				msgAT.release();
				msgAT = null;
			}
		}

	}
}
