import { Directive, Input, OnChanges, SimpleChanges } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, Validator, ValidatorFn, AsyncValidatorFn, Validators } from '@angular/forms';

/** A hero's name can't match the given regular expression */
export function dataExistsValidator( url: string ): AsyncValidatorFn {
    return ( control: AbstractControl ): { [key: string]: any } => {
        return new Promise((resolve, reject) => {
            setTimeout(function() {
                if(control.value === 'banner-001.jpg') {
                    resolve({ dataExists: true });
                } else {
                    resolve(null);
                }
            }, 1000);
        });
    };
}

@Directive( {
    selector: '[dataExists]',
    providers: [{ provide: NG_VALIDATORS, useExisting: DataExistsValidatorDirective, multi: true }]
})
export class DataExistsValidatorDirective implements Validator, OnChanges {
    @Input() forbiddenName: string;
    private valFn = Validators.nullValidator;

    ngOnChanges( changes: SimpleChanges ): void {
        const change = changes['dataExists'];
        if ( change ) {
            const val: string = change.currentValue;
            this.valFn = dataExistsValidator(val);
        } else {
            this.valFn = Validators.nullValidator;
        }
    }

    validate( control: AbstractControl ): { [key: string]: any } {
        return this.valFn( control );
    }
}