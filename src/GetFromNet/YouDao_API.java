package GetFromNet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouDao_API {
	   public static String run(String word) { 
		   String meaning = new String();
	        try {


	            URL url = new URL("http://fanyi.youdao.com/openapi.do");
	            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	            connection.addRequestProperty("encoding", "UTF-8");
	            connection.setDoInput(true);
	            connection.setDoOutput(true);

	            connection.setRequestMethod("POST");

	            OutputStream os = connection.getOutputStream();
	            OutputStreamWriter osw = new OutputStreamWriter(os);
	            BufferedWriter bw = new BufferedWriter(osw);


	            bw.write("keyfrom=fadabvaa&key=522071532&type=data&doctype=json&version=1.1&q="+word);
	            bw.flush();

	            InputStream is = connection.getInputStream();
	            InputStreamReader isr = new InputStreamReader(is,"UTF-8");
	            BufferedReader br = new BufferedReader(isr);

	            String line;
	            StringBuilder builder = new StringBuilder();
	            while((line = br.readLine()) != null){
	                builder.append(line);
	            }

	            bw.close();
	            osw.close();
	            os.close();

	            br.close();
	            isr.close();
	            is.close();

	            //meaning = builder.toString();
	            String str1 = builder.toString();
	            Pattern pattern = Pattern.compile("\"explains\":(.+?)\"query\"");
	            Matcher matcher = pattern.matcher(str1);
	            if(matcher.find()){
	            	String x = matcher.group();
	            	String[] arg = x.split("\\[");
	            	String[] arg1 = arg[1].split("\\]");
	            	meaning = arg1[0];
	            }
	            else{
	            	meaning = "未找到释义";
	            }
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	       return meaning;
	}
	   
	public static void main(String[] args){
		System.out.print("请输入查询单词:");
		Scanner input = new Scanner(System.in);
		String word = input.nextLine();
		System.out.println(run(word));
	}
}
