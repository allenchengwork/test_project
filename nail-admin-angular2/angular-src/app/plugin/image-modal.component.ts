import { Component, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Logger } from 'angular2-logger/core';
import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateService } from 'ng2-translate/ng2-translate';

import { forbiddenNameValidator } from '../directive/all-directive';
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
    
    private photoForm: FormGroup;
    
    public hasBaseDropZoneOver:boolean = false;
    
    private validationMessages: any;
    
    private translateSub: any;
    
    private formErrors = {
        'photoName': ''
    };

    constructor(private logger: Logger, 
            private activeModal: NgbActiveModal,
            private fb: FormBuilder,
            private translate: TranslateService
            ) {
    }
    
    submitted = false;

    onSubmit() {
        this.submitted = true;
        this.logger.debug('onSubmit');
    }
    
    ngOnInit() {
        this.logger.debug('ngOnInit');
        this.translateSub = this.translate.getTranslation('zh').subscribe((res: any) => {
            this.validationMessages = res['validationMessages'];
        });
        this.buildForm();
    }
    
    ngOnDestroy() {
        if (this.translateSub) {
            this.translateSub.unsubscribe();
        }
    }
    
    uploadFile(event) {
        if (event.target.files && event.target.files[0]) {
            let file:File = event.target.files[0];
            this.item.fileName = file.name;
            
            let reader = new FileReader();
            reader.onload = (e:ProgressEvent) => {
                let target = e.target as FileReader;
                this.item.url = target.result;
            };

            reader.readAsDataURL(file);
        }
    }
    
    public fileOverBase(e:any) {
        this.hasBaseDropZoneOver = e;
    }
    
    ngAfterViewChecked() {
        this.logger.debug('ngAfterViewChecked');
    }
    
    buildForm() {
        this.photoForm = this.fb.group({
            'file': [this.item.file, [
                Validators.required
            ]],
            'photoName': [this.item.photoName, [
                Validators.required,
                Validators.minLength(3),
                Validators.maxLength(10),
                Validators.pattern('^[A-Za-z0-9]*$'),
                forbiddenNameValidator(/bob/i)
            ]]
        });

        this.photoForm.valueChanges
          .subscribe(data => this.onValueChanged(data));

        this.onValueChanged(); // (re)set validation messages now
    }

    onValueChanged(data?: any) {
        if (!this.photoForm) { return; }
        const form = this.photoForm;

        for (const field in this.formErrors) {
            this.formErrors[field] = '';
            const control = form.get(field);

            if (control && control.dirty && !control.valid) {
                const messages = this.validationMessages[field];
                for (const key in control.errors) {
                    if (this.formErrors[field]) {
                        this.formErrors[field] += ', ';
                    }
                    this.formErrors[field] += messages[key];
                }
            }
        }
    }
}