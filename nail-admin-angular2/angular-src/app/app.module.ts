import { NgModule }      from '@angular/core';
import { Http, XHRBackend, RequestOptions } from '@angular/http';
import { APP_BASE_HREF } from '@angular/common';

import { SharedModule } from './module/shared.module';

import { environment } from '../environments/environment';

import { InterceptorService } from 'ng2-interceptors';

import { Logger, Options as LoggerOptions, Level as LoggerLevel } from 'angular2-logger/core';

import { ViewModule, PluginModule } from './module/all-module';

import { AppComponent }   from './app.component';

import { ImageModalComponent } from './plugin/all-plugin';

import { ServerURLInterceptor } from './interceptor/server-url-interceptor.class';

export function interceptorFactory(xhrBackend: XHRBackend, requestOptions: RequestOptions, serverURLInterceptor: ServerURLInterceptor) {
    let service = new InterceptorService(xhrBackend, requestOptions);
    service.addInterceptor(serverURLInterceptor);
    return service;
}

@NgModule({
    imports: [
        SharedModule,
        PluginModule,
        ViewModule
    ],
    declarations: [
        AppComponent
    ],
    providers: [
       {provide: 'appSettings', useValue: environment},
       {provide: APP_BASE_HREF, useValue: environment.baseUrl},
       Logger,
       {provide: LoggerOptions, useValue: {level: environment.logLevel}},
       ServerURLInterceptor,
       {
           provide: InterceptorService,
           useFactory: interceptorFactory,
           deps: [XHRBackend, RequestOptions, ServerURLInterceptor]
       }
    ],
    entryComponents: [ ImageModalComponent ],
    bootstrap: [ AppComponent ]
})
export class AppModule { }
