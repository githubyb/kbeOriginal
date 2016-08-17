package edu.nwpu.dmpm.kbe.system.pageModel;

public class PassWordRule implements java.io.Serializable {


		private String min;
		private String max;
		private String needNum;
		private String needWord;
		private String needCharacter;
		
		
		public String getMin() {
			return min;
		}
		public void setMin(String min) {
			this.min = min;
		}
		public String getMax() {
			return max;
		}
		public void setMax(String max) {
			this.max = max;
		}
		public String getNeedNum() {
			return needNum;
		}
		public void setNeedNum(String needNum) {
			this.needNum = needNum;
		}
		public String getNeedWord() {
			return needWord;
		}
		public void setNeedWord(String needWord) {
			this.needWord = needWord;
		}
		public String getNeedCharacter() {
			return needCharacter;
		}
		public void setNeedCharacter(String needCharacter) {
			this.needCharacter = needCharacter;
		}
	
}