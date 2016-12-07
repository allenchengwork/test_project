import { Injectable, Inject } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { Observable } from 'rxjs';

import 'rxjs/add/operator/toPromise';

import { Logger } from 'angular2-logger/core';

import { InterceptorService } from 'ng2-interceptors';

@Injectable()
export class BaseService {
    constructor(private logger: Logger, private apiUrl: string, protected http: InterceptorService) {
        
    }
    
    protected getAdminApi(apiName): string {
        let apiPath = this.apiUrl + "admin/" + apiName;;
        this.logger.info('getAdminApi', apiPath);
        return apiPath;
    }
    
    protected listData(apiUrl, handleData): Promise<any> {
        return this.http.get(apiUrl)
            .toPromise()
            .then(response => {
                let json = response.json();
                if (json.success) {
                    if (handleData) {
                        return handleData(json);
                    }
                } else {
                    this.handleFail(json.message);
                }
            })
            .catch(error => {
                this.handleError(error);
            });
    }
    
    protected handleFail(message: string): Promise<any> {
        this.logger.error('handleFail', message);
        return Promise.reject(message);
    }

    protected handleError(error: any): Promise<any> {
        this.logger.error('handleError', error);
        return Promise.reject(error.message || error);
    }
}