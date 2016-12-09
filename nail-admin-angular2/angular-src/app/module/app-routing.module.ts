import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ImageManager }   from '../view/image-manager.view';
import { DataManager }   from '../view/data-manager.view';

const routes: Routes = [
    { path: '', redirectTo: '/imageManager', pathMatch: 'full' },
    { path: 'imageManager', component: ImageManager },
    { path: 'dataManager', component: DataManager }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }