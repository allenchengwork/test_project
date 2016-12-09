import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';

import { ImageManager } from '../view/image-manager.view';
import { DataManager } from '../view/data-manager.view';

export const VIEW_MODULES = [
    ImageManager, DataManager
];

@NgModule({
    imports: [BrowserModule, AppRoutingModule, NgbModule.forRoot()],
    exports: VIEW_MODULES,
    declarations: VIEW_MODULES
})
export class ViewModule {
    
}