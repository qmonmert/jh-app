import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { StravaActivity } from './strava-activity.model';
import { StravaActivityPopupService } from './strava-activity-popup.service';
import { StravaActivityService } from './strava-activity.service';

@Component({
    selector: 'jhi-strava-activity-dialog',
    templateUrl: './strava-activity-dialog.component.html'
})
export class StravaActivityDialogComponent implements OnInit {

    stravaActivity: StravaActivity;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private stravaActivityService: StravaActivityService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['stravaActivity']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.stravaActivity.id !== undefined) {
            this.stravaActivityService.update(this.stravaActivity)
                .subscribe((res: StravaActivity) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.stravaActivityService.create(this.stravaActivity)
                .subscribe((res: StravaActivity) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: StravaActivity) {
        this.eventManager.broadcast({ name: 'stravaActivityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-strava-activity-popup',
    template: ''
})
export class StravaActivityPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stravaActivityPopupService: StravaActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.stravaActivityPopupService
                    .open(StravaActivityDialogComponent, params['id']);
            } else {
                this.modalRef = this.stravaActivityPopupService
                    .open(StravaActivityDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
