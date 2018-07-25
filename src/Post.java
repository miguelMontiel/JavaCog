import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class Post 
{	
	public Map<String, String> searchQuestion(String pregunta) throws Exception
	{	
		URL url = new URL("https://cognitiveassistant.in.edst.ibm.com/apiserver/api/v1/ask/direct/custom");
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		Map<String, String> urlParameters = new HashMap<>();
		urlParameters.put("apiToken", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.IjRlN2ZjZTgzLThlN2EtNGYxYy1hYjk0LTE3N2E2YWIzODE4NCI.7I28yN8OOhv_6kQYNZ62BnqWuWGBhkxZAMFj81oMUOOAr35k3PYJLapC5-SJQ5rbfP591k_I9TG_5AM3TeMJbQ");
		urlParameters.put("language", "es");
		urlParameters.put("question", pregunta);
		urlParameters.put("projectId", "2215");
		
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		
		wr.writeBytes(ParameterStringBuilder.getParamsString(urlParameters));
		wr.flush();
		wr.close();
		
		System.out.println("\nSending 'POST' request to URL: " + url);
		System.out.println("Post parameters: " + urlParameters);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine())!= null)
		{
			response.append(inputLine);
		}
		in.close();
		
		JSONObject jsonobject = new JSONObject(response.toString()); 
		
		JSONArray jsonarray = jsonobject.getJSONArray("answers");
		String question = null;
		String answer = null;
		
		Map<String, String> questionAnswer = new HashMap<String, String>();
		
		for(int i = 0; i < jsonarray.length(); i++)
		{
			question = jsonarray.getJSONObject(i).getString("question");
			answer = jsonarray.getJSONObject(i).getString("answer");
			
			questionAnswer.put(question, answer);
		}
		return questionAnswer;
	}
	
	public void postQuestion(String pregunta, String mail) throws Exception
	{	
		URL url = new URL("https://cognitiveassistant.in.edst.ibm.com:443/apiserver/api/v1/postQuestion");
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		Map<String, String> urlParameters = new HashMap<>();
		urlParameters.put("apiToken", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.IjRlN2ZjZTgzLThlN2EtNGYxYy1hYjk0LTE3N2E2YWIzODE4NCI.7I28yN8OOhv_6kQYNZ62BnqWuWGBhkxZAMFj81oMUOOAr35k3PYJLapC5-SJQ5rbfP591k_I9TG_5AM3TeMJbQ");
		urlParameters.put("semanticQuestion", pregunta);
		urlParameters.put("projectid", "2215");
		urlParameters.put("userid", mail);
		urlParameters.put("source", "AgentAssist");
		urlParameters.put("curatedLang", "es");
		urlParameters.put("username", mail);
		urlParameters.put("error_code", "");
		
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		
		wr.writeBytes(ParameterStringBuilder.getParamsString(urlParameters));
		wr.flush();
		wr.close();
		
		System.out.println("\nSending 'POST' request to URL: " + url);
		System.out.println("Post parameters: " + urlParameters);
		
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
