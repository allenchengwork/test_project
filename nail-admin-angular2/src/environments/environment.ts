// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `angular-cli.json`.

import { Level as LoggerLevel } from "angular2-logger/core";

export const environment = {
  production: false,
  appMode: 'dev',
  appVersion: '1.0.0',
  appRoot: '/admin/',
  apiUrl: 'http://127.0.0.1:8080/api/',
  logLevel: LoggerLevel.DEBUG
};
