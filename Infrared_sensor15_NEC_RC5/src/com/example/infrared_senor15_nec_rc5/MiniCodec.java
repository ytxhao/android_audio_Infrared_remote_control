package com.example.infrared_senor15_nec_rc5;

public class MiniCodec{

	NECCodec necCode=null;
	RC5Codec rc5Code=null;
	int codecNum=2;
	Icodec [] icodec= new Icodec[codecNum];
	byte[] byteData=null;
	int dataSize=0;
	
	MiniCodec(int dataSize ){
		necCode=new NECCodec(dataSize);
		rc5Code=new RC5Codec(dataSize);
		icodec[MinicodecType.typeRC5]=rc5Code;

		icodec[MinicodecType.typeNEC]=necCode;

	}

	public byte[] miniEncode(int miniCodecType, int org_data, int mode) {
		// TODO Auto-generated method stub
		if (miniCodecType >= 0 && miniCodecType < codecNum) {
			byteData = icodec[miniCodecType].encode(org_data, mode);
		}

//		if(miniCodecType==MinicodecType.typeRC5){
//		//	byteData=rc5Code.encode(org_data, mode);
//			byteData = icodec[miniCodecType].encode(org_data, mode);
//		}else{
//		//	byteData=necCode.encode(org_data, mode);
//			byteData = icodec[miniCodecType].encode(org_data, mode);
//		}
		return byteData;
	}

	public int getDataSize(int miniCodecType){
		
		if (miniCodecType >= 0 && miniCodecType < codecNum) {
			dataSize = icodec[miniCodecType].getDate_size();
		}
//		
//		if(miniCodecType==MinicodecType.typeRC5){
//		//	dataSize=rc5Code.getDate_size();
//			dataSize = icodec[miniCodecType].getDate_size();
//		}else{
//		//	dataSize=necCode.getDate_size();
//			dataSize = icodec[miniCodecType].getDate_size();
//		}
		return dataSize;
	}


}
