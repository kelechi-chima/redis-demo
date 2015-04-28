package com.kelechi.app;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

public class RedisDemoTokenStoreTest {

	private final RedisDemoTokenStore demoTokenStore = new RedisDemoTokenStore();
	
	@Test
	public void redisShouldStoreValues() {
		String token = UUID.randomUUID().toString();
		String username = "demouser@test.com";
		String password = "demo-password";
		
		Map<String, String> values = new HashMap<>();
		values.put("username", username);
		values.put("password", password);
		boolean result = demoTokenStore.storeToken(token, values);
		Assert.assertTrue(result);
		
		Map<String, String> returnedValues = demoTokenStore.resolveToken(token);
		Assert.assertNotNull(returnedValues);
		Assert.assertTrue(returnedValues.containsKey("username"));
		Assert.assertEquals(username, returnedValues.get("username"));
		Assert.assertTrue(returnedValues.containsKey("password"));
		Assert.assertEquals(password, returnedValues.get("password"));
	}
	
	@Test
	public void redisTokensShouldExpireAfterTimeout() {
		String token = UUID.randomUUID().toString();
		String username = "skimbola@gmail.com";
		String password = "crunch1234";
		
		Map<String, String> values = new HashMap<>();
		values.put("username", username);
		values.put("password", password);
		boolean result = demoTokenStore.storeToken(token, values);
		Assert.assertTrue(result);
		
		try {
			Thread.sleep(3*60*60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Map<String, String> returnedValue = demoTokenStore.resolveToken(token);
		Assert.assertTrue(returnedValue == null || returnedValue.isEmpty());
	}
}
