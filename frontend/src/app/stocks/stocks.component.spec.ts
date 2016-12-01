import { Observable } from 'rxjs/Rx';
import { StockService } from './../services/stock.service';
import { Stock } from './../models/stock';
import { HttpModule } from '@angular/http';
import { Logger } from 'angular2-logger/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StocksComponent } from './stocks.component';

const fakeStocks = [
  new Stock(1, 'US0378331005', 'AAPL', 'Apple', new Date('2016-11-25T21:50:05.000+0100')),
  new Stock(2, 'US02079K3059', 'GOOGL', 'Google', new Date('2016-11-25T21:50:05.000+0100')),
  new Stock(3, 'US5949181045', 'MSŒFT', 'Microsoft', new Date('2016-11-25T21:50:05.000+0100'))
];

class MockLogger {
  debug(str) { }
}

class MockStockService {
  public getAllStocks(): Observable<Stock[]> {
    return Observable.of(fakeStocks);
  }
}

describe('StocksComponent', () => {
  let component: StocksComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StocksComponent],
      providers: [
        { provide: Logger, useClass: MockLogger },
      ],
      imports: [HttpModule]
    });

    TestBed.overrideComponent(StocksComponent, {
      set: {
        providers: [{ provide: StockService, useClass: MockStockService }]
      }
    });

    const fixture: ComponentFixture<StocksComponent> = TestBed.createComponent(StocksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should have a defined component', () => {
    expect(component).toBeDefined();
  });

  it('should init stocks', () => {
    component.initStocks();
    expect(component.stocks).toEqual(fakeStocks);
  });

  it('should add new stock', () => {
    let stock: Stock = new Stock(4, 'FR1234567890', 'NEW', 'new', new Date('2016-11-26T21:50:05.000+0100'));
    let expectedStocks = fakeStocks;
    expectedStocks.push(stock);

    expect(component.stocks).toEqual(fakeStocks);
    component.addStock(stock);
    expect(component.stocks).toEqual(expectedStocks);
  });

});
