package com.mayikt.entity;

//import lombok.Data;

//@Data
public class GateWayEntity {
	private Long id;
	private String routeId;
	private String routeName;
	private String routePattern;
	private String routeType;
	private String routeUrl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getRoutePattern() {
		return routePattern;
	}
	public void setRoutePattern(String routePattern) {
		this.routePattern = routePattern;
	}
	public String getRouteType() {
		return routeType;
	}
	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}
	public String getRouteUrl() {
		return routeUrl;
	}
	public void setRouteUrl(String routeUrl) {
		this.routeUrl = routeUrl;
	}
}
