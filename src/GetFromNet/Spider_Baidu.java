package GetFromNet;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Spider_Baidu{
	 public static String GetMeaning(String word) throws IOException {
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        //根据查找单词构造查找地址
	        HttpGet getWordMean = new HttpGet("http://dict.baidu.com/s?wd="+word+"&device=pc&from=home&q="+word);
	        CloseableHttpResponse response = httpClient.execute(getWordMean);//取得返回的网页源码

	        String result = EntityUtils.toString(response.getEntity());
	        response.close();
	        
	        //注意(?s)，意思是让'.'匹配换行符，默认情况下不匹配
	        Pattern searchMeanPattern = Pattern.compile("<div><p><strong>(.+?)</strong><span>(.+?)</span></p></div>");
	        Matcher m1 = searchMeanPattern.matcher(result);
	        StringBuffer meaning = new StringBuffer();
	        if (m1.find()) {
	            String means1 = m1.group(1);//所有解释，包含网页标签
	            meaning.append(means1);
	            String means2 = m1.group(2);
	            String[] means3 = means2.split("</span></p><p><strong>");
	            for(int i=0;i<means3.length;i++){
	            	String[] x = means3[i].split("</strong><span>");
	            	for(int j=0;j<x.length;j++)
	            		meaning.append(x[j]);
	            }
	        } else {
	            meaning.append("未找到释义");
	        }
	        return meaning.toString();
	 }
	 
	 public static void main(String[] args)throws IOException{
		 System.out.print("请输入查询单词:");
		 Scanner input = new Scanner(System.in); 
		 String word = input.nextLine();
		 System.out.println(GetMeaning(word));	 
	 }
}
