import { HttpModule } from '@angular/http';
import { Logger } from 'angular2-logger/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { StockDetailsComponent } from './stock-details.component';

class MockLogger {
  debug(str) { }
}

describe('AddStockComponent', () => {
  let component: StockDetailsComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StockDetailsComponent],
      providers: [
        { provide: Logger, useClass: MockLogger },
      ],
      imports: [HttpModule]
    });

    const fixture: ComponentFixture<StockDetailsComponent> = TestBed.createComponent(StockDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should have a defined component', () => {
    expect(component).toBeDefined();
  });

});
