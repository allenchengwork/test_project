import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';

import { SidebarMenu } from '../plugin/sidebar-menu';
import { NavbarMenu } from '../plugin/navbar-menu';

export const PLUGIN_MODULES = [
    SidebarMenu, NavbarMenu
];

@NgModule({
    imports: [BrowserModule, AppRoutingModule, NgbModule.forRoot()],
    exports: PLUGIN_MODULES,
    declarations: PLUGIN_MODULES
})
export class PluginModule {
}