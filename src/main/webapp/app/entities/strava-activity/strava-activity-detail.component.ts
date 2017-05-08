import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { StravaActivity } from './strava-activity.model';
import { StravaActivityService } from './strava-activity.service';

@Component({
    selector: 'jhi-strava-activity-detail',
    templateUrl: './strava-activity-detail.component.html'
})
export class StravaActivityDetailComponent implements OnInit, OnDestroy {

    stravaActivity: StravaActivity;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private stravaActivityService: StravaActivityService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['stravaActivity']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStravaActivities();
    }

    load(id) {
        this.stravaActivityService.find(id).subscribe((stravaActivity) => {
            this.stravaActivity = stravaActivity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStravaActivities() {
        this.eventSubscriber = this.eventManager.subscribe('stravaActivityListModification', (response) => this.load(this.stravaActivity.id));
    }
}
