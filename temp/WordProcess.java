	
	public class WordProcess implements Runnable{
		public String[] pool;
		String key = "";
		String msg = "";
		String result = "";

		public int state;
		public static final int KEYGEN = 1;
		public static final int XOR = 2;
		public static final int BitToStr = 3;
		public static final int StrToBit = 4;
		int index;
		int length;

		public WordProcess(int index, int take) {
			this.index  = index;
			this.length = take;
		}
		
		public WordProcess(int take) {
			this.length = take;
		}
		@Override
		public void run() {
			switch(state){
			case KEYGEN:
				keyGen();
				break;
			case XOR:
				XOR();
				break;
			case BitToStr:
				strToBit();
				break;
			case StrToBit:
				break;
			}
		}
		
		public String getKey(){

				return key;
		}
		
		public String getMsg() {
			return msg;
		}
		
		public String getResult() {
			return result;
		}
		
		public void setLength(int length){
			this.length = length;
		}
		
		public void setMsg(String msg){
			this.msg = msg;
		}
		
		public void setKey(String key){
			this.key = key;
		}
		
		public void setState(int state){
			this.state = state;
		}

		public void keyGen(){
			StringBuilder keyBuilder = new StringBuilder();
			for(int i =0; i<length; i++){
			int pixel = (int)(Math.random()*16777216);
			String pix = Integer.toBinaryString(pixel);
			while(pix.length()<24)
				pix = "0"+pix;
			keyBuilder.append(pix);
			}
			this.key = keyBuilder.toString();
			if(pool!=null)
			pool[index] = key;
		}

		public void XOR(){
			StringBuilder sb = new StringBuilder();
			if(msg.length()>key.length()){
				throw new IndexOutOfBoundsException("Message length "+msg.length()+
						" is longer than the key length "+key.length());
			}
			//XOR operation here
			for(int i = 0; i<msg.length(); i++){
				int a  = Integer.parseInt(msg.substring(i, i+1));
				int b  = Integer.parseInt(key.substring(i, i+1));
				String bit = (a^b)+"";
				sb.append(bit);
			}
			//fill in the blank
			if(msg.length()<key.length())
				sb.append(key.substring(msg.length()));
				this.result= sb.toString();
			if(pool!=null)
				pool[index] = result;
		}

		public void strToBit(){
			
		}
		
		public static String unicode2String(String unicode) {
		    StringBuffer string = new StringBuffer();
		    String[] hex = unicode.split("\\\\u");
		    for (int i = 1; i < hex.length; i++) {
		        // 转换出每一个代码点
		        int data = Integer.parseInt(hex[i], 16);
		        // 追加成string
		        string.append((char) data);
		    }
		    return string.toString();
		}
		
	}
