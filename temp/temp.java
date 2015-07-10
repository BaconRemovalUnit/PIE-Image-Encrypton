import java.io.UnsupportedEncodingException;


public class temp{
	public static void main(String[] args) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder("这是测试\n\tthis is a test");
		for(int i=0;i<50000;i++){
			sb.append("这是测试this is a test");
		}
		
		long start = System.nanoTime();
		String string = sb.toString();
		String a = StringToBit(string);
		long end = System.nanoTime();
		System.out.println((end-start)*1.0/1000000000);
		System.out.println(a.length());

	}
	
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
		WordProcess remainder = null;
		for(int i=0; i<threadNum; i++){
			WordProcess temp = new WordProcess(i,take);
			temp.setText(str.substring(i, i+take));
			temp.pool = result;
			temp.setState(WordProcess.StrToBit);			
			temp.run();
		}
		//handeling the remaining stuff
		if(length%THREAD_LIMIT!=0){
		remainder = new WordProcess(length%THREAD_LIMIT);
		remainder.setText(str.substring(threadNum*take));
		remainder.setState(WordProcess.StrToBit);
		remainder.run();
		}
		for(int i = 0; i<result.length; i++){
			if(result[i]!=null)
			sb = sb.append(result[i]);
//			System.out.println(result[i].substring(0,100));
		}
		if(length%THREAD_LIMIT!=0)
			if(remainder.getMsg()!=null)
				sb.append(remainder.getBinary());
		return sb.toString();
	}
	
	static String BitToString(String str){
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
		WordProcess remainder = null;
		for(int i=0; i<threadNum; i++){
			WordProcess temp = new WordProcess(i,take);
			temp.setBinary(str.substring(i, i+take));
			temp.pool = result;
			temp.setState(WordProcess.BitToStr);			
			temp.run();
		}
		//handeling the remaining stuff
		if(length%THREAD_LIMIT!=0){
		remainder = new WordProcess(length%THREAD_LIMIT);
		remainder.setBinary(str.substring(threadNum*take));
		remainder.setState(WordProcess.BitToStr);
		remainder.run();
		}
		for(int i = 0; i<result.length; i++){
			if(result[i]!=null)
			sb = sb.append(result[i]);
//			System.out.println(result[i].substring(0,100));
		}
		if(length%THREAD_LIMIT!=0)
			if(remainder.getMsg()!=null)
				sb.append(remainder.getBinary());
		return sb.toString();
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
	
	//returns a random sequence of 1 and 0 with length*24


}
