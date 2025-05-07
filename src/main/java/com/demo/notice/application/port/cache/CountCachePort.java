package com.demo.notice.application.port.cache;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public interface CountCachePort {

  long incrementAndGet(String prefix, String key);

  long get(String prefix, String key);

  Map<String, Long> multipleGet(String prefix, Collection<String> keys);

  Iterator<String> getKeys(String prefix);
}
