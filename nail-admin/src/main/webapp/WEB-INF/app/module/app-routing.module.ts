import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppView1 }   from '../view/app-view1.view';
import { AppView2 }   from '../view/app-view2.view';

const routes: Routes = [
    { path: '', redirectTo: '/appView1', pathMatch: 'full' },
    { path: 'appView1', component: AppView1 },
    { path: 'appView2', component: AppView2 }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }