<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<base href="${appRoot}" />
	<meta charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport" />
	
    <title>網站管理後台</title>
    
    <!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css" />
	
	<!-- font-awesome 4.7.0 -->
	<link rel="stylesheet" href="resources/font-awesome/css/font-awesome.min.css" />
	
	<!-- app style -->
    <link rel="stylesheet" href="resources/css/app.min.css" />
    
    <!-- 1. Load libraries -->
     <!-- Polyfill for older browsers -->
    <script src="node_modules/core-js/client/shim.min.js"></script>
    <script src="node_modules/zone.js/dist/zone.js"></script>
    <script src="node_modules/reflect-metadata/Reflect.js"></script>
    <script src="node_modules/systemjs/dist/system.src.js"></script>
    <!-- 2. Configure SystemJS -->
    <script src="systemjs.config.js?v=${appVersion}"></script>
	<script>
		System.import('app').catch(function(err){ console.error(err); });
    </script>
</head>
<body>
    <web-app>載入中...</web-app>
</body>
</html>
