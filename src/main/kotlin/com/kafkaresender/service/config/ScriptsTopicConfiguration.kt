package com.kafkaresender.service.config
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("script")
class ScriptsTopicConfiguration {
    var scriptTopicMap: MutableMap<String, String> = mutableMapOf()
}
