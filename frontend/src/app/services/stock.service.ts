import { Observable } from 'rxjs/Rx';
import { Headers, Http, Response, RequestOptions } from '@angular/http';
import { Injectable } from '@angular/core';
import { Logger } from 'angular2-logger/core';

import { Stock } from '../models/stock';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class StockService {

    private stocksUrl = 'http://localhost:8090/api/stocks';

    constructor(private log: Logger, private http: Http) {
    }

    public getAllStocks(): Observable<Stock[]> {
        this.log.debug('StockService.getAllStocks()');

        const _this = this;
        return this.http.get(this.stocksUrl)
            .map((res: Response) => res.json())
            .map((stocks: Array<any>) => {
                let result: Array<Stock> = [];
                if (stocks) {
                    stocks.forEach((stock) => {
                        result.push(new Stock(stock.id, stock.isin, stock.code, stock.name, stock.createdDate));
                    });
                }
                return result;
            })
            .do(function (data) { _this.log.debug('StockService.getAllStocks data received : ', data); })
            .catch(this.handleError);
    }

    public getStockByIsin(isin: string): Observable<Stock> {
        this.log.debug('StockService.getStockByIsin(isin=' + isin + ')');

        const _this = this;
        return this.http.get(this.stocksUrl + '/' + isin)
            .map((res: Response) => res.json())
            .map((stock: any) => {
                let result: Stock = undefined;
                if (stock) {
                    result = new Stock(stock.id, stock.isin, stock.code, stock.name, stock.createdDate);
                }
                return result;
            })
            .do(function (data) { _this.log.debug('StockService.getStockByIsin data received : ', data); })
            .catch(this.handleError);
    }

    public addStock(body: Object):Observable<Response> {
        this.log.debug('StockService.addStock()');

        let bodyString = JSON.stringify(body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        const _this = this;
        return this.http.post(this.stocksUrl, bodyString, options)
            .flatMap((res: Response) => {
                const location = res.headers.get('Location');
                _this.log.info('StockService.addStock Stock added location : ', location);
                return _this.http.get(location);
            }).map((res: Response) => {
                // TODO cast
                _this.log.info('StockService.addStock retrieve stock by location : ', res);
                return res.json();
            })
            .catch(this.handleError);
    }

    private handleError(error: Response) {
        console.error('StockService.handleError()', error);
        return Observable.throw(error.json() || 'StockService : Server error');
    }
}
