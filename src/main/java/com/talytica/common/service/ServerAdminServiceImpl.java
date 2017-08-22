package com.talytica.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.JSONArray;
import org.json.JSONObject;

@Slf4j
@Service
public class ServerAdminServiceImpl implements ServerAdminService {

	
	@Value("${com.talytica.integrationserver.user:test}")
	private String userName;
	@Value("${com.talytica.integrationserver.user:password}")
	private String userPassword;
	
	@Value("${com.talytica.urls.assessment}")
	private String BASE_SURVEY_URL;
	@Value("${com.talytica.urls.portal}")
	private String BASE_PORTAL_URL;
	@Value("${com.talytica.urls.integration}")
	private String BASE_SERVICE_URL;
	
	@Override
	public boolean clearRemoteCache(String server) {
	
		Client client = null;
		WebTarget target;		
		switch (server) {
		case "integration":
			client = getIntegrationClient();
			target = client.target(BASE_SERVICE_URL + "/integration/clearcache");
			break;
		case "survey":
			client = ClientBuilder.newClient();
			target = client.target(BASE_SURVEY_URL + "/survey/clearcache");
			break;
		default:
			return false; 		
		}
		try {
			javax.ws.rs.core.Response result = target.request(MediaType.APPLICATION_JSON).post(null);
			if (result.getStatus() >= 300) {
				log.warn("Cache Clear of {} failed: {}", server, result.getStatusInfo().getReasonPhrase());
				return false;
			} else {
				log.info("SUCCESS: Cleared {} Cache", server);
			}
		} catch (Exception e) {
			log.warn("Cache Clear of {} Failed {}", server, e.getMessage());
			return false;
		}		
		return true;
	}

	@Override
	public JSONObject serverHealthCheck(String serverName) {

		JSONObject status = new JSONObject();
		Client client = ClientBuilder.newClient();
		WebTarget target;	
    	switch (serverName) {
    	case "integration":
    		target = client.target(BASE_SERVICE_URL + "/health");
    		break;
    	case "survey":
    		target = client.target(BASE_SURVEY_URL + "/health");
    		break;
    	case "portal":
    		target = client.target(BASE_PORTAL_URL + "/health");
    		break;
    	default:
    		target = client.target("/health");
    		break;    		
    	}
		
		try {
			javax.ws.rs.core.Response result = target.request(MediaType.APPLICATION_JSON).get();	
			if (result.getStatus() >= 300) {
				log.error("Status Check of {} Failed: {}", serverName, result.getStatusInfo().getReasonPhrase());
				status.put("status", "UNKNOWN");
			} else {
				String resp = result.readEntity(String.class);
				log.debug("Get status request {} resulted in: \n {}",target.toString(), resp);
				status = new JSONObject(resp);
			}
		} catch (Exception e) {
			log.warn("Status Check of {} Failed: {}", serverName, e.getMessage());
			status.put("status", "DOWN");
		}	
		return status;
	}
	  
	public Client getIntegrationClient() {
		    Client client = null;
		    ClientConfig cc = new ClientConfig();
		    cc.property("jersey.config.client.followRedirects", Boolean.valueOf(false));
			try {
				SSLContext sslContext = SSLContext.getInstance("TLS");
				sslContext.init(null, new TrustManager[]{new X509TrustManager() {
			        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
			        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
			        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
			    }}, new java.security.SecureRandom());

				client = ClientBuilder.newBuilder().sslContext(sslContext).withConfig(cc).build();
				
			} catch (Exception e) {
				log.warn("Integration client configured WITHOUT ssl context");
				client = ClientBuilder.newBuilder().withConfig(cc).build();			
			}
		    HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userName, userPassword);
		    client.register(feature);
		    
		    return client;
	  }
}
