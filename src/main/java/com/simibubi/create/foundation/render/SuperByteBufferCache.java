package com.simibubi.create.foundation.render;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class SuperByteBufferCache {

	Map<Compartment<?>, Cache<Object, SuperByteBuffer>> cache;

	public SuperByteBufferCache() {
		cache = new HashMap<>();
		registerCompartment(Compartment.GENERIC_TILE);
		registerCompartment(Compartment.PARTIAL);
		registerCompartment(Compartment.DIRECTIONAL_PARTIAL);
	}


	public <T> SuperByteBuffer get(Compartment<T> compartment, T key, Supplier<SuperByteBuffer> supplier) {
		Cache<Object, SuperByteBuffer> compartmentCache = this.cache.get(compartment);
		try {
			return compartmentCache.get(key, supplier::get);
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}

	public <T> void invalidate(Compartment<T> compartment, T key) {
		Cache<Object, SuperByteBuffer> compartmentCache = this.cache.get(compartment);
		compartmentCache.invalidate(key);
	}

	public void registerCompartment(Compartment<?> instance) {
		cache.put(instance, CacheBuilder.newBuilder()
			.build());
	}

	public void registerCompartment(Compartment<?> instance, long ticksUntilExpired) {
		cache.put(instance, CacheBuilder.newBuilder()
			.expireAfterAccess(ticksUntilExpired * 50, TimeUnit.MILLISECONDS)
			.build());
	}

	public void invalidate() {
		cache.forEach((comp, cache) -> cache.invalidateAll());
	}

}
