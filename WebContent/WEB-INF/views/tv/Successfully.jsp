<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>节目爬取</title>
<LINK href="css/default.css" type="text/css" rel="stylesheet"/>
</head>

<body style="background:#DDDDFF;">
<form method="post" name="form1" action="/tvCrawler/add">
<table style="width:1000px;"> 
 
<div style="margin:0 auto;width:400px;">
<div style="float:left;">
<img src="images/JsoupCctvSpider.bmp" width=36px height=36px/>
</div>
<div style="float:left;">
&nbsp;&nbsp;&nbsp;&nbsp;
<span style="font-size:30px;">TVCrawler抓取成功</span>
</div>
</div>   
        

<br/><br/><br/><br/><br/><br/>


<span style="float:left;margin-left:200px;">      
 本次所用的信息源为：
</span>

<table class="tableDefault" style="margin-left:400px;width:200px;">
<tr class="dataGridTr">
<td> 
<c:out value="${usefulXy}"/>
</td>
</tr>
</table>  
         
         
<br/><br/>
     
       
       
              
<span style="float:left;margin-left:200px;">       
当前可获取的频道有：
</span>

<table class="tableDefault" style="margin-left:400px;width:200px;">
<c:forEach items="${programesList}" var="programe">
<tr class="dataGridTr">
<td>
<c:out value="${programe}"/>
</td>
</tr>
</c:forEach> 
</table>   
    
     
         
</table>
       
<br/> 
       
  
</form>
</body>
</html>
