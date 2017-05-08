import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { StravaActivityDetailComponent } from '../../../../../../main/webapp/app/entities/strava-activity/strava-activity-detail.component';
import { StravaActivityService } from '../../../../../../main/webapp/app/entities/strava-activity/strava-activity.service';
import { StravaActivity } from '../../../../../../main/webapp/app/entities/strava-activity/strava-activity.model';

describe('Component Tests', () => {

    describe('StravaActivity Management Detail Component', () => {
        let comp: StravaActivityDetailComponent;
        let fixture: ComponentFixture<StravaActivityDetailComponent>;
        let service: StravaActivityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [StravaActivityDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    StravaActivityService,
                    EventManager
                ]
            }).overrideComponent(StravaActivityDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StravaActivityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StravaActivityService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new StravaActivity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.stravaActivity).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
