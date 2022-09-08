package com.whyrano.gateway.configuration

import com.whyrano.gateway.filter.LoggingFilter
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by ShinD on 2022/08/28.
 */
@Configuration
class GatewayConfig {

    @Bean
    fun routes(builder: RouteLocatorBuilder): RouteLocator =
        builder.routes()
            .route {
                it.path("/member-service/**")
                    .filters{f ->
                        f.removeRequestHeader("Cookie")
                        f.rewritePath("/member-service/(?<segment>.*)", "/$\\{segment}")
                    }
                  .uri("lb://MOHANG-MEMBER-SERVICE")
            }
            .route {
                it.path("/meeting-service/**")
                    .filters{f ->
                        f.removeRequestHeader("Cookie")
                        f.rewritePath("/meeting-service/(?<segment>.*)", "/$\\{segment}")
                    }
                    .uri("lb://MOHANG-MEETING-SERVICE")
            }
            .build()

    @Bean
    fun logFiler(): GlobalFilter = LoggingFilter()
}