import { Component, Input } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

import { Menu } from '../model/all-model';

@Component({
    selector: 'sidebar-menu',
    templateUrl: './sidebar-menu.component.html'
})
export class SidebarMenuComponent {
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
    }
}