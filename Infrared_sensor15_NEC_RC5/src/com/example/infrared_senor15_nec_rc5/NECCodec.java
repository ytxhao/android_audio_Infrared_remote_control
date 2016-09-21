package com.example.infrared_senor15_nec_rc5;

public class NECCodec extends Icodec{

	public byte[] necData = null;
	private int date_size;
	private int date_size_tmp;
//	int m_value = 140;
//	int h_value = 255;
//	int l_value = 0;

	final int necMsgMinBufferSize=7524;
	
	int necData0_size=13*2*2;//13个正弦波，每个正弦波用2个点，高低电平个一半
	int necData1_size=13*2*2*2;
	int necDataHeader_size=297*2;//前198*2个点是正弦波，以后的点为0；
	
	byte [] left_header_data_nec=new byte[necDataHeader_size];
	byte [] right_header_data_nec=new byte[necDataHeader_size];
	
	byte [] left_data0_nec=new byte[necData0_size];
	byte [] left_data1_nec=new byte[necData1_size];
	
	byte [] right_data0_nec=new byte[necData0_size];
	byte [] right_data1_nec=new byte[necData1_size];
	
	byte [] left_end_data0_nec=new byte[necData0_size];
	byte [] right_end_data0_nec=new byte[necData0_size];

	public int getDate_size() {
		return this.date_size;
	}

	public void setDate_size(int date_size) {
		this.date_size = date_size;
	}

	public NECCodec(int data_size) {
		
		date_size_tmp=data_size;
		while(date_size_tmp < necMsgMinBufferSize){
			date_size_tmp=2*date_size_tmp;
		}
		setDate_size(date_size_tmp);
	//	this.date_size = date_size_tmp;
		necData = new byte[this.date_size];
		dataInit();

	}
	
	public byte[] encode(int org_data, int mode) {
		int tmp;
		int remainder;
		int[] data_stack_nec = new int[32];
		int[] data_stack_tmp_nec = new int[32];
		tmp=org_data;
		
		for(int i=0;i<32;i++){
			data_stack_nec[i]=0;
			data_stack_tmp_nec[i]=0;
		}
		
		for(int i=data_stack_tmp_nec.length-1;i>0;i--){
			remainder=tmp%2;
			tmp=tmp/2;
			data_stack_tmp_nec[i]=remainder;
		}
		
		
		int j=0;
		for(int i=0;i<data_stack_nec.length;i++){

			j = i % 8;
			if(i<8){
				data_stack_nec[i]=data_stack_tmp_nec[7-j];
			}else if(i<16){
				data_stack_nec[i]=data_stack_tmp_nec[15-j];
			}else if(i<24){
				data_stack_nec[i]=data_stack_tmp_nec[23-j];
			}else if(i<32){
				data_stack_nec[i]=data_stack_tmp_nec[31-j];
			}
		}

		int position = 800;

		//copy header data
		//copy 左声道 右声道
		cpy_data(position, necDataHeader_size, necData, left_header_data_nec);
		cpy_data(position+1, necDataHeader_size, necData, right_header_data_nec);
		position=position+necDataHeader_size*2;
		for(int i=0;i<32;i++){
			if(data_stack_nec[i]==0){
				cpy_data(position, necData0_size, necData, left_data0_nec);
				cpy_data(position+1, necData0_size, necData, right_data0_nec);
				position=position+necData0_size*2;
			}else{
				cpy_data(position, necData1_size, necData, left_data1_nec);
				cpy_data(position+1, necData1_size, necData, right_data1_nec);
				position=position+necData1_size*2;
				
			}
		}
		//copy end data
		cpy_data(position, necData0_size, necData, left_end_data0_nec);
		cpy_data(position+1, necData0_size, necData, right_end_data0_nec);
		position=position+necData0_size*2;
		
		
		return necData;
	}
	
	public void dataInit() {
		
		
		for(int i=0;i<necData0_size;i=i+2){
			if(i<necData0_size/2){
				left_end_data0_nec[i] = (byte) h_value;
				left_end_data0_nec[i + 1] = (byte) l_value;
			}else{
				left_end_data0_nec[i] = (byte) m_value;
				left_end_data0_nec[i + 1] = (byte) m_value;
			}
			
		}
		
		for(int i=0;i<necData0_size;i=i+2){
			if(i<necData0_size/2){
				right_end_data0_nec[i] = (byte) l_value;
				right_end_data0_nec[i + 1] = (byte) h_value;
			}else{
				right_end_data0_nec[i] = (byte) m_value;
				right_end_data0_nec[i + 1] = (byte) m_value;
			}
			
		}
		
		for(int i=0;i<necData0_size;i=i+2){
			if(i<necData0_size/2){
				left_data0_nec[i] = (byte) h_value;
				left_data0_nec[i + 1] = (byte) l_value;
			}else{
				left_data0_nec[i] = (byte) m_value;
				left_data0_nec[i + 1] = (byte) m_value;
			}
			
		}
		
		for(int i=0;i<necData0_size;i=i+2){
			if(i<necData0_size/2){
				right_data0_nec[i] = (byte) l_value;
				right_data0_nec[i + 1] = (byte) h_value;
			}else{
				right_data0_nec[i] = (byte) m_value;
				right_data0_nec[i + 1] = (byte) m_value;
			}
			
		}
		
		for(int i=0;i<necData1_size;i=i+2){
			
			if(i<13*2){
				left_data1_nec[i] = (byte) h_value;
				left_data1_nec[i + 1] = (byte) l_value;
			}else{
				left_data1_nec[i] = (byte) m_value;
				left_data1_nec[i + 1] = (byte) m_value;
			}
		}
		
		for(int i=0;i<necData1_size;i=i+2){
			
			if(i<13*2){
				right_data1_nec[i] = (byte) l_value;
				right_data1_nec[i + 1] = (byte) h_value;
			}else{
				right_data1_nec[i] = (byte) m_value;
				right_data1_nec[i + 1] = (byte) m_value;
			}
		}
		
		for(int i=0;i<necDataHeader_size;i=i+2){
			if(i<198*2){
				left_header_data_nec[i]=(byte) h_value;
				left_header_data_nec[i+1]=(byte) l_value;
			}else{
				left_header_data_nec[i]=(byte) m_value;
				left_header_data_nec[i+1]=(byte) m_value;
			}
		}
		
		for(int i=0;i<necDataHeader_size;i=i+2){
			if(i<198*2){
				right_header_data_nec[i]=(byte) l_value;
				right_header_data_nec[i+1]=(byte) h_value;
			}else{
				right_header_data_nec[i]=(byte) m_value;
				right_header_data_nec[i+1]=(byte) m_value;
			}
		}
		
		
		for (int i = 0; i < necData.length - 4; i = i + 4) {

			if (i % 400 < 200) {
				necData[i] = (byte) m_value;
				necData[i + 1] = (byte) m_value;

				necData[i + 2] = (byte) m_value;
				necData[i + 3] = (byte) m_value;
			} else {
				necData[i] = (byte) (m_value + 20);
				necData[i + 1] = (byte) (m_value - 20);

				necData[i + 2] = (byte) (m_value - 20);
				necData[i + 3] = (byte) (m_value + 20);
			}
		}
	}
	
	private void cpy_data(int pos, int len, byte[] des, byte[] src) {

		int j = 0;
		for (int i = 0; i < len; i++) {
			j = 2 * i + pos;
			des[j] = src[i];
		}
	}
}
