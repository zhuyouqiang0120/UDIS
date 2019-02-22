<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>节目爬取</title>
    <LINK href="css/default.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">

    window.onload = function(){
 
        document.getElementById('submit').onclick = function(){
        	// 不能有相同取值的下拉列表集合
            var items = document.getElementsByTagName('select'),
                   len = items.length,
                   i = 0,
                   valMap = {},
                   sel,
                   val;
             
            // 遍历并保存各个下拉列表的值于valMap
            for(; i < len; i++){
                sel = items[i];
                val = sel.options[sel.selectedIndex].value;
                 
                // 没有相同的取值时保存该值，否则返回false
                if(!valMap[val]){
                    valMap[val] = 1;
                }else{
                    alert('亲！请不要重复选择信息源！');
                    return false;
                }
            }
             
            // 校验通过，全部都不相同
            return true;
        }
    }
    
</script>

</head>

<body style="background:#DDDDFF;">
<form method="post" name="form1" action="/TVCrawler/add">

<table style="width:1000px;">

<div style="margin:0 auto;width:400px;">
<div style="float:left;">
<img src="images/JsoupCctvSpider.bmp" width=36px height=36px/>
</div>
<div style="float:left;">
&nbsp;&nbsp;&nbsp;&nbsp;
<span style="font-size:30px;">TVCrawler参数配置</span>
</div>
</div>     
        
<br/><br/><br/><br/><br/><br/>
 
 
 
 <div>
 <div style="float:left;margin-left:200px;">
   第1信息源：
  <select name="xy1"  class="inputText select" style="width:150px;">
    <option value="1">请选择第1信息源</option>
             <c:forEach items="${options}" var="option">
                 <option value="${option[0]}">${option[1]}</option>
             </c:forEach>
  </select>
  </div>
  
  <div style="float:left;margin-left:50px;">
           节目列表文件保存路径：
    <input type="text" name="programesPath" value="${programesPath}" class="inputText" style="width:200px;" />
  </div>
  </div>
  
  <br/>
  <br/>
  <br/>
  <br/>
  
 <div> 
 <div style="float:left;margin-left:200px;">
  第2信息源：
  <select name="xy2"  class="inputText select" style="width:150px;">
    <option value="2">请选择第2信息源</option>
             <c:forEach items="${options}" var="option">
                 <option value="${option[0]}">${option[1]}</option>
             </c:forEach>
  </select>
 
 </div> 
 
 <div style="float:left;margin-left:50px;">
      频道名单获取接口：
  <input type="text" name="api" value="${api}" class="inputText" style="width:225px;" />
 </div>
 
 </div>
 
  <br/>
  <br/>
  <br/>
  <br/>
  
 <div style="float:left;margin-left:475px;">
      抓取完毕回调的接口：
  <input type="text" name="overApi" value="${overApi}" class="inputText" style="width:225px;" />
 </div>
  

  <br/>
  <br/>
  <br/>
          
  
          
<div style="margin-left:870px;"> 
<input id="submit" type="submit" name="submit" class="button red" value="确认"/>&nbsp;       
<br/>
<br/>
<br/>
</div>
     
     
    

 
  
<span style="float:left;margin-left:200px;">       
当前用户选择的信息源为：
</span>

<table class="tableDefault" style="margin-left:400px;width:200px;">
<c:forEach items="${userXyNamesList}" var="userXyNamesline">
<tr class="dataGridTr">
<td>
<c:out value="${userXyNamesline}"/>
</td>
</tr>
</c:forEach> 
</table>

<br/>    
                             
<span style="float:left;margin-left:200px;"> 
当前不可达的信息源为：
</span>

<table class="tableDefault" style="margin-left:400px;width:200px;">
<c:forEach items="${unreachableNamesList}" var="unreachableNamesline">
<tr class="dataGridTr">
<td>
<c:out value="${unreachableNamesline}"/>
</td>
</tr>
</c:forEach> 
</table>

<br/>
<br/>
       
<span style="float:left;margin-left:200px;">            
当前节目列表文件保存路径为：
</span>

<table class="tableDefault" style="margin-left:200px;width:250px;">
<tr class="dataGridTr">
<td> 
<c:out value="${programesPath}"/>
</td>
</tr>
</table>    

<br/>
       
<span style="float:left;margin-left:200px;">      
            当前频道名单获取接口为：
</span>

<table class="tableDefault" style="margin-left:200px;width:200px;">
<tr class="dataGridTr">
<td> 
<c:out value="${api}"/>
</td>
</tr>
</table>

<br/>


<span style="float:left;margin-left:200px;">      
            当前抓取完毕回调的接口为：
</span>

<table class="tableDefault" style="margin-left:200px;width:300px;">
<tr class="dataGridTr">
<td> 
<c:out value="${overApi}"/>
</td>
</tr>
</table>

       
<br/> 
       
</table>
</form>
</html>
</body>
</html>