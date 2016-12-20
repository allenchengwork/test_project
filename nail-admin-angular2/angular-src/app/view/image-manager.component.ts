import { Component, Inject } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

import { Logger } from 'angular2-logger/core';
import { NgbModal, NgbActiveModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

import { ImageModalComponent } from '../plugin/all-plugin';

import { Label, Photo } from '../model/all-model';

@Component({
    selector: 'image-manager',
    templateUrl: './image-manager.component.html',
    styleUrls: ['./image-manager.component.css']
})
export class ImageManagerComponent {
    private labelList:Label[] = [];
    private imageList:Photo[][] = [];

    constructor(private logger: Logger, 
            @Inject("appSettings") private appSettings:any, 
            private modalService: NgbModal) {
    }

    ngOnInit() {
        this.labelList = [
            {text: '分類一'},
            {text: '分類二'},
            {text: '分類三'},
            {text: '分類四'},
            {text: '分類五'},
            {text: '分類六'},
            {text: '分類七'}
        ];
        let imagePath = this.appSettings.baseUrl+'assets/photos/';
        this.imageList = [
            [
                 {url:imagePath+'lights.jpg', photoFile: null, fileName: '', photoName: '', title:'Lights', desc:'Lorem ipsum...'},
                 {url:imagePath+'fjords.jpg', photoFile: null, fileName: '', photoName: '', title:'Fjords', desc:'Lorem ipsum...'},
                 {url:imagePath+'nature.jpg', photoFile: null, fileName: '', photoName: '', title:'Nature', desc:'這是說明文字...'},
                 {url:imagePath+'unnamed.png', photoFile: null, fileName: '', photoName: '', title:'Unnamed', desc:'Lorem ipsum...'},
                 {url:imagePath+'lights.jpg', photoFile: null, fileName: '', photoName: '', title:'Lights', desc:'Lorem ipsum...'}
            ],
            [
                 {url:imagePath+'lights.jpg', photoFile: null, fileName: '', photoName: '', title:'Lights', desc:'Lorem ipsum...'},
                 {url:imagePath+'lights.jpg', photoFile: null, fileName: '', photoName: '', title:'Lights', desc:'Lorem ipsum...'},
                 {url:imagePath+'lights.jpg', photoFile: null, fileName: '', photoName: '', title:'Lights', desc:'Lorem ipsum...'},
                 {url:imagePath+'lights.jpg', photoFile: null, fileName: '', photoName: '', title:'Lights', desc:'Lorem ipsum...'},
                 {url:imagePath+'lights.jpg', photoFile: null, fileName: '', photoName: '', title:'Lights', desc:'Lorem ipsum...'}
            ]
        ];
    }

    ngOnDestroy() {
        
    }
    
    editImage(item) {
        const modalRef = this.modalService.open(ImageModalComponent);
        modalRef.componentInstance.title = '圖片編輯';
        modalRef.componentInstance.item = Object.assign({}, item);
        let recItem = item;
        modalRef.componentInstance.saveItem = (item) => {
            this.logger.debug('saveItem', item);
            Object.assign(recItem, item);
            modalRef.close('saveItem');
        };
        modalRef.result.then((result) => {
            this.logger.debug(`Closed with: ${result}`);
        }, (reason) => {
            this.logger.debug(`Dismissed ${this.getDismissReason(reason)}`);
        });
    }
    
    private getDismissReason(reason: any): string {
        if (reason === ModalDismissReasons.ESC) {
          return 'by pressing ESC';
        } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
          return 'by clicking on a backdrop';
        } else {
          return  `with: ${reason}`;
        }
    }
}