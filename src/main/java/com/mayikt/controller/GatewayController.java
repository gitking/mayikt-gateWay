package com.mayikt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayikt.mapper.MayiktGatewayMapper;
import com.mayikt.service.GatewayService;

@RestController
public class GatewayController {
	@Autowired
	private GatewayService gatewayService;
	@Autowired
	private MayiktGatewayMapper mayiktGatewayMapper;
	/*
	 * 从数据库同步网关配置,生产环境建议把这个方法放到启动监听里面
	 * 项目已启动就直接访问这个请求从数据加载网关配置,我们这次本地测试就在项目启动成功之后,自己手工在浏览器上访问一下这个请求就可以
	 */
	@RequestMapping("/synGatewayConfig")
	public String synGatewayConfig() {
		return gatewayService.loadAllLoadRoute();
	}
	/*
	 * 初始化默认网关配置
	 */
	@RequestMapping("/synRoute")
	public String save() {
		initAllRoute();
		return "正在配置中...";
	}
	/*
	 * 同步数据库配置
	 */
	@RequestMapping("/updateSynRoute")
	public String synRoute(String routeId, String routeUrl){
		Integer result = mayiktGatewayMapper.updateGateWay(routeId, routeUrl);
		initAllRoute();
		return result > 0 ? "数据更新成功,正在同步中..." : "同步失败";
	}
	public void initAllRoute() {
		gatewayService.loadAllLoadRoute();
	}
}
