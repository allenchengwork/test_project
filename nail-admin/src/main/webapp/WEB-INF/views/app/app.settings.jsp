<%@ page language="java" contentType="application/javascript; charset=UTF-8"
    pageEncoding="UTF-8"%>
"use strict";
var AppSettings = (function () {
    function AppSettings() {
    }
    AppSettings.APP_VERSION = '${appVersion}';
    AppSettings.APP_MODE = '${appMode}';
    return AppSettings;
}());
exports.AppSettings = AppSettings;
//# sourceMappingURL=app.settings.js.map