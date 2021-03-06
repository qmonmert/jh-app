import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterAuthorModule } from './author/author.module';
import { JhipsterBookModule } from './book/book.module';
import { JhipsterStravaActivityModule } from './strava-activity/strava-activity.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterAuthorModule,
        JhipsterBookModule,
        JhipsterStravaActivityModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterEntityModule {}
