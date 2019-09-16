package com.kafkaresender.service

import com.kafkaresender.service.config.ScriptsTopicConfiguration
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import javax.annotation.PostConstruct

@Service
@EnableConfigurationProperties(ScriptsTopicConfiguration::class)
class SenderService(
        private val stConfig: ScriptsTopicConfiguration,
        @Value("\${http-address}") val address: String
) {

    private val log = LoggerFactory.getLogger(SenderService::class.java)
    val restTemplate = RestTemplate()

    @PostConstruct
    fun showScriptsTopicMap() {
        log.debug("scripts-topic map: ${stConfig.scriptTopicMap}")
        log.debug("address: $address")
    }

    fun send(request: String, topic: String) {
        val headers = HttpHeaders()
        log.trace("request variable: $request")
        if(request == "") return
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("Accept", MediaType.TEXT_PLAIN_VALUE)
        stConfig.scriptTopicMap[topic]?.split(",")?.forEach { scriptName ->
            val entity: HttpEntity<String> = HttpEntity(request, headers)
            log.trace("exchange request run '$scriptName' with body: $entity")
            try {
                restTemplate.exchange(
                        "http://$address/$scriptName",
                        HttpMethod.POST,
                        entity,
                        Any::class.java,
                        headers
                )
            } catch (ex: HttpServerErrorException) {
                log.debug("Exception while sending http request: $ex")
            } catch (ex: Exception) {
                log.debug("Exception while sending http request: $ex")
            }
        }
    }

}
