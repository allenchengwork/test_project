import { Component, Input } from '@angular/core';

import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FileUploader } from 'ng2-file-upload/ng2-file-upload';

import { Photo } from '../model/all-model';

const URL = 'http://127.0.0.1:9030/api';

@Component({
    selector: 'image-modal',
    templateUrl: './image-modal.component.html',
    styleUrls: ['./image-modal.component.css']
})
export class ImageModalComponent {
    @Input() 
    private title;
    
    @Input()
    private item: Photo;
    
    @Input()
    private uploader:FileUploader;
    
    public hasBaseDropZoneOver:boolean = false;

    constructor(public activeModal: NgbActiveModal) {
        
    }
    
    ngOnInit() {
        this.uploader = new FileUploader({url: URL});
    }
    
    uploadFile(event) {
        if (event.target.files && event.target.files[0]) {
            let reader = new FileReader();

            reader.onload = (e:ProgressEvent) => {
                let target = e.target as FileReader;
                this.item.url = target.result;
            };

            reader.readAsDataURL(event.target.files[0]);
        }
    }
    
    public fileOverBase(e:any) {
        this.hasBaseDropZoneOver = e;
    }
}