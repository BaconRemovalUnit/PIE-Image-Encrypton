import java.io.UnsupportedEncodingException;

	
	public class ThreadWordProcessor implements Runnable{

		public static final int KEYGEN = 1;
		public static final int XOR = 2;
		
		public static final int BitToStr = 3;
		public static final int StrToBit = 4;
		
		public static final int HexToBinary = 5;
		public static final int BinaryToHex = 6;
		
		public static final int StringToHex = 7;
		public static final int HexToString = 8;

		public int state;
		int length;
		int index;
		
		String binary = "";
		String hex = "";
		String text = "";
		String msg = "";
		String key = "";
		String result = "";
		public String[] pool;
		
		

		public ThreadWordProcessor(int take) {
			this.length = take;
		}
		
		public ThreadWordProcessor(int index, int take) {
			this.index  = index;
			this.length = take;
		}
		
		private String binaryToHex(String binary){
			StringBuilder hex = new StringBuilder();
			for(int i =0; i<binary.length(); i+=4){
				String bin =  ByteBinaryToHex(binary.substring(i,i+4));
				hex = hex.append(bin);
			}
			return hex.toString();
		}
		
		private void bitToStr() {
			String hex = binaryToHex(this.binary);
			String unicode = hexToUnicode(hex);
			String rev_text = Unicode2String(unicode);
			this.text = rev_text;
			if(pool!=null)
				pool[index] = text;
			
		}

		private String ByteBinaryToHex(String s){
			return Long.toHexString(Long.parseLong(s,2));
			}

		private String ByteHexToBinary(String s){
			return Long.toBinaryString(Long.parseLong(s, 16));
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
		
		private String hexToBinary(String hex){
			StringBuilder binary = new StringBuilder();
			for(int i =0; i<hex.length(); i++){
				String bin =  ByteHexToBinary(hex.substring(i,i+1));
				while(bin.length()<4){
					bin ="0"+bin;
				}
				binary = binary.append(bin);
			}
			return binary.toString();
		}
		
		private String hexToUnicode(String hexString){
			StringBuilder unicode = new StringBuilder();
			for(int i=0; i<hexString.length(); i+=4){
				unicode = unicode.append("\\u").append(hexString.substring(i, i+4));
			}
			return unicode.toString();
		}
		
		private void keyGen(){
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
					bitToStr();
				break;
			case StrToBit:
				try {
					strToBit();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case StringToHex:
				try {
					stringToHex();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case HexToString:
					hexToString();
				break;
			case BinaryToHex:
				break;
			case HexToBinary:
				break;
			}
		}

		private void hexToString() {
			String unicode = hexToUnicode(this.hex);
			String text = Unicode2String(unicode);
			this.text = text;
			if(pool!=null)
				pool[index] = text;
		}

		private void stringToHex() throws UnsupportedEncodingException {
			String unicodehex = String2Unicode(this.text);
			this.hex = unicodehex;
			if(pool!=null)
				pool[index] = hex;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getBinary() {
			return binary;
		}

		public void setBinary(String binary) {
			this.binary = binary;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public int getState() {
			return state;
		}

		public int getLength() {
			return length;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public void setKey(String key){
			this.key = key;
		}

		public void setLength(int length){
			this.length = length;
		}
		
		public void setMsg(String msg){
			this.msg = msg;
		}
		
		public void setState(int state){
			this.state = state;
		}
		
		private String String2Unicode(String str) throws UnsupportedEncodingException{
			byte[] input = str.getBytes("UTF-16");
			String[] output = new String[input.length-2];
			StringBuilder result = new StringBuilder();
			for(int i = 2; i<input.length;i++){
				String hex = Integer.toHexString(input[i]);
				//remove the fffff at the begining
				if(hex.length()>2)
					hex = hex.substring(6);
				// if hex is too small
				if(hex.length()<2)
					hex = "0"+hex;
				//since i started at 2 here
				output[i-2] = hex;
			}
			//just putting it together
			for(int i = 0; i<output.length;i+=2){
				String newChar = output[i]+output[i+1];
				result.append(newChar);
			}
			return result.toString();
		}
		
		private void strToBit() throws UnsupportedEncodingException {
			String hex = String2Unicode(text);
			String bin = hexToBinary(hex);
			this.binary = bin;
			if(pool!=null)
				pool[index] = binary;
		}
		
		private String Unicode2String(String unicode) {
		    StringBuffer string = new StringBuffer();
		    String[] hex = unicode.split("\\\\u");
		    for (int i = 1; i < hex.length; i++) {
		        int data = Integer.parseInt(hex[i], 16);
		        string.append((char) data);
		    }
		    return string.toString();
		}
		
		private void XOR(){
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
		
	}
