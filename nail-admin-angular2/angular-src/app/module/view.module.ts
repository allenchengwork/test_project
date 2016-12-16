import { NgModule } from '@angular/core';
import { SharedModule } from './shared.module';

import { PluginModule } from './plugin.module';

import { 
    ImageManagerComponent, 
    DataManagerComponent 
} from '../view/all-view';

export const VIEW_MODULES = [
    ImageManagerComponent, DataManagerComponent
];

@NgModule({
    imports: [SharedModule, PluginModule],
    exports: VIEW_MODULES,
    declarations: VIEW_MODULES
})
export class ViewModule {
    
}