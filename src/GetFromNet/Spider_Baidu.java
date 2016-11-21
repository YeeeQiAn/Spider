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
	        //���ݲ��ҵ��ʹ�����ҵ�ַ
	        HttpGet getWordMean = new HttpGet("http://dict.baidu.com/s?wd="+word+"&device=pc&from=home&q="+word);
	        CloseableHttpResponse response = httpClient.execute(getWordMean);//ȡ�÷��ص���ҳԴ��

	        String result = EntityUtils.toString(response.getEntity());
	        response.close();
	        
	        //ע��(?s)����˼����'.'ƥ�任�з���Ĭ������²�ƥ��
	        Pattern searchMeanPattern = Pattern.compile("<div><p><strong>(.+?)</strong><span>(.+?)</span></p></div>");
	        Matcher m1 = searchMeanPattern.matcher(result);
	        StringBuffer meaning = new StringBuffer();
	        if (m1.find()) {
	            String means1 = m1.group(1);//���н��ͣ�������ҳ��ǩ
	            meaning.append(means1);
	            String means2 = m1.group(2);
	            String[] means3 = means2.split("</span></p><p><strong>");
	            for(int i=0;i<means3.length;i++){
	            	String[] x = means3[i].split("</strong><span>");
	            	for(int j=0;j<x.length;j++)
	            		meaning.append(x[j]);
	            }
	        } else {
	            meaning.append("δ�ҵ�����");
	        }
	        return meaning.toString();
	 }
	 
	 public static void main(String[] args)throws IOException{
		 System.out.print("�������ѯ����:");
		 Scanner input = new Scanner(System.in); 
		 String word = input.nextLine();
		 System.out.println(GetMeaning(word));	 
	 }
}
