import java.io.UnsupportedEncodingException;


public class temp{
	public static void main(String[] args) throws UnsupportedEncodingException{
		String binary = "";
		String hexResult = ""; 
		String biResult = "";
		String result = "";
		String textResult = "";
		String string = "测试test\nthis is a test!\n\tmother fuckers!\n23324t2gggg222222r322f2fdfdfsPREPARE URANUS!!!";
		byte[] input = string.getBytes("UTF-16");
		String[] output = new String[input.length-2];
		
		System.out.println(string);
		//text to unicode formated hex string
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
			hexResult = hexResult.concat(newChar);
		}
		
		System.out.println(hexResult);
		//hex to binary
		for(int i =0; i<hexResult.length(); i++){
			String bin =  HexToBinary(hexResult.substring(i,i+1));
			while(bin.length()<4){
				bin ="0"+bin;
			}
			binary = binary.concat(bin);
		}
		
		System.out.println(binary);
		//binary back to hex
		for(int i =0; i<binary.length(); i+=4){
			String bin =  BinaryToHex(binary.substring(i,i+4));
			biResult = biResult.concat(bin);
		}
		
		System.out.println(biResult);
		//hex to Unicode format
		for(int i=0; i<biResult.length(); i+=4){
			result = result+"\\u"+biResult.substring(i, i+4);
		}
		
		System.out.println(result);
		String finalResult = unicode2String(result);
		System.out.println(finalResult);
		String digit = XOR("1011","1110");
		System.out.println(digit.length());
		System.out.println(digit);
	}
	
	//http://www.jb51.net/article/56096.htm
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
	
	static String BinaryToHex(String s){
		return Long.toHexString(Long.parseLong(s,2));
		}
	
	static String HexToBinary(String s){
		return Long.toBinaryString(Long.parseLong(s, 16));
		}
	
	static String XOR(String msg, String key){
		if(msg.length()>key.length()){
			throw new IndexOutOfBoundsException("Message length "+msg.length()+
					" is longer than the key length "+key.length());
		}
		StringBuilder sb = new StringBuilder();
		int length = msg.length();
		//generate all for all pixel
		final int THREAD_LIMIT = 500;
		int take = 5000;
		int threadNum = length/take;
		int remainderTake = length%THREAD_LIMIT;
		if(threadNum>THREAD_LIMIT){
			threadNum = THREAD_LIMIT;
			take = length/THREAD_LIMIT;
		}
		System.out.println("Take: "+take+" Threads: "+threadNum+" RemainderTake:"+remainderTake);
		WordProcess[] pool = new WordProcess[threadNum];
		String[] result = new String[threadNum];
		WordProcess remainder = null;
		for(int i=0; i<threadNum; i++){
			WordProcess temp = new WordProcess(i,take);
			temp.setKey(key.substring(i, i+take));
			temp.setMsg(msg.substring(i, i+take));
			temp.pool = result;
			temp.setState(WordProcess.XOR);			
			temp.run();
		}
		//handeling the remaining stuff
		if(length%THREAD_LIMIT!=0){
		remainder = new WordProcess(length%THREAD_LIMIT);
		remainder.setKey(key.substring(threadNum*take));
		remainder.setMsg(msg.substring(threadNum*take));
		remainder.setState(WordProcess.XOR);
		remainder.run();
		}
		for(int i = 0; i<result.length; i++){
			if(result[i]!=null)
			sb = sb.append(result[i]);
//			System.out.println(result[i].substring(0,100));
		}
		if(length%THREAD_LIMIT!=0)
			if(remainder.getMsg()!=null)
				sb.append(remainder.getResult());
		return sb.toString();
	}
	
	
	//returns a bitString key
	static String pixelGen(int length){
		StringBuilder key = new StringBuilder();
		//generate all for all pixel
		final int THREAD_LIMIT = 1000;
		int take = 80;
		int threadNum = length/take;
		int remainderTake = length%THREAD_LIMIT;
		if(threadNum>THREAD_LIMIT){
			threadNum = THREAD_LIMIT;
			take = length/THREAD_LIMIT;
		}
		System.out.println("Take: "+take+" Threads: "+threadNum+" RemainderTake:"+remainderTake);
		WordProcess[] pool = new WordProcess[threadNum];
		String[] result = new String[threadNum];
		WordProcess remainder = null;
		for(int i=0; i<threadNum; i++){
			WordProcess temp = new WordProcess(i,take);
			temp.pool = result;
			temp.setState(WordProcess.KEYGEN);			
			temp.run();
		}
		//handeling the remaining stuff
		if(length%THREAD_LIMIT!=0){
		remainder = new WordProcess(length%THREAD_LIMIT);
		remainder.setState(WordProcess.KEYGEN);
		remainder.run();
		}
		for(int i = 0; i<result.length; i++){
			if(result[i]!=null)
			key = key.append(result[i]);
//			System.out.println(result[i].substring(0,100));
		}
		if(length%THREAD_LIMIT!=0)
		key.append(remainder.getKey());
		
		return key.toString();
	}

}
