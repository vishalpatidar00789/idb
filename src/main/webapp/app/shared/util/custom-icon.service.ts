import { DomSanitizer } from '@angular/platform-browser';
import { MatIconRegistry } from '@angular/material/icon';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class CustomIconService {
    constructor(private matIconRegistry: MatIconRegistry, private domSanitizer: DomSanitizer) {
        this.init();
    }
    init() {
        this.matIconRegistry.addSvgIcon(
            'gender-male',
            this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/gender-male.svg')
        );
        this.matIconRegistry.addSvgIcon(
            'gender-female',
            this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/gender-female.svg')
        );
        this.matIconRegistry.addSvgIcon(
            'gender-transgender',
            this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/gender-transgender.svg')
        );
        this.matIconRegistry.addSvgIcon(
            'personality',
            this.domSanitizer.bypassSecurityTrustResourceUrl('../../../content/images/personality.svg')
        );
    }
}
