package com.api.webvote.verifier;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class VoterVerifier {
	public boolean canVote(String clientCpf) throws ClientProtocolException, IOException {
		String url = "https://user-info.herokuapp.com/users/" + clientCpf;
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet request = new HttpGet(url);

			try (CloseableHttpResponse response = httpClient.execute(request)) {
				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode == 200) {
					// o usuário pode votar
					return true;
				}
				else
				{
					return false;
				}
			}
		}
	}
}
