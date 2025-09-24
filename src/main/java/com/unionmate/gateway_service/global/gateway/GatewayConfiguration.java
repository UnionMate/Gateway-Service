package com.unionmate.gateway_service.global.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;

@Configuration
@Profile({"local","dev","prod"})
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

			//스웨거를 위한 라우트 설정(각 서비스마다 등록해줘야 합니다.)
			.route("backend-service_api_docs", r -> r.path("/api-docs/backend/**")
				.filters(f -> f
					.rewritePath("/api-docs/backend/(?<rem>.*)", "/${rem}")
				)
				.uri("lb://backend-service"))
			.build();
	}
}
