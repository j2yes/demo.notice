package com.demo.notice.infrastructure.adapter.cache;

import com.demo.notice.application.port.cache.CountCachePort;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLongAsync;
import org.redisson.api.RBatch;
import org.redisson.api.RFuture;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.redisson.api.options.KeysScanOptions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisCountCacheAdapter implements CountCachePort {

  private final RedissonClient redissonClient;

  @Override
  public long incrementAndGet(String prefix, String id) {
    return redissonClient.getAtomicLong(prefix + id).getAndIncrement();
  }

  @Override
  public long get(String prefix, String id) {
    return redissonClient.getAtomicLong(prefix + id).get();
  }

  @Override
  public Map<String, Long> multipleGet(String prefix, Collection<String> ids) {
    List<String> keys = ids.stream()
        .map(id -> prefix + id)
        .toList();

    RBatch batch = redissonClient.createBatch();

    Map<String, RFuture<Long>> futures = new HashMap<>();
    for (String key : keys) {
      RAtomicLongAsync atomicLong = batch.getAtomicLong(key);
      futures.put(key, atomicLong.getAsync());
    }

    batch.execute();

    Map<String, Long> result = new HashMap<>();
    for (Map.Entry<String, RFuture<Long>> entry : futures.entrySet()) {
      try {
        result.put(entry.getKey(), entry.getValue().get());
      } catch (Exception e) {
        result.put(entry.getKey(), null);
      }
    }

    return result;
  }

  @Override
  public Iterator<String> getKeys(String prefix) {
    RKeys keys = redissonClient.getKeys();

    KeysScanOptions options = KeysScanOptions
        .defaults()
        .pattern(prefix + "*")
        .limit(100);

    Iterable<String> keyIterable = keys.getKeys(options);
    return keyIterable.iterator();
  }
}
