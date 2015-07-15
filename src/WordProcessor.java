import java.io.UnsupportedEncodingException;


public class WordProcessor{
	
	//returns a random sequence of 1 and 0 with length*24
	static String keyGen(int length){
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
		String[] result = new String[threadNum];
		ThreadWordProcessor remainder = null;
		for(int i=0; i<threadNum; i++){
			ThreadWordProcessor temp = new ThreadWordProcessor(i,take);
			temp.pool = result;
			temp.setState(ThreadWordProcessor.KEYGEN);			
			temp.run();
		}
		//handeling the remaining stuff
		if(length%THREAD_LIMIT!=0){
		remainder = new ThreadWordProcessor(length%THREAD_LIMIT);
		remainder.setState(ThreadWordProcessor.KEYGEN);
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
	
	public static String XOR(String msg, String key){
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
		String[] result = new String[threadNum];
		ThreadWordProcessor remainder = null;
		for(int i=0; i<threadNum; i++){
			ThreadWordProcessor temp = new ThreadWordProcessor(i,take);
			temp.setKey(key.substring(i, i+take));
			temp.setMsg(msg.substring(i, i+take));
			temp.pool = result;
			temp.setState(ThreadWordProcessor.XOR);			
			temp.run();
		}
		//handeling the remaining stuff
		if(length%THREAD_LIMIT!=0){
		remainder = new ThreadWordProcessor(length%THREAD_LIMIT);
		remainder.setKey(key.substring(threadNum*take));
		remainder.setMsg(msg.substring(threadNum*take));
		remainder.setState(ThreadWordProcessor.XOR);
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
	
	//normal text to binary string
	public static String StringToBit(String str){
		StringBuilder sb = new StringBuilder();
		int length = str.length();
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
		String[] result = new String[threadNum];
		ThreadWordProcessor remainder = null;
		for(int i=0; i<threadNum; i++){
			ThreadWordProcessor temp = new ThreadWordProcessor(i,take);
			temp.setText(str.substring(i, i+take));
			temp.pool = result;
			temp.setState(ThreadWordProcessor.StrToBit);			
			temp.run();
		}
		//handeling the remaining stuff
		if(length%THREAD_LIMIT!=0){
		remainder = new ThreadWordProcessor(length%THREAD_LIMIT);
		remainder.setText(str.substring(threadNum*take));
		remainder.setState(ThreadWordProcessor.StrToBit);
		remainder.run();
		}
		for(int i = 0; i<result.length; i++){
			if(result[i]!=null)
			sb = sb.append(result[i]);
//			System.out.println(result[i].substring(0,100));
		}
		if(length%THREAD_LIMIT!=0)
			if(remainder.binary!=null)
				sb.append(remainder.getBinary());
		return sb.toString();
	}
	
	//remove the extra 0s at the end of the binary string
	public static String trimBinaryTail(String str){
		int index = str.lastIndexOf("0000000000000000");
		while(index != -1){
			str = str.substring(0, index);
			index = str.lastIndexOf("0000000000000000");
		}
		return str;
	}
	
	//binary string to normal text
	public static String BitToString(String str){
		str = trimBinaryTail(str);
		StringBuilder sb = new StringBuilder();
		int length = str.length();
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
		String[] result = new String[threadNum];
		ThreadWordProcessor remainder = null;
		for(int i=0; i<threadNum; i++){
			ThreadWordProcessor temp = new ThreadWordProcessor(i,take);
			temp.setBinary(str.substring(i, i+take));
			temp.pool = result;
			temp.setState(ThreadWordProcessor.BitToStr);			
			temp.run();
		}
		//handeling the remaining stuff
		if(length%THREAD_LIMIT!=0){
		remainder = new ThreadWordProcessor(length%THREAD_LIMIT);
		remainder.setBinary(str.substring(threadNum*take));
		remainder.setState(ThreadWordProcessor.BitToStr);
		remainder.run();
		}
		for(int i = 0; i<result.length; i++){
			if(result[i]!=null)
			sb = sb.append(result[i]);
//			System.out.println(result[i].substring(0,100));
		}
		if(length%THREAD_LIMIT!=0)
			if(remainder.text!=null)
				sb.append(remainder.getText());
		return sb.toString();
	}
	
	//binary string to hex
	public static String BitToHex(String str){
		StringBuilder sb = new StringBuilder();
		int length = str.length();
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
		String[] result = new String[threadNum];
		ThreadWordProcessor remainder = null;
		for(int i=0; i<threadNum; i++){
			ThreadWordProcessor temp = new ThreadWordProcessor(i,take);
			temp.setBinary(str.substring(i, i+take));
			temp.pool = result;
			temp.setState(ThreadWordProcessor.BitToHex);			
			temp.run();
		}
		//handeling the remaining stuff
		if(length%THREAD_LIMIT!=0){
		remainder = new ThreadWordProcessor(length%THREAD_LIMIT);
		remainder.setBinary(str.substring(threadNum*take));
		remainder.setState(ThreadWordProcessor.BitToHex);
		remainder.run();
		}
		for(int i = 0; i<result.length; i++){
			if(result[i]!=null)
			sb = sb.append(result[i]);
//			System.out.println(result[i].substring(0,100));
		}
		if(length%THREAD_LIMIT!=0)
			if(remainder.hex!=null)
				sb.append(remainder.getHex());
		return sb.toString();
	}
	
	//hex string to binary
	public static String HexToBit(String str){
		StringBuilder sb = new StringBuilder();
		int length = str.length();
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
		String[] result = new String[threadNum];
		ThreadWordProcessor remainder = null;
		for(int i=0; i<threadNum; i++){
			ThreadWordProcessor temp = new ThreadWordProcessor(i,take);
			temp.setBinary(str.substring(i, i+take));
			temp.pool = result;
			temp.setState(ThreadWordProcessor.HexToBit);			
			temp.run();
		}
		//handeling the remaining stuff
		if(length%THREAD_LIMIT!=0){
		remainder = new ThreadWordProcessor(length%THREAD_LIMIT);
		remainder.setHex(str.substring(threadNum*take));
		remainder.setState(ThreadWordProcessor.HexToBit);
		remainder.run();
		}
		for(int i = 0; i<result.length; i++){
			if(result[i]!=null)
			sb = sb.append(result[i]);
//			System.out.println(result[i].substring(0,100));
		}
		if(length%THREAD_LIMIT!=0)
			if(remainder.binary!=null)
				sb.append(remainder.getBinary());
		return sb.toString();
	}
}
