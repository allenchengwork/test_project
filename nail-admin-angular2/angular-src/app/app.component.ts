import { Component, Inject, ViewChild } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { Router, NavigationEnd } from '@angular/router';
import 'rxjs/add/operator/filter';

import { environment } from '../environments/environment';

import { Logger } from 'angular2-logger/core';

import { Menu } from './model/menu';

import { NavbarMenu } from './plugin/navbar-menu';
import { SidebarMenu } from './plugin/sidebar-menu';

import { MenuService } from './service/menu.service';

declare var $: any;

@Component({
    providers: [
        MenuService
    ],
    selector: 'app-root',
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
    
    private appVersion:string;
    
    private sub:any;
    
    private title:string = '';
    private smallTitle:string = '';
    
    private breadcrumb:Menu[] = [];
    
    private homeMenu:Menu[] = [];
    
    constructor(private logger: Logger,
            private router: Router,
            private menuService: MenuService) {
        this.appVersion = environment.appVersion;
    }

    ngOnInit() {
        this.logger.info('ngOnInit INFO OK');

        this.menuService.listMenu().then(menuList => {
            this.menuList = menuList;
            this.insertActiveMenu(this.menuList, this.homeMenu, 0);
            this.setTitleAndBreadcrumb();
        });
        
        this.sub = this.router.events
            .filter(event => event instanceof NavigationEnd)
            .subscribe((event: NavigationEnd) => {
                this.reloadPage(event);
            });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }
    
    ngAfterViewInit() {
        this.logger.info('ngAfterViewInit INFO');
    }
    
    ngAfterViewChecked() {
        this.logger.info('ngAfterViewChecked LOL');
    }
    
    reloadPage(event: NavigationEnd) {
        if (!this.menuList) {
            return;
        }
        this.logger.debug('NavigationEnd', event);
        this.resetMenu(event.url);
        this.setTitleAndBreadcrumb();
    }
    
    setTitleAndBreadcrumb() {
        this.breadcrumb.length = 0;
        this.insertActiveMenu(this.menuList, this.breadcrumb, 0);
        if (this.breadcrumb.length > 0) {
            this.title = this.breadcrumb[0].name;
            this.smallTitle = this.breadcrumb[1].name;
        }
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
    
    resetMenu(activeUrl) {
        this.clearActiveMenu(this.menuList);
        if (activeUrl == '/') {
            for (let menu of this.homeMenu) {
                menu.active = true;
            }
        } else {
            this.activeUrlMenu(activeUrl, this.menuList, null);
        }
    }
    
    clearActiveMenu(menuList) {
        this.logger.debug('clearActiveMenu', menuList);
        for (let menu of menuList) {
            menu.active = false;
            if (menu.childs) {
                this.clearActiveMenu(menu.childs);
            }
        }
    }
    
    activeUrlMenu(url, menuList, parentMenu) {
        this.logger.debug('activeUrlMenu', menuList);
        for (let menu of menuList) {
            if (menu.url == url) {
                if (parentMenu) {
                    parentMenu.active = true;
                }
                menu.active = true;
                return;
            }
            if (menu.childs) {
                this.activeUrlMenu(url, menu.childs, menu);
            }
        }
    }
    
    insertActiveMenu(menuList, breadcrumb, index) {
        this.logger.debug('insertActiveMenu', menuList);
        for (let menu of menuList) {
            if (menu.active == true) {
                this.logger.debug('index', index);
                breadcrumb[index] = menu;
                if (menu.childs) {
                    this.insertActiveMenu(menu.childs, breadcrumb, index+1);
                }
            }
        }
    }
}
