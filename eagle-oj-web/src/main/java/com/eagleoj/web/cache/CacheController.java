package com.eagleoj.web.cache;

import com.eagleoj.web.DefaultConfig;
import com.eagleoj.web.judger.JudgeResult;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

import java.util.concurrent.TimeUnit;

/**
 * @author Smith
 **/
public class CacheController {

    /**
     * [{token, password}, ...]
     */
    private static Cache<String, String> authCache;

    private static Cache<String, JudgeResult> submissionCache;

    private static Cache<Integer, Object> leaderboardCache;

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
                                JudgeResult.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(100, MemoryUnit.MB))
                                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.HOURS)))
                                .withSizeOfMaxObjectGraph(5000)
                                .build());
        leaderboardCache = cacheManager
                .createCache("leaderboardCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Integer.class,
                                Object.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(20, MemoryUnit.MB))
                                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(DefaultConfig.LEADERBOARD_REFRESH_TIME, TimeUnit.MINUTES)))
                .build());
    }

    public static Cache<String, String> getAuthCache() {
        return authCache;
    }

    public static Cache<String, JudgeResult> getSubmissionCache() { return submissionCache; }

    public static Cache<Integer, Object> getLeaderboard() {
        return leaderboardCache;
    }

}
