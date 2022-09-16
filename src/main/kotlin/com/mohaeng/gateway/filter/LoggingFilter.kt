package com.mohaeng.gateway.filter

import mu.KotlinLogging
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


/**
 * Created by ShinD on 2022/08/28.
 */
class LoggingFilter : GlobalFilter, Ordered {

    private val log = KotlinLogging.logger {  }

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {

        val request = exchange.request
        val response = exchange.response
        log.info { "[Request]  - URI: [${request.uri}] ,  ID: [${request.id}] " }

        return chain.filter(exchange) // Post Filter
            .then(Mono.fromRunnable {
                log.info { "[Response] - STATUS: [${response.statusCode}] " }
            })
    }


    override fun getOrder(): Int = 0 //최우선

}