import { Component } from '@angular/core';
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

    constructor() {
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
        this.imageList = [
            [
                 {url:'/admin/assets/resources/photos/lights.jpg', title:'Lights', desc:'Lorem ipsum...'},
                 {url:'/admin/assets/resources/photos/fjords.jpg', title:'Fjords', desc:'Lorem ipsum...'},
                 {url:'/admin/assets/resources/photos/nature.jpg', title:'Nature', desc:'這是說明文字...'},
                 {url:'/admin/assets/resources/photos/unnamed.png', title:'Unnamed', desc:'Lorem ipsum...'},
                 {url:'/admin/assets/resources/photos/lights.jpg', title:'Lights', desc:'Lorem ipsum...'}
            ],
            [
                 {url:'/admin/assets/resources/photos/lights.jpg', title:'Lights', desc:'Lorem ipsum...'},
                 {url:'/admin/assets/resources/photos/lights.jpg', title:'Lights', desc:'Lorem ipsum...'},
                 {url:'/admin/assets/resources/photos/lights.jpg', title:'Lights', desc:'Lorem ipsum...'},
                 {url:'/admin/assets/resources/photos/lights.jpg', title:'Lights', desc:'Lorem ipsum...'},
                 {url:'/admin/assets/resources/photos/lights.jpg', title:'Lights', desc:'Lorem ipsum...'}
            ]
        ];
    }

    ngOnDestroy() {
        
    }
}