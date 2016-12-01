import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent }   from './app.component';
import { AppSettings } from './app.settings';
import { AppRoutingModule } from './module/app-routing.module';

import { AppView1 }   from './view/app-view1.view';
import { AppView2 }   from './view/app-view2.view';

@NgModule({
    imports: [
        BrowserModule, AppRoutingModule
    ],
    declarations: [
        AppComponent, AppView1, AppView2
    ],
    providers: [
       {provide: 'APP_MODE', useValue: AppSettings.APP_MODE},
       {provide: 'APP_VERSION', useValue: AppSettings.APP_VERSION}
    ],
    bootstrap: [ AppComponent ]
})
export class AppModule { }
