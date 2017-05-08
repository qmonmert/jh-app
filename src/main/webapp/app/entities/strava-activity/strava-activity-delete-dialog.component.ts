import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { StravaActivity } from './strava-activity.model';
import { StravaActivityPopupService } from './strava-activity-popup.service';
import { StravaActivityService } from './strava-activity.service';

@Component({
    selector: 'jhi-strava-activity-delete-dialog',
    templateUrl: './strava-activity-delete-dialog.component.html'
})
export class StravaActivityDeleteDialogComponent {

    stravaActivity: StravaActivity;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private stravaActivityService: StravaActivityService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['stravaActivity']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stravaActivityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'stravaActivityListModification',
                content: 'Deleted an stravaActivity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-strava-activity-delete-popup',
    template: ''
})
export class StravaActivityDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stravaActivityPopupService: StravaActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.stravaActivityPopupService
                .open(StravaActivityDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
