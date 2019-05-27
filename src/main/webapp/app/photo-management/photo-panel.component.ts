import { Component, OnInit, ViewChild } from '@angular/core';
import { IPhotos } from 'app/shared/model/photos.model';
import { PhotoService } from './photo.service';
import { NgxGalleryImage, NgxGalleryComponent, INgxGalleryOptions } from 'ngx-gallery';
import * as _ from 'lodash';
import { ConfirmDialogModalService } from 'app/core/confirm-dialog/confirm-dialog-modal.service';
@Component({
    selector: 'idb-photo-panel',
    templateUrl: 'photo-panel.component.html',
    styleUrls: ['./photo-management.scss']
})
export class PhotoPanelComponent implements OnInit {
    photoList: IPhotos[] = [];
    galleryOptions: INgxGalleryOptions[] = [
        {
            image: false,
            thumbnails: false,
            previewCloseOnClick: true,
            previewCloseOnEsc: true,
            previewRotate: true,
            previewFullscreen: true,
            previewKeyboardNavigation: true,
            previewSwipe: true,
            previewZoom: true,
            width: '0px',
            height: '0px'
        }
    ];
    galleryImages: NgxGalleryImage[] = [];
    @ViewChild('gallery') gallery: NgxGalleryComponent;
    constructor(private confirmDialogModalService: ConfirmDialogModalService, private photoService: PhotoService) {}
    ngOnInit() {
        this.loadPhotoList();
    }

    async loadPhotoList() {
        const photoList = await this.photoService.queryPhotoList().toPromise();
        if (photoList) {
            this.photoList = photoList;
            this.setGalleryImages();
        }
    }

    setGalleryImages() {
        this.galleryImages = [];
        this.galleryImages = this.photoList.map(photo => {
            return {
                big: photo.image
            };
        });
    }
    openPhotoGallery(event: any, index: number) {
        if (event.target.id === 'photoSettings') {
            event.preventDefault();
            event.stopPropagation();
        } else {
            this.gallery.openPreview(index);
        }
    }

    setAsProfile(photo: IPhotos) {
        _.forEach(this.photoList, (value, key) => {
            value.isProfilePicture = value.id === photo.id ? true : false;
        });
    }

    removePhoto(photo: IPhotos) {
        this.confirmDialogModalService
            .openConfirmDialog('Are you sure to delete this photo ?')
            .afterClosed()
            .subscribe(res => {
                if (res) {
                    const removedPhtoto = _.remove(this.photoList, { id: photo.id });
                    if (removedPhtoto) {
                        this.setGalleryImages();
                        // Re-Order Image
                        _.forEach(this.photoList, (value, key) => {
                            value.orderInProfile = key + 1;
                        });
                    }
                }
            });
    }
}
