import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { StravaActivity } from './strava-activity.model';
import { StravaActivityService } from './strava-activity.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-strava-activity',
    templateUrl: './strava-activity.component.html'
})
export class StravaActivityComponent implements OnInit, OnDestroy {
stravaActivities: StravaActivity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private stravaActivityService: StravaActivityService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['stravaActivity']);
    }

    loadAll() {
        this.stravaActivityService.query().subscribe(
            (res: Response) => {
                this.stravaActivities = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInStravaActivities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: StravaActivity) {
        return item.id;
    }
    registerChangeInStravaActivities() {
        this.eventSubscriber = this.eventManager.subscribe('stravaActivityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
