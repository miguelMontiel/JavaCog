import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Post 
{
	public static void main(String[] args) throws Exception
	{
		Post http = new Post();
		
		System.out.println("Testing - POST");
		
		TrustManager[] trustAllCerts = new TrustManager[] 
		{ 
			new X509TrustManager() 
			{
				public java.security.cert.X509Certificate[] getAcceptedIssuers()
				{
					return null;
				}
				public void checkClientTrusted(X509Certificate[] certs, String authType){}
				public void checkServerTrusted(X509Certificate[] certs, String authType){}
			}
		};
		
		// All trusting manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		
		// All trusting host name
		HostnameVerifier allHostsValid = new HostnameVerifier() 
		{
			public boolean verify(String hostname, SSLSession session)
			{
				return true;
			}
		};
		
		// Install all trusting
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		
		http.sendPost();
	}
	
	private void sendPost() throws Exception
	{
		/*
		String url = "https://cognitiveassistant.in.edst.ibm.com:443/apiserver/api/v1/postQuestion";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Pregunta a subir: ");
		String pregunta = scanner.nextLine();
		System.out.println(pregunta);
		scanner.close();
		*/
		
		URL url = new URL("https://cognitiveassistant.in.edst.ibm.com:443/apiserver/api/v1/postQuestion");
		URLConnection con = url.openConnection();
		
		//con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		Map<String, String> urlParameters = new HashMap<>();
		urlParameters.put("apiToken", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.IjRlN2ZjZTgzLThlN2EtNGYxYy1hYjk0LTE3N2E2YWIzODE4NCI.7I28yN8OOhv_6kQYNZ62BnqWuWGBhkxZAMFj81oMUOOAr35k3PYJLapC5-SJQ5rbfP591k_I9TG_5AM3TeMJbQ");
		urlParameters.put("semanticQuestion", "Java Httprequest prueba14");
		urlParameters.put("projectid", "2215");
		urlParameters.put("userid", "Dolores.Arevalo@ibm.com");
		urlParameters.put("source", "AgentAssist");
		urlParameters.put("curatedLang", "es");
		urlParameters.put("username", "Dolores.Arevalo@ibm.com");
		urlParameters.put("error_code", "");
		
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		
		wr.writeBytes(ParameterStringBuilder.getParamsString(urlParameters));
		wr.flush();
		wr.close();
		
		//int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL: " + url);
		System.out.println("Post parameters: " + urlParameters);
		//System.out.println("Response code: " + responseCode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine())!= null)
		{
			response.append(inputLine);
		}
		in.close();
		
		System.out.println(response.toString());
	}
}