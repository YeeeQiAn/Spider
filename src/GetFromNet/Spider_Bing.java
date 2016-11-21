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

public class Spider_Bing {
	public static String GetMeaning(String word) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //根据查找单词构造查找地址
        HttpGet getWordMean = new HttpGet("http://cn.bing.com/dict/search?q=" + word + "&go=%E6%90%9C%E7%B4%A2&qs=n&form=Z9LH5&pq="+word);
        CloseableHttpResponse response = httpClient.execute(getWordMean);//取得返回的网页源码

        String result = EntityUtils.toString(response.getEntity());
        response.close();
        
        //注意(?s)，意思是让'.'匹配换行符，默认情况下不匹配
        Pattern searchMeanPattern = Pattern.compile("<meta name=\"description\" content=(.*?) />");
        Matcher m1 = searchMeanPattern.matcher(result);
        StringBuffer meaning = new StringBuffer();
        if (m1.find()) {
        	String means = m1.group();//所有解释，包含网页标签
            String[] m2=means.split("，");
            System.out.print("释义:");
            if(m2.length>=3) {
            	String[] p=m2[3].split("\"");
            	String[] m=p[0].split(" ");
            	for(int i=0;i*2<m.length;i++)
            		meaning.append(m[i*2]+" "+m[i*2+1]);
            		//System.out.println("\t"+m[i*2]+" "+m[i*2+1]);
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
