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
	<link rel="stylesheet" href="plugins/bootstrap-3.3.7/css/bootstrap.min.css" />
	
	<!-- Font Awesome 4.7.0 -->
	<link rel="stylesheet" href="plugins/font-awesome-4.7.0/css/font-awesome.min.css" />
	
	<!-- Ionicons 2.0.1 -->
	<link rel="stylesheet" href="plugins/ionicons-2.0.1/css/ionicons.min.css" />
	
	<!-- Theme style -->
	<link rel="stylesheet" href="resources/css/admin.min.css" />
	<!-- admin Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
	<link rel="stylesheet" href="resources/css/skins/_all-skins.min.css" />
	
	<!-- app style -->
    <link rel="stylesheet" href="resources/css/app.min.css?v=${appVersion}" />
    
    <!-- jQuery 2.2.3 -->
	<script src="plugins/jQuery/jquery-2.2.3.min.js"></script>
	
	<!-- app js -->
	<script src="resources/js/app.js?v=${appVersion}"></script>
    
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
<body class="hold-transition skin-blue sidebar-mini">
    <web-app>載入中...</web-app>
</body>
</html>
