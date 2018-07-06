import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import javax.swing.JFrame;

public class myMain 
{
	public static void main(String[] args) throws Exception
	{
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
		
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		
		HostnameVerifier allHostsValid = new HostnameVerifier() 
		{
			public boolean verify(String hostname, SSLSession session)
			{
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		
		Window window = new Window();
		
		window.setTitle("Envio de información");
		window.setSize(500, 100);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
