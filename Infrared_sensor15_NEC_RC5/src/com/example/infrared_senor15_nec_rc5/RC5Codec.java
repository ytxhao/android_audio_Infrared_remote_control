package com.example.infrared_senor15_nec_rc5;

public class RC5Codec extends Icodec{

	public byte[] RC5data = null;
	private int date_size;
//	int m_value = 140;
//	int h_value = 255;
//	int l_value = 0;
	int date_len = 19 * 2 * 2;

	byte[] left_data0 = new byte[date_len];
	byte[] left_data1 = new byte[date_len];

	byte[] right_data0 = new byte[date_len];
	byte[] right_data1 = new byte[date_len];

	public int getDate_size() {
		return date_size;
	}

	public void setDate_size(int date_size) {
		this.date_size = date_size;
	}

	public RC5Codec(int data_size) {
		//this.date_size = data_size;
		setDate_size(data_size);
		RC5data = new byte[this.date_size];
		dataInit();

	}

	public byte[] encode(int org_data, int mode) {
		int tmp;
		int remainder;
		int[] data_stack = new int[14];
		int[] data_stack_tmp = new int[16];
		if (mode == 0) {
			data_stack[0] = 1;
			data_stack[1] = 1;
			data_stack[2] = 0;
		} else if (mode == 1) {
			data_stack[0] = 1;
			data_stack[1] = 0;
			data_stack[2] = 1;
		}

		tmp = org_data;

		for (int i = data_stack_tmp.length - 1; i > 0; i--) {
			remainder = tmp % 2;
			tmp = tmp / 2;
			data_stack_tmp[i] = remainder;
		}

		for (int i = 3; i < data_stack.length; i++) {
			if (i < 8) {
				data_stack[i] = data_stack_tmp[i];
			} else {
				data_stack[i] = data_stack_tmp[i + 2];
			}
		}

		int position = 300;
		for (int i = 0; i < 14; i++) {
			position = position + left_data0.length * 2;
			if (data_stack[i] == 0) {
				cpy_data(position, left_data0.length, RC5data, left_data0);
				cpy_data(position + 1, right_data0.length, RC5data, right_data0);
			} else {
				cpy_data(position, left_data1.length, RC5data, left_data1);
				cpy_data(position + 1, right_data1.length, RC5data, right_data1);
			}
		}

		return RC5data;

	}

	public void dataInit() {
		for (int i = 0; i < date_len; i = i + 2) {

			if (i < date_len / 2) {
				left_data0[i] = (byte) h_value;
				left_data0[i + 1] = (byte) l_value;

				left_data1[i] = (byte) m_value;
				left_data1[i + 1] = (byte) m_value;
			} else {
				left_data0[i] = (byte) m_value;
				left_data0[i + 1] = (byte) m_value;

				left_data1[i] = (byte) h_value;
				left_data1[i + 1] = (byte) l_value;
			}

		}

		for (int i = 0; i < date_len; i = i + 2) {

			if (i < date_len / 2) {
				right_data0[i] = (byte) l_value;
				right_data0[i + 1] = (byte) h_value;

				right_data1[i] = (byte) m_value;
				right_data1[i + 1] = (byte) m_value;
			} else {
				right_data0[i] = (byte) m_value;
				right_data0[i + 1] = (byte) m_value;

				right_data1[i] = (byte) l_value;
				right_data1[i + 1] = (byte) h_value;
			}
		}

		for (int i = 0; i < RC5data.length - 4; i = i + 4) {

			if (i % 400 < 200) {
				RC5data[i] = (byte) m_value;
				RC5data[i + 1] = (byte) m_value;

				RC5data[i + 2] = (byte) m_value;
				RC5data[i + 3] = (byte) m_value;
			} else {
				RC5data[i] = (byte) (m_value + 20);
				RC5data[i + 1] = (byte) (m_value - 20);

				RC5data[i + 2] = (byte) (m_value - 20);
				RC5data[i + 3] = (byte) (m_value + 20);
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
