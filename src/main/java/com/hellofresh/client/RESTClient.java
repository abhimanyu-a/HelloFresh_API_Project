package com.hellofresh.client;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.hellofresh.utils.PropertyUtils;

public class RESTClient {

	private CloseableHttpClient httpClient;
	private CloseableHttpResponse response;
	private RequestConfig config;
	private Gson GSON;
	private String JSONdata;
	private StringEntity entity;
	private PropertyUtils propertyUtils;
	private int API_TIMEOUT;

	public RESTClient() {
		propertyUtils = new PropertyUtils();
		try {
			API_TIMEOUT = Integer.parseInt(propertyUtils.getProperty("API_TIMEOUT"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		config = RequestConfig.custom().setConnectTimeout(API_TIMEOUT * 1000)
				.setConnectionRequestTimeout(API_TIMEOUT * 1000).setSocketTimeout(API_TIMEOUT * 1000).build();
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}

	/// <summary>
	/// Post call with url and model as parameters
	/// </summary>
	public <T> HttpResponse sendPOST(String uri, T model) throws Exception {
		try {
			HttpPost httpPost = new HttpPost(uri);
			httpPost.addHeader("Accept", propertyUtils.getProperty("Accept"));
			httpPost.addHeader("Content-Type", propertyUtils.getProperty("Content-Type"));
			GSON = new Gson();
			JSONdata = GSON.toJson(model);
			entity = new StringEntity(JSONdata);
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost);

			return response;
		} catch (Exception e) {
			throw new Exception("Error while making POST Call: " + e.toString());
		}
	}
	
	/// <summary>
		/// Put call with url and model as parameters
		/// </summary>
		public <T> HttpResponse sendPUT(String uri, T model) throws Exception {
			try {
				HttpPut httpPut = new HttpPut(uri);
				httpPut.addHeader("Accept", propertyUtils.getProperty("Accept"));
				httpPut.addHeader("Content-Type", propertyUtils.getProperty("Content-Type"));
				GSON = new Gson();
				JSONdata = GSON.toJson(model);
				entity = new StringEntity(JSONdata);
				httpPut.setEntity(entity);
				response = httpClient.execute(httpPut);

				return response;
			} catch (Exception e) {
				throw new Exception("Error while making POST Call: " + e.toString());
			}
		}

	/// <summary>
	/// Get call with url as parameter
	/// </summary>
	public HttpResponse sendGET(String uri) throws Exception {

		try {
			HttpUriRequest request = new HttpGet(uri);
			request.addHeader("Accept", "application/json");
			request.addHeader("Content-Type", "application/json");
			response = httpClient.execute(request);
			return response;
		} catch (Exception e) {
			throw new Exception("Error while making GET Call: " + e.toString());
		}
	}

	/// <summary>
	/// Delete call with url as parameter
	/// </summary>
	public HttpResponse sendDELETE(String uri) throws Exception {
		try {
			HttpDelete request = new HttpDelete();
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "application/json");
			request = new HttpDelete(uri);
			response = httpClient.execute(request);
			return response;
		} catch (Exception e) {
			throw new Exception("Error while making DELETE Call: " + e.toString());
		}

	}
}
