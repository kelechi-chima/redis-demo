package com.kelechi.app;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class RedisDemoTokenStore implements DemoTokenStore {

	private final Jedis jedis = new Jedis("localhost", 6379);
	
	private final Logger logger = LoggerFactory.getLogger(RedisDemoTokenStore.class);
	
	@Override
	public boolean storeToken(String token, Map<String, String> values) {
		if (StringUtils.isEmpty(token)) {
			throw new IllegalArgumentException("Token must not be null or empty");
		}
		
		if (values == null || values.isEmpty()) {
			throw new IllegalArgumentException("Values must not be null or empty");
		}
		
		String result = jedis.hmset(token, values);
		jedis.pexpire(token, 7200L);
		logger.info("Token {} added: {}", token, result);
		return StringUtils.isNotBlank(result);
	}

	@Override
	public Map<String, String> resolveToken(String token) {
		if (StringUtils.isEmpty(token)) {
			throw new IllegalArgumentException("Token must not be null or empty");
		}
		
		return jedis.hgetAll(token);
	}

}
