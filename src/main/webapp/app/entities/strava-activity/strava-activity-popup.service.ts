import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { StravaActivity } from './strava-activity.model';
import { StravaActivityService } from './strava-activity.service';
@Injectable()
export class StravaActivityPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private stravaActivityService: StravaActivityService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.stravaActivityService.find(id).subscribe((stravaActivity) => {
                this.stravaActivityModalRef(component, stravaActivity);
            });
        } else {
            return this.stravaActivityModalRef(component, new StravaActivity());
        }
    }

    stravaActivityModalRef(component: Component, stravaActivity: StravaActivity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.stravaActivity = stravaActivity;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
