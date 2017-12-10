package org.inlighting.oj.web.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.inlighting.oj.web.judger.JudgerResult;

import java.util.concurrent.TimeUnit;

/**
 * @author Smith
 **/
public class CacheController {

    /**
     * [{token, password}, ...]
     */
    private static Cache<String, String> authCache;

    private static Cache<String, JudgerResult> submissionCache;

    static {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
        authCache = cacheManager
                .createCache("authCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                String.class,
                                String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10, MemoryUnit.MB)));

        submissionCache = cacheManager
                .createCache("submissionCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                String.class,
                                JudgerResult.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10, MemoryUnit.MB))
                                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(5, TimeUnit.MINUTES)))
                                .build()
                );
    }

    public static Cache<String, String> getAuthCache() {
        return authCache;
    }

    public static Cache<String, JudgerResult> getSubmissionCache() { return submissionCache; }
}
