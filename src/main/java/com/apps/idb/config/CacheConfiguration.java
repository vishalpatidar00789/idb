package com.apps.idb.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            //cm.createCache(com.apps.idb.repository.IDBUserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.apps.idb.repository.IDBUserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.IDBUser.class.getName(), jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.IDBUser.class.getName() + ".authorities", jcacheConfiguration);
           // cm.createCache(com.apps.idb.domain.IDBUser.class.getName(), jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.IDBUser.class.getName() + ".initiatedChapters", jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.IDBUser.class.getName() + ".partnerChapters", jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.UserProfile.class.getName(), jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.UserProfile.class.getName() + ".pics", jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.UserProfile.class.getName() + ".profileSettings", jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.ProfileSettings.class.getName(), jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.Photos.class.getName(), jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.UserAccount.class.getName(), jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.UserAccount.class.getName() + ".payments", jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.Packages.class.getName(), jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.Packages.class.getName() + ".payments", jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.Chapters.class.getName(), jcacheConfiguration);
            cm.createCache(com.apps.idb.domain.Payments.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
