import { HttpModule, Response, Http, BaseRequestOptions, RequestMethod, ResponseOptions, Headers } from '@angular/http';
import { TestBed, fakeAsync, inject } from '@angular/core/testing';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { Logger } from 'angular2-logger/core';
import { Stock } from './../models/stock';
import { StockService } from './stock.service';

const mockGetAllStocksHttpResponse = [
  {
    'id': 1,
    'isin': 'US0378331005',
    'code': 'AAPL',
    'name': 'Apple',
    'createdDate': '2016-11-25T21:50:05.000+0100'
  },
  {
    'id': 2,
    'isin': 'US02079K3059',
    'code': 'GOOGL',
    'name': 'Google',
    'createdDate': '2016-11-25T21:50:05.000+0100'
  },
  {
    'id': 3,
    'isin': 'US5949181045',
    'code': 'MSFT',
    'name': 'Microsoft',
    'createdDate': '2016-11-25T21:50:05.000+0100'
  }
];

const mockGetStockHttpResponse = {
  'id': 1,
  'isin': 'US0378331005',
  'code': 'AAPL',
  'name': 'Apple',
  'createdDate': '2016-11-25T21:50:05.000+0100'
};

describe('Service: Stock', () => {

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule],
      providers: [MockBackend, BaseRequestOptions,
        {
          provide: Http,
          useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
            return new Http(backendInstance, defaultOptions);
          },
          deps: [MockBackend, BaseRequestOptions]
        }, StockService, Logger]
    });
  });

  it('should retrieve all stocks', inject(
    [MockBackend, StockService],
    fakeAsync((backend: MockBackend, service: StockService) => {

      // expected results
      const expectedUrl = 'http://localhost:8090/api/stocks';

      const expectedResponse = [
        new Stock(1, 'US0378331005', 'AAPL', 'Apple', new Date('2016-11-25T21:50:05.000+0100')),
        new Stock(2, 'US02079K3059', 'GOOGL', 'Google', new Date('2016-11-25T21:50:05.000+0100')),
        new Stock(3, 'US5949181045', 'MSFT', 'Microsoft', new Date('2016-11-25T21:50:05.000+0100'))
      ];

      // mock http response
      backend.connections.subscribe((connection: MockConnection) => {
        expect(connection.request.method).toBe(RequestMethod.Get);
        expect(connection.request.url).toBe(expectedUrl);

        connection.mockRespond(
          new Response(
            new ResponseOptions({ body: mockGetAllStocksHttpResponse })));
      });

      // call service
      service.getAllStocks().subscribe(res => {
        expect(res).toEqual(expectedResponse);
      });
    })
  ));

  it('should retrieve stock by isin', inject(
    [MockBackend, StockService],
    fakeAsync((backend: MockBackend, service: StockService) => {

      // expected results
      const expectedUrl = 'http://localhost:8090/api/stocks/US0378331005';

      const expectedResponse = new Stock(1, 'US0378331005', 'AAPL', 'Apple',
        new Date('2016-11-25T21:50:05.000+0100'));

      // mock http response
      backend.connections.subscribe((connection: MockConnection) => {
        expect(connection.request.method).toBe(RequestMethod.Get);
        expect(connection.request.url).toBe(expectedUrl);

        connection.mockRespond(
          new Response(
            new ResponseOptions({ body: mockGetStockHttpResponse })));
      });

      // call service
      service.getStockByIsin('US0378331005').subscribe(res => {
        expect(res).toEqual(expectedResponse);
      });
    })
  ));

  it('should add stock', inject(
    [MockBackend, StockService],
    fakeAsync((backend: MockBackend, service: StockService) => {

      // expected results
      const expectedUrlPost = 'http://localhost:8090/api/stocks';
      const expectedUrlGetStock = 'http://localhost:8090/api/stocks/FR1234567890';

      const expectedResponse = new Stock(4, 'FR1234567890', 'NEW', 'New', new Date('2016-12-25T21:50:05.000+0100'));

      // mock http response
      backend.connections.subscribe((connection: MockConnection) => {

        if (connection.request.method === RequestMethod.Post) {
          expect(connection.request.url).toBe(expectedUrlPost);
          expect(connection.request.getBody()).toBe(JSON.stringify({ isin: 'FR1234567890', code: 'NEW', name: 'New' }));

          connection.mockRespond(
            new Response(
              new ResponseOptions({ headers: new Headers({ 'Location': 'http://localhost:8090/api/stocks/FR1234567890' }) })));
        } else {
          expect(connection.request.url).toBe(expectedUrlGetStock);

          connection.mockRespond(
            new Response(
              new ResponseOptions({
                body: {
                  id: 4, isin: 'FR1234567890', code: 'NEW', name: 'New', createdDate: '2016-12-25T21:50:05.000+0100'
                }
              }))
          );
        }

      });

      let body = { isin: 'FR1234567890', code: 'NEW', name: 'New' };

      // call service
      service.addStock(body).subscribe(res => {
        expect(res).toEqual(expectedResponse);
      });
    })
  ));
});
