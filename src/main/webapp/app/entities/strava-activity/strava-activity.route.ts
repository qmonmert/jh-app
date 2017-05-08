import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { StravaActivityComponent } from './strava-activity.component';
import { StravaActivityDetailComponent } from './strava-activity-detail.component';
import { StravaActivityPopupComponent } from './strava-activity-dialog.component';
import { StravaActivityDeletePopupComponent } from './strava-activity-delete-dialog.component';

import { Principal } from '../../shared';

export const stravaActivityRoute: Routes = [
  {
    path: 'strava-activity',
    component: StravaActivityComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'jhipsterApp.stravaActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'strava-activity/:id',
    component: StravaActivityDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'jhipsterApp.stravaActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const stravaActivityPopupRoute: Routes = [
  {
    path: 'strava-activity-new',
    component: StravaActivityPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'jhipsterApp.stravaActivity.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'strava-activity/:id/edit',
    component: StravaActivityPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'jhipsterApp.stravaActivity.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'strava-activity/:id/delete',
    component: StravaActivityDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'jhipsterApp.stravaActivity.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
