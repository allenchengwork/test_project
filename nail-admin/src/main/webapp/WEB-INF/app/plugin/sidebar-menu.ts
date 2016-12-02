import { Component, Input } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

import { Menu } from '../model/menu';

@Component({
    moduleId: module.id,
    selector: 'sidebar-menu',
    templateUrl: './sidebar-menu.html'
})
export class SidebarMenu {
    @Input()
    menuList: Menu[] = [];

    constructor() {
    }

    ngOnInit() {
        
    }

    ngOnDestroy() {
        
    }
    
    clickTree(event, item) {
        event.preventDefault();
        
        item.active = !item.active;
    }
    
    clickTreeMenu(event, childs, child) {
        event.preventDefault();
        
        for (let item of childs) {
            item.active = false;
        }
        child.active = true;
    }
}