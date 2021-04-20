package com.mayikt.service;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.mayikt.entity.GateWayEntity;
import com.mayikt.mapper.MayiktGatewayMapper;

import reactor.core.publisher.Mono;

/*
 * 基于数据库形式构建动态网关,代码实现创建动态网关实现
 * https://edu.aliyun.com/lesson_2007_18284#_18284
 */
@Service
public class GatewayService implements ApplicationEventPublisherAware {
	private ApplicationEventPublisher publisher;
	@Autowired
	private RouteDefinitionWriter routeDefinitionWriter;
	
	@Autowired
	private MayiktGatewayMapper mayiktGatewayMapper;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}
	
	public String loadAllLoadRoute() {
		List<GateWayEntity> gateWayEntities = mayiktGatewayMapper.getWayAll();
		for (GateWayEntity gb: gateWayEntities) {
			loadRoute(gb);
		}
		return "success";
	}
	
	/*
	 * 有了这个方法application.yml里面的[routes: #路由策略]这整个配置节点就可以去掉了。
	 */
	public String loadRoute(GateWayEntity gateWayEntity) {
		RouteDefinition definition = new RouteDefinition();
		Map<String, String> predicateParams = new HashMap<String, String>(8);
		PredicateDefinition predicate = new PredicateDefinition();
		FilterDefinition filterDefinition = new FilterDefinition();
		Map<String, String> filterParams = new HashMap<String, String>(8);
		//如果配置路由type为0的话,则从注册中心获取服务
		URI uri = null;
		if ("0".equals(gateWayEntity.getRouteType())) {
			uri = UriComponentsBuilder.fromUriString("lb://" + gateWayEntity.getRouteUrl() + "/").build().toUri();
			//这里写死的mayikt-member都应该从数据库里面读取出来
			//uri = UriComponentsBuilder.fromUriString("lb://mayikt-member/").build().toUri();
		} else {
			uri = UriComponentsBuilder.fromHttpUrl(gateWayEntity.getRouteUrl()).build().toUri();
		}
		//定义的路由唯一的ID
		definition.setId(gateWayEntity.getRouteId());
		//definition.setId("member");//路由ID,实际上应该从数据库里面读取出来
		predicate.setName("Path");
		//路由转发地址
		predicateParams.put("pattern", gateWayEntity.getRoutePattern());
		//predicateParams.put("pattern", "/member/**");
		predicate.setArgs(predicateParams);
		
		//名称是固定的,路径去前缀
		filterDefinition.setName("StripPrefix");
		filterParams.put("_genkey_0", "1");
		filterDefinition.setArgs(filterParams);
		definition.setPredicates(Arrays.asList(predicate));
		definition.setFilters(Arrays.asList(filterDefinition));
		definition.setUri(uri);
		routeDefinitionWriter.save(Mono.just(definition)).subscribe();
		this.publisher.publishEvent(new RefreshRoutesEvent(this));
		return "success";
	}
	/*
	 * 有了这个方法application.yml里面的[routes: #路由策略]这整个配置节点就可以去掉了。
	 */
	public String loadRoute() {
		RouteDefinition definition = new RouteDefinition();
		Map<String, String> predicateParams = new HashMap<String, String>(8);
		PredicateDefinition predicate = new PredicateDefinition();
		FilterDefinition filterDefinition = new FilterDefinition();
		Map<String, String> filterParams = new HashMap<String, String>(8);
		//如果配置路由type为0的话,则从注册中心获取服务
		URI uri = null;
		//if (gateWayEntity.getRouteType().equals("0")) {
			//uri = UriComponentsBuilder.fromUriString("lb://" + gateWayEntity.getRouteUrl() + "/").build();
			//这里写死的mayikt-member都应该从数据库里面读取出来
			uri = UriComponentsBuilder.fromUriString("lb://mayikt-member/").build().toUri();
//		} else {
//			uri = UriComponentsBuilder.fromHttpUrl(gateWayEntity.getRouteUrl()).build().toUrl();
//		}
		//定义的路由唯一的ID
		//definition.setId(gateWayEntity.getRouteId());
		definition.setId("member");//路由ID,实际上应该从数据库里面读取出来
		predicate.setName("Path");
		//路由转发地址
		//predicateParams.put("pattern", gateWayEntity.getRoutePattern());
		predicateParams.put("pattern", "/member/**");
		predicate.setArgs(predicateParams);
		
		//名称是固定的,路径去前缀
		filterDefinition.setName("StripPrefix");
		filterParams.put("_genkey_0", "1");
		filterDefinition.setArgs(filterParams);
		definition.setPredicates(Arrays.asList(predicate));
		definition.setFilters(Arrays.asList(filterDefinition));
		definition.setUri(uri);
		routeDefinitionWriter.save(Mono.just(definition)).subscribe();
		this.publisher.publishEvent(new RefreshRoutesEvent(this));
		return "success";
	}
}
