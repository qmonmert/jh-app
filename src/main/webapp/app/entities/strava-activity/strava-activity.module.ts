import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import {
    StravaActivityService,
    StravaActivityPopupService,
    StravaActivityComponent,
    StravaActivityDetailComponent,
    StravaActivityDialogComponent,
    StravaActivityPopupComponent,
    StravaActivityDeletePopupComponent,
    StravaActivityDeleteDialogComponent,
    stravaActivityRoute,
    stravaActivityPopupRoute,
} from './';

import { ConvertDistancePipe } from './convert-distance.pipe';

const ENTITY_STATES = [
    ...stravaActivityRoute,
    ...stravaActivityPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        StravaActivityComponent,
        StravaActivityDetailComponent,
        StravaActivityDialogComponent,
        StravaActivityDeleteDialogComponent,
        StravaActivityPopupComponent,
        StravaActivityDeletePopupComponent,
ConvertDistancePipe
    ],
    entryComponents: [
        StravaActivityComponent,
        StravaActivityDialogComponent,
        StravaActivityPopupComponent,
        StravaActivityDeleteDialogComponent,
        StravaActivityDeletePopupComponent,
    ],
    providers: [
        StravaActivityService,
        StravaActivityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterStravaActivityModule {}
