import { Component, Inject } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

import { Label } from '../model/label';
import { Photo } from '../model/photo';

@Component({
    selector: 'image-manager',
    templateUrl: './image-manager.view.html',
    styleUrls: ['./image-manager.view.css']
})
export class ImageManager {
    private labelList:Label[] = [];
    private imageList:Photo[][] = [];

    constructor(@Inject("BASE_URL") private baseUrl:string) {
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
        let imagePath = this.baseUrl+'assets/photos/';
        this.imageList = [
            [
                 {url:imagePath+'lights.jpg', title:'Lights', desc:'Lorem ipsum...'},
                 {url:imagePath+'fjords.jpg', title:'Fjords', desc:'Lorem ipsum...'},
                 {url:imagePath+'nature.jpg', title:'Nature', desc:'這是說明文字...'},
                 {url:imagePath+'unnamed.png', title:'Unnamed', desc:'Lorem ipsum...'},
                 {url:imagePath+'lights.jpg', title:'Lights', desc:'Lorem ipsum...'}
            ],
            [
                 {url:imagePath+'lights.jpg', title:'Lights', desc:'Lorem ipsum...'},
                 {url:imagePath+'lights.jpg', title:'Lights', desc:'Lorem ipsum...'},
                 {url:imagePath+'lights.jpg', title:'Lights', desc:'Lorem ipsum...'},
                 {url:imagePath+'lights.jpg', title:'Lights', desc:'Lorem ipsum...'},
                 {url:imagePath+'lights.jpg', title:'Lights', desc:'Lorem ipsum...'}
            ]
        ];
    }

    ngOnDestroy() {
        
    }
}