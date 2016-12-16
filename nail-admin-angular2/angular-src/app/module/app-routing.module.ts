import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {
    ImageManagerComponent, 
    DataManagerComponent
} from '../view/all-view';

const routes: Routes = [
    { path: '', redirectTo: '/imageManager', pathMatch: 'full' },
    { path: 'imageManager', component: ImageManagerComponent },
    { path: 'dataManager', component: DataManagerComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }