import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { FileUploadModule } from 'ng2-file-upload/ng2-file-upload';

import { AppRoutingModule } from './app-routing.module';

@NgModule({
    imports: [
        BrowserModule, HttpModule,
        CommonModule, FormsModule,
        NgbModule.forRoot(),
        FileUploadModule,
        AppRoutingModule
    ],
    exports: [
        BrowserModule, HttpModule,
        CommonModule, FormsModule,
        NgbModule,
        FileUploadModule,
        AppRoutingModule
    ]
})
export class SharedModule {
    
}