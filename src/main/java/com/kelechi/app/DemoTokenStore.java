package com.kelechi.app;

import java.util.Map;

public interface DemoTokenStore {

	boolean storeToken(String token, Map<String, String> values);
	
	Map<String, String> resolveToken(String token);
	
}
