package com.api;

public class NCApiConst {
	//用户登录，获取token
	public static final String URL_SCMBASE_1_USERLOGIN = "/scmbase/1.0/userlogin";
	
	//根据销售订单号查询销售订单信息
	public static final String URL_SO_1_QUERYORDER = "/so/1.0/queryorder";
	//新增销售订单，支持多张
	public static final String URL_SO_1_INSERTORDER = "/so/1.0/insertorder";
	
	//根据采购组织、订单号查询采购订单
	public static final String URL_PU_1_QUERYORDER = "/pu/1.0/queryorder";
	//新增采购订单
	public static final String URL_PU_1_INSERTORDER = "/pu/1.0/insertorder";
	//新增到货单
	public static final String URL_PU_1_INSERTARRIVEBILL = "/pu/1.0/insertarrivebill";
	
	//用来查询可用量，用户输入一组可用量查询参数，返回对应的可用量
	public static final String URL_IC_1_QUERYATP = "/ic/1.0/queryatp";
	//用来查询可用量，根据库存组织、物料、计划日期查询可用量。适用于简单的查询场景
	public static final String URL_IC_1_QUERYATPSIM = "/ic/1.0/queryatpsim";
	//用来查询现存量，根据存量维度查询现存量，返回现存量明细信息
	public static final String URL_IC_1_QUERYONHAND = "/ic/1.0/queryonhand";
	//现存量简单查询服务。 根据库存组织、仓库、物料数组查询现存量
	public static final String URL_IC_1_QUERYONHANDSIM = "/ic/1.0/queryonhandsim";
	
	//根据信用查询参数查询信用数据
	public static final String URL_CREDIT_1_QUERYCREDIT = "/credit/1.0/querycredit";
}
