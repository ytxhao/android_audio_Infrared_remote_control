package com.example.infrared_senor15_nec_rc5;

public class MiniCmd {

	static int mode=0;
	public static int getCmdMode(int actionButtenValue){
		
		if(actionButtenValue<15)
		{
			mode=0;
		}else{
			mode=1;
		}
		
		return mode;
	}
	
	public static int getCmdKeyCode(int actionButtenValue,int codecType){
		int cmdKeyCode=0;
		if(codecType==MinicodecType.typeRC5){
			cmdKeyCode=rc5KeyCode(actionButtenValue);
		}else if(codecType==MinicodecType.typeNEC){
			cmdKeyCode=necKeyCode(actionButtenValue);
		}
		
		return cmdKeyCode;
	}
	
	public static int rc5KeyCode(int actionButtenValue){
		int keyCode=0;
		switch (actionButtenValue) {
		case ActionButten.num0:
			keyCode=RC5Cmd.num0;
			break;
		case ActionButten.num1:
			keyCode=RC5Cmd.num1;
			break;
		case ActionButten.num2:
			keyCode=RC5Cmd.num2;
			break;
		case ActionButten.num3:
			keyCode=RC5Cmd.num3;
			break;
		case ActionButten.num4:
			keyCode=RC5Cmd.num4;
			break;
		case ActionButten.num5:
			keyCode=RC5Cmd.num5;
			break;
		case ActionButten.num6:
			keyCode=RC5Cmd.num6;
			break;
		case ActionButten.num7:
			keyCode=RC5Cmd.num7;
			break;
		case ActionButten.num8:
			keyCode=RC5Cmd.num8;
			break;
		case ActionButten.num9:
			keyCode=RC5Cmd.num9;
			break;
			
		case ActionButten.vol_up:
			keyCode=RC5Cmd.vol_up;
			break;
		case ActionButten.vol_down:
			keyCode=RC5Cmd.vol_down;
			break;
		case ActionButten.ch_up:
			keyCode=RC5Cmd.ch_up;
			break;
		case ActionButten.ch_down:
			keyCode=RC5Cmd.ch_down;
			break;
		case ActionButten.shut_down:
			keyCode=RC5Cmd.shut_down;
			break;
		case ActionButten.ok:
			keyCode=RC5Cmd.ok;
			break;
		case ActionButten.dir_up:
			keyCode=RC5Cmd.dir_up;
			break;
		case ActionButten.dir_down:
			keyCode=RC5Cmd.dir_down;
			break;
		case ActionButten.dir_left:
			keyCode=RC5Cmd.dir_left;
			break;
		case ActionButten.dir_right:
			keyCode=RC5Cmd.dir_right;
			break;
		case ActionButten.menu:
			keyCode=RC5Cmd.menu;
			break;
		case ActionButten.exit:
			keyCode=RC5Cmd.exit;
			break;

		default:
			break;
		}
		
		return keyCode;
	}
	
	public static int necKeyCode(int actionButtenValue){
		int keyCode=0;
		switch (actionButtenValue) {
		case ActionButten.num0:
			keyCode=NECCmd.num0;
			break;
		case ActionButten.num1:
			keyCode=NECCmd.num1;
			break;
		case ActionButten.num2:
			keyCode=NECCmd.num2;
			break;
		case ActionButten.num3:
			keyCode=NECCmd.num3;
			break;
		case ActionButten.num4:
			keyCode=NECCmd.num4;
			break;
		case ActionButten.num5:
			keyCode=NECCmd.num5;
			break;
		case ActionButten.num6:
			keyCode=NECCmd.num6;
			break;
		case ActionButten.num7:
			keyCode=NECCmd.num7;
			break;
		case ActionButten.num8:
			keyCode=NECCmd.num8;
			break;
		case ActionButten.num9:
			keyCode=NECCmd.num9;
			break;
			
		case ActionButten.vol_up:
			keyCode=NECCmd.vol_up;
			break;
		case ActionButten.vol_down:
			keyCode=NECCmd.vol_down;
			break;
		case ActionButten.ch_up:
			keyCode=NECCmd.ch_up;
			break;
		case ActionButten.ch_down:
			keyCode=NECCmd.ch_down;
			break;
		case ActionButten.shut_down:
			keyCode=NECCmd.shut_down;
			break;
		case ActionButten.ok:
			keyCode=NECCmd.ok;
			break;
		case ActionButten.dir_up:
			keyCode=NECCmd.dir_up;
			break;
		case ActionButten.dir_down:
			keyCode=NECCmd.dir_down;
			break;
		case ActionButten.dir_left:
			keyCode=NECCmd.dir_left;
			break;
		case ActionButten.dir_right:
			keyCode=NECCmd.dir_right;
			break;
		case ActionButten.menu:
			keyCode=NECCmd.menu;
			break;
		case ActionButten.exit:
			keyCode=NECCmd.exit;
			break;

		default:
			break;
		}
		
		return keyCode;
	}
	
}
