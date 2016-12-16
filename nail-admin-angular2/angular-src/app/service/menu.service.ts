import { Injectable, Inject } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { Observable } from 'rxjs';

import 'rxjs/add/operator/toPromise';

import { Logger } from 'angular2-logger/core';

import { InterceptorService } from 'ng2-interceptors';

import { BaseService } from './base.service';

import { Menu } from '../model/all-model';

@Injectable()
export class MenuService extends BaseService {
    private static API_MAIN_NAVIGATION:string = 'menu/mainNavigation';
    
    constructor(logger: Logger, @Inject("appSettings") appSettings, http: InterceptorService) {
        super(logger, appSettings, http);
    }

    public listMenu(): Promise<Menu[]> {
        return this.listData(
            this.getAdminApi(MenuService.API_MAIN_NAVIGATION), 
            json => json.data as Menu[]);
    }
}