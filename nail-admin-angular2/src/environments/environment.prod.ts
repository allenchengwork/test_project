import { Level as LoggerLevel } from "angular2-logger/core";

export const environment = {
  production: true,
  appMode: 'production',
  appVersion: '1.0.0',
  appRoot: '/admin/',
  apiUrl: '/api/',
  logLevel: LoggerLevel.WARN
};
