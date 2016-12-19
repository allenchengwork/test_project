import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { InterceptorService } from 'ng2-interceptors';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FileUploadModule } from 'ng2-file-upload/ng2-file-upload';

import { AppRoutingModule } from './app-routing.module';

import { TranslateModule, TranslateLoader, TranslateStaticLoader } from 'ng2-translate/ng2-translate';

@NgModule({
    imports: [
        BrowserModule, HttpModule,
        CommonModule, FormsModule, ReactiveFormsModule,
        NgbModule.forRoot(),
        FileUploadModule,
        AppRoutingModule,
        TranslateModule.forRoot({
            provide: TranslateLoader,
            useFactory: (http: InterceptorService) => new TranslateStaticLoader(http, '/assets/i18n', '.json'),
            deps: [InterceptorService]
        })
    ],
    exports: [
        BrowserModule, HttpModule,
        CommonModule, FormsModule, ReactiveFormsModule,
        NgbModule,
        FileUploadModule,
        AppRoutingModule
    ]
})
export class SharedModule {
    
}