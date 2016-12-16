import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { FileUploadModule } from 'ng2-file-upload/ng2-file-upload';

import { SharedModule } from './shared.module';

import { 
    SidebarMenuComponent, 
    NavbarMenuComponent, 
    ImageModalComponent
} from '../plugin/all-plugin';

export const PLUGIN_MODULES = [
    SidebarMenuComponent, NavbarMenuComponent,
    ImageModalComponent
];

@NgModule({
    imports: [SharedModule],
    exports: PLUGIN_MODULES,
    declarations: PLUGIN_MODULES,
    entryComponents: [ImageModalComponent]
})
export class PluginModule {
}