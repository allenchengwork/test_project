import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './module/app-routing.module';

import { ViewModule } from './module/view.module';
import { PluginModule } from './module/plugin.module';

import { AppSettings } from './app.settings';

import { AppComponent }   from './app.component';

@NgModule({
    imports: [
        BrowserModule, 
        AppRoutingModule, 
        NgbModule.forRoot(), 
        PluginModule,
        ViewModule
    ],
    declarations: [
        AppComponent
    ],
    providers: [
       {provide: 'APP_MODE', useValue: AppSettings.APP_MODE},
       {provide: 'APP_VERSION', useValue: AppSettings.APP_VERSION}
    ],
    bootstrap: [ AppComponent ]
})
export class AppModule { }
