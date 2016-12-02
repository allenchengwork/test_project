import { Component, Inject, ViewChild } from '@angular/core';
import {Observable} from 'rxjs/Rx';
import { Menu } from './model/menu';

import { NavbarMenu } from './plugin/navbar-menu';
import { SidebarMenu } from './plugin/sidebar-menu';

declare var $: any;

@Component({
    moduleId: module.id,
    selector: 'web-app',
    templateUrl: './app.component.html'
})
export class AppComponent {
    private screenSizes:any = {
        xs: 480,
        sm: 768,
        md: 992,
        lg: 1200
    };

    @ViewChild(NavbarMenu) navbarMenu: NavbarMenu;

    private menuList: Menu[];
    @ViewChild(SidebarMenu) sidebarMenu: SidebarMenu;
    
    constructor() {

    }

    ngOnInit() {
        console.log('ngOnInit');
        //this.bindSidebarClick();
        //this.bindTreeMenuClick();
        this.menuList = [
             {
                 name: 'Dashboard', 
                 url: '',
                 active: true,
                 childs: [
                      {name: 'Dashboard v1', url: '/appView1', active: true, childs: []},
                      {name: 'Dashboard v2', url: '/appView2', active: false, childs: []}
                 ]
             }
         ];
        /*this.menuList = new Observable<Menu[]>(observer => {
            console.log('Observable');
            let menus = [
                {
                    name: 'Dashboard', 
                    url: '',
                    active: true,
                    childs: [
                         {name: 'Dashboard v1', url: '/appView1', active: true, childs: []},
                         {name: 'Dashboard v2', url: '/appView2', active: false, childs: []}
                    ]
                }
            ];
            
            observer.next(menus);
            observer.complete();
        });*/
    }

    ngOnDestroy() {

    }
    
    ngAfterViewInit() {
        console.log('ngAfterViewInit');
    }
    
    ngAfterViewChecked() {
        console.log('ngAfterViewChecked LOL');
    }
    
    clickSidebar(event) {
        event.preventDefault();
        
        let $body = $('body');
        //Enable sidebar push menu
        if ($(window).width() > (this.screenSizes.sm - 1)) {
            if ($body.hasClass('sidebar-collapse')) {
                $body.removeClass('sidebar-collapse');
            } else {
                $body.addClass('sidebar-collapse');
            }
        }
        //Handle sidebar push menu for small screens
        else {
            if ($body.hasClass('sidebar-open')) {
                $body.removeClass('sidebar-open').removeClass('sidebar-collapse');
            } else {
                $body.addClass('sidebar-open');
            }
        }
    }
}
