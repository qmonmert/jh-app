import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JhipsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BookDetailComponent } from '../../../../../../main/webapp/app/entities/book/book-detail.component';
import { BookService } from '../../../../../../main/webapp/app/entities/book/book.service';
import { Book } from '../../../../../../main/webapp/app/entities/book/book.model';

describe('Component Tests', () => {

    describe('Book Management Detail Component', () => {
        let comp: BookDetailComponent;
        let fixture: ComponentFixture<BookDetailComponent>;
        let service: BookService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTestModule],
                declarations: [BookDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BookService,
                    EventManager
                ]
            }).overrideComponent(BookDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BookDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Book(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.book).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
