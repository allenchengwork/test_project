import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule, Http, XHRBackend, RequestOptions } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';

import { environment } from '../environments/environment';

import { InterceptorService } from 'ng2-interceptors';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { Logger, Options as LoggerOptions, Level as LoggerLevel } from "angular2-logger/core";

import { AppRoutingModule } from './module/app-routing.module';

import { ViewModule } from './module/view.module';
import { PluginModule } from './module/plugin.module';

import { AppComponent }   from './app.component';

import { ServerURLInterceptor } from './interceptor/server-url.interceptor';

export function interceptorFactory(xhrBackend: XHRBackend, requestOptions: RequestOptions, serverURLInterceptor: ServerURLInterceptor) {
    let service = new InterceptorService(xhrBackend, requestOptions);
    service.addInterceptor(serverURLInterceptor);
    return service;
}

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        AppRoutingModule, 
        NgbModule.forRoot(), 
        PluginModule,
        ViewModule
    ],
    declarations: [
        AppComponent
    ],
    providers: [
       {provide: APP_BASE_HREF, useValue: environment.appRoot},
       {provide: 'APP_MODE', useValue: environment.appMode},
       {provide: 'APP_VERSION', useValue: environment.appVersion},
       {provide: 'API_URL', useValue:environment.apiUrl},
       Logger,
       {provide: LoggerOptions, useValue: {level: environment.logLevel}},
       ServerURLInterceptor,
       {
           provide: InterceptorService,
           useFactory: interceptorFactory,
           deps: [XHRBackend, RequestOptions, ServerURLInterceptor]
       }
    ],
    bootstrap: [ AppComponent ]
})
export class AppModule { }
