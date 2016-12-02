import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';

import { AppView1 } from '../view/app-view1.view';
import { AppView2 } from '../view/app-view2.view';

@NgModule({
    imports: [BrowserModule, AppRoutingModule, NgbModule.forRoot()],
    exports: [AppView1, AppView2],
    declarations: [AppView1, AppView2]
})
export class ViewModule {
    
}