import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';

import { ImageManager } from '../view/image-manager.view';
import { DataManager } from '../view/data-manager.view';

let viewModules = [
    ImageManager, DataManager
];

@NgModule({
    imports: [BrowserModule, AppRoutingModule, NgbModule.forRoot()],
    exports: viewModules,
    declarations: viewModules
})
export class ViewModule {
    
}