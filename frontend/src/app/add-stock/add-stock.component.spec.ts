import { Observable } from 'rxjs/Rx';
import { Stock } from './../models/stock';
import { HttpModule } from '@angular/http';
import { StockService } from './../services/stock.service';
import { Logger } from 'angular2-logger/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddStockComponent } from './add-stock.component';

class MockLogger {
  debug(str) { }
}

class MockStockService {
  public addStock(body: Object): Observable<Stock> {
    let fakeStock: Stock = new Stock(1, 'US0378331005', 'AAPL', 'Apple', new Date('2016-11-25T21:50:05.000+0100'));
    return Observable.of(fakeStock);
  }
}

describe('AddStockComponent', () => {
  let component: AddStockComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddStockComponent],
      providers: [
        { provide: Logger, useClass: MockLogger },
      ],
      imports: [HttpModule, FormsModule, ReactiveFormsModule]
    });

    TestBed.overrideComponent(AddStockComponent, {
      set: {
        providers: [{ provide: StockService, useClass: MockStockService }]
      }
    });

    const fixture: ComponentFixture<AddStockComponent> = TestBed.createComponent(AddStockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should have a defined component', () => {
    expect(component).toBeDefined();
  });

  it('should emit event when stock is added', () => {
    let expectedEventStock = new Stock(1, 'US0378331005', 'AAPL', 'Apple', new Date('2016-11-25T21:50:05.000+0100'));
    spyOn(component.onStockAdded, 'emit');

    component.addStock();
    expect(component.onStockAdded.emit).toHaveBeenCalledWith(expectedEventStock);
  });

});
