package com.mayikt.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/*
 * spring-cloud-gateway-core-2.0.0.RELEASE.jar,里面的LoadBalancerClientFilter也是同样实现了GlobalFilter, Ordered这俩个接口
 * org.springframework.cloud.gateway.filter.LoadBalancerClientFilter
 */
@Component
public class TokenGlobalFilter implements GlobalFilter, Ordered {
	@Value("${server.port}")
	private String serverPort;
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		//如何获取参数
		String token = exchange.getRequest().getQueryParams().getFirst("token");
		if (StringUtils.isEmpty(token)) {
			ServerHttpResponse response = exchange.getResponse();
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			String msg = "token not is null";
			DataBuffer buffer = response.bufferFactory().wrap(msg.getBytes());
			return response.writeWith(Mono.just(buffer));
		}
		//return chain.filter();//放行
		//在请求头中存放serverPort
		ServerHttpRequest request = exchange.getRequest().mutate().header("serverPort", serverPort).build();
		return chain.filter(exchange.mutate().request(request).build());//放行
	}
	@Override
	public int getOrder() {//过滤器的执行顺序,优先级
		return -1;
	}
}
