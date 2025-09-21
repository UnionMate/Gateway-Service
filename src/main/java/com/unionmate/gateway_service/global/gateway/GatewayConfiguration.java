package com.unionmate.gateway_service.global.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;

@Configuration
@Profile({"local","dev"})
public class GatewayConfiguration {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
			// 인증 필요 없는 라우트
			.route("backend_route", r -> r.path("/**")
				.filters(f -> f
					.removeRequestHeader(HttpHeaders.COOKIE)
				)
				.uri("lb://backend-service"))
			.build();
	}
}
