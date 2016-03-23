package com.api;

public class NCApiConst {
	//用户登录，获取token
	public static final String URL_USER_LOGIN = "/user/login";
	
	//根据销售订单号查询销售订单信息
	public static final String URL_SALEORDER_BYBILLCODE = "/saleorder/bybillcode";
	//新增销售订单，支持多张
	public static final String URL_SALEORDER_INSERTWITHARRAY = "/saleorder/insertWithArray";
	
	//根据采购组织、订单号查询采购订单
	public static final String URL_PURCHASEORDER_BYBILLCODE = "/purchaseOrder/bybillcode";
	//新增采购订单
	public static final String URL_PURCHASEORDER_INSERTWITHARRAY = "/purchaseOrder/insertWithArray";
	//新增到货单
	public static final String URL_ARRIVEBILL_INSERTWITHARRAY = "/arrivebill/insertWithArray";
	
	//用来查询可用量，用户输入一组可用量查询参数，返回对应的可用量
	public static final String URL_ATP_DIM = "/atp/dim";
	//用来查询可用量，根据库存组织、物料、计划日期查询可用量。适用于简单的查询场景
	public static final String URL_ATP_MATERIAL = "/atp/material";
	//用来查询现存量，根据存量维度查询现存量，返回现存量明细信息
	public static final String URL_ONHAND_DIM = "/onhand/dim";
	//现存量简单查询服务。 根据库存组织、仓库、物料数组查询现存量
	public static final String URL_ONHANDWAREHOUSE = "/onhand/warehouse";
	
	//根据信用查询参数查询信用数据
	public static final String URL_CREDIT_QUERYCREDIT = "/credit/querycredit";
}
