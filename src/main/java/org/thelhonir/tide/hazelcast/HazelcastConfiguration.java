package org.thelhonir.tide.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    public static final String INSTANCE_NAME = "TideApp";
    public static final String ANNOUNCEMENTS_MAP = "AnnouncementsMap";

    @Bean
    public Config hazelcastConfig() {
        return new Config().setInstanceName(INSTANCE_NAME)
                .addMapConfig(new MapConfig().setName(ANNOUNCEMENTS_MAP)
                        .setMaxSizeConfig(new MaxSizeConfig(500, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU).setTimeToLiveSeconds(20));

    }
}
