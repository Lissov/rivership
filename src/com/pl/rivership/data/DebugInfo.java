package com.pl.rivership.data;

public class DebugInfo
{
	public MaxData updateInterval = new MaxData();
	public MaxData modelCalcTime = new MaxData();
	
	public class MaxData{
		private static final int count = 40;
		private float[] data = new float[count];
		private int pointer = 0;
		private int cmax = 0;
		
		public void put(float value){
			data[pointer] = value;
			if (pointer == cmax || value > data[cmax])
				findMax();
			pointer++;
			if (pointer == count)
				pointer = 0;
		}
		public float get(){
			return data[cmax];
		}
		private void findMax(){
			cmax = 0;
			float m = data[0];
			for (int k = 0; k<count; k++){
				if (data[k] > m){
					m = data[k];
					cmax = k;
				}
			}
		}
	}	
}


