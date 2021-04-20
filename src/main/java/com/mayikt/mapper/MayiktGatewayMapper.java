package com.mayikt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mayikt.entity.GateWayEntity;

public interface MayiktGatewayMapper {

	@Select("SELECT ID AS ID, route_id as routeid, route_name as routeName, route_pattern as routePattern"
			+ ",route_type as routeType, route_url as routeUrl from mayikt_gateway ")
	public List<GateWayEntity> getWayAll();
	
	@Update("update mayikt_gateway set route_url = #{routeUrl} where route_id = #{routeId}")
	public Integer updateGateWay(@Param("routeId")String routeId, @Param("routeUrl") String routeUrl);
}
