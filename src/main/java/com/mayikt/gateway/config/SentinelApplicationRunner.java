package com.mayikt.gateway.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component//项目启动的时候就会加载这个限流规则
public class SentinelApplicationRunner implements ApplicationRunner{
	@Override
	public void run(ApplicationArguments args) throws Exception {
		initGatewayRules();
	}
	private void initGatewayRules() {
		Set<GatewayFlowRule> rules = new HashSet<>();
		rules.add(new GatewayFlowRule("mayikt_sentinel_gateway")
				.setCount(1)//限流阈值
				.setIntervalSec(1)//统计时间窗口,单位是秒,默认是1秒
				);
		GatewayRuleManager.loadRules(rules);
	}
}
