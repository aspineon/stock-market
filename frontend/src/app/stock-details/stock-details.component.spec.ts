import { MockLogger } from './../mock-logger';
import { RouterTestingModule } from '@angular/router/testing';
import { RouterModule } from '@angular/router';
import { HttpModule } from '@angular/http';
import { Logger } from 'angular2-logger/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { StockDetailsComponent } from './stock-details.component';

describe('StockDetailsComponent', () => {
    let component: StockDetailsComponent;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [StockDetailsComponent],
            providers: [
                { provide: Logger, useClass: MockLogger }
            ],
            imports: [HttpModule, RouterModule, RouterTestingModule]
        });

        const fixture: ComponentFixture<StockDetailsComponent> = TestBed.createComponent(StockDetailsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should have a defined component', () => {
        expect(component).toBeDefined();
    });

});
