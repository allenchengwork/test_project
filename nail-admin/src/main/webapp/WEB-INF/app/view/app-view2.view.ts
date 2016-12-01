import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

@Component({
    moduleId: module.id,
    selector: 'app-view1',
    template: '<h3>測試頁面中</h3>'
})
export class AppView2 implements OnInit, OnDestroy {
    constructor() {
    }

    ngOnInit(): void {
        
    }

    ngOnDestroy() {
        
    }
}