package com.hellofresh.services;

import java.util.concurrent.ConcurrentHashMap;

import com.hellofresh.client.RESTClient;
import com.hellofresh.utils.PropertyUtils;

public class BaseService {

	PropertyUtils propertyUtils;
	private ConcurrentHashMap<Long, RESTClient> _restClientCollection;

	public BaseService() {
		this.propertyUtils = new PropertyUtils();
		_restClientCollection = new ConcurrentHashMap<Long, RESTClient>();
	}

	public RESTClient client() {
		RESTClient client = getRESTClient();
		return client;
	}

	private RESTClient getRESTClient() {
		if (_restClientCollection.contains(Thread.currentThread().getId())) {
			return _restClientCollection.get(Thread.currentThread().getId());
		}
		RESTClient client = new RESTClient();
		_restClientCollection.put(Thread.currentThread().getId(), client);
		return client;

	}
}