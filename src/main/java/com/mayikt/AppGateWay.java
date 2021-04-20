package com.mayikt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mayikt.mapper")
public class AppGateWay {
	/*
	 * mayikt-gateWay是一个独立的Maven项目,可以直接运行
	 * 启用nacos注册中心时:
	 * 1,先将本地的nacos服务启动起来
	 * 2,再启动mayikt-service-impl-member项目
	 * 3,再启动mayikt-gateWay本项目就可以了
	 * 启动成功之后访问:
	 * 正常测试时访问:http://127.0.0.1/mayikt
	 * 测试注册中心时访问:http://127.0.0.1/member/getUser
	 * 网关集群测试:
	 * 把application.yml里面的端口改成81,启动
	 * 然后再把application.yml里面的端口改成82,再启动,集群就搭建好了
	 * http://127.0.0.1:82/?token=11
	 */
	public static void main(String[] args) {
		SpringApplication.run(AppGateWay.class, args);
	}
}
