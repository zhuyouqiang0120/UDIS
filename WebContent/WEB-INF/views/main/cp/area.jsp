<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
	name="viewport" />
<link href="${ContextPath}/static/bootstrap/css/bootstrap.min.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ContextPath}/static/bootstrap/css/bootstrap-switch.css" media="all" rel="stylesheet" type="text/css" />
<link href="${ContextPath}/static/hySpin/css/spin.core.css" media="all" rel="stylesheet" type="text/css" />
<title>网络爬去</title>
</head>
<body>
	<div class="panel panel-default"
		style="margin: 1px; height: 100%; width: 101%; overflow-y: scroll;">
		<div class="panel-heading">
			<h4 class="panel-title">
				<span class="glyphicon glyphicon-pushpin"></span> 区域管理
			</h4>
		</div>
		<div class="panel-body">
			<div id="loading" style="position: absolute; display: hidden; width: 200px; height: 200px; align: center; left: 45%; top: 40%;">
				<img src="${ContextPath}/static/hySpin/img/loadingR.gif" />
			</div>
			<div id="spin-table"></div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${ContextPath}/static/js/jquery-2.0.3.js"></script>
<script type="text/javascript" src="${ContextPath}/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ContextPath}/static/bootstrap/js/bootstrap-switch.js"></script>
<script type="text/javascript" src="${ContextPath}/static/hySpin/lib/netname.core.js"></script>
<script type="text/javascript" src="${ContextPath}/static/hySpin/lib/spin.util.js"></script>
<script type="text/javascript" src="${ContextPath}/static/hySpin/lib/spin.table.js"></script>
<script type="text/javascript">
;({
	table : {
		pageSize : 15,
		orderCase : '',
		isAllowMulit : true,
		uri : '../area/getAreas?deleted=0&AreaType=0&interTag=0',
		util :[{name : '添加区域', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-plus', uri : '../area/insertArea', func:'add'},
	           {name : '编辑区域', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-edit', uri : '../area/updateArea', func:'edit'},
	           {name : '移除区域', type : 'btn', direct:'left', css:'btn btn-default', icon : 'glyphicon glyphicon-minus', uri : '../area/removeArea', func:'remove'},
	          ],
	     field : [{name : 'id', text : 'ID', isShow : true,  type:'INDEX',popover:false, readonly:true}, 
			         {name : 'area', text : '区域名称', isShow : true,  type:'text', popover:false, readonly:false}, 
			         {name : 'remark', text : '区域详情', isShow : true,  type:'text',popover:false, readonly:false},
			         {name : 'createtime', text : '创建时间', isShow : true,  type:'sys', value:'', popover:false, readonly:true}, 
			         {name : 'updatetime', text : '更改时间', isShow : false,  type:'sys', value:'', popover:false, readonly:true}, 
			         {name : 'isdelete', text : '', isShow : false,  type:'sys', value:0, popover:false}]
	},
	init : function(){
		spin.table.init( this.table );
	}
}).init();

Class('Extend', null, {
	Static : {
		runTask : function( _name, items ){
			if( items.length == 0 ){
				spin.modal.show(1, _name, '请先选择您需要 ' + _name + '的业务，谢谢~');
			}else{
				var param = param = '{\'ids\' : \'';
				for(var i = 0; i < items.length; ++i){
					//此处默认删除是按照主键ID，如需拓展可根据field中type=INDEX参数进行判断
					param += spin.table.list[ items[i] ].id;
					if(i != items.length - 1){
						param += ',';
					}
				}
				param = eval('(' + ( param + '\'}' ) + ')');
				
				$.ajax({
					type : "post",
					url : '../task/runTask',
					data : param,
					async : false,
					success : function( data ){console.log(data);
						if(typeof data === 'string'){
							data = eval("(" + data + ")");
						}
						var dataHTML = '<table class="table table-bordered"	style="text-align: center"><caption align="top" style="text-align: center"><b>运行结果</b></caption>';
						for(var i = 0; i < data.length; i++){
							dataHTML += '<tr>' + 
											'<td>' + ( data[i].name || '' ) + '</td>' + 
											'<td>' + (data[i].success ? '<span class="label label-success">爬取成功</span>' : '<span class="label label-danger">爬取失败</span>') + '</td>' +
											'<td><button　class="btn btn-default btn-sm" onclick="window.open(\'../data/getData?ID=' + data[i].taskID + '\');"><span class="glyphicon glyphicon-list-alt"></span> 查看詳情</button></td>' +
										'</tr>';	
						}	
						dataHTML += '</table>';console.log(dataHTML);
						spin.modal.show(1, "数据爬取", 'aaaaaa');
					},
					dataType:'json',
					timeout : 50000,
					error : function(req, error){
						alert('爬取結果出錯');
					}
				});
			}
		},
		dataDetail : function( _name, item ){
			window.open('../data/getData?ID=' + spin.table.list[ item ].ID);
		}
	}
});
</script>
</html>