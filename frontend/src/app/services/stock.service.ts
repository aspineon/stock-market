import { Observable } from 'rxjs/Rx';
import { Headers, Http, Response, RequestOptions, URLSearchParams } from '@angular/http';
import { Injectable } from '@angular/core';
import { Logger } from 'angular2-logger/core';

import { Stock } from '../models/stock';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class StockService {

    // TODO export urls
    private stocksUrl = 'http://localhost:8090/api/stocks';
    private loadHistoricalDataUrl = 'http://localhost:8090/api/stocks/historical/load';

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
                        result.push(new Stock(stock.id, stock.isin, stock.code, stock.name, new Date(stock.createdDate)));
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
            .map((stockJson: any) => Stock.toObject(stockJson))
            .do(function (data) { _this.log.debug('StockService.getStockByIsin data received : ', data); })
            .catch(this.handleError);
    }

    public addStock(body: Object): Observable<Stock> {
        this.log.debug('StockService.addStock(...) body=', body);

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
                _this.log.debug('StockService.addStock retrieve stock by location : ', res);
                return Stock.toObject(res.json());
            })
            .catch(this.handleError);
    }

    public loadHistoricalData(isin: string, startDate: any, endDate: any): Observable<any> {
        this.log.debug('StockService.loadHistoricalData(...) body=', isin, startDate, endDate);

        let params = new URLSearchParams();
        params.set('isin', isin);
        params.set('startDate', startDate);
        params.set('endDate', endDate);

        //let bodyString = JSON.stringify(body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({
            headers: headers,
            search: params
        });

        const _this = this;
        //return this.http.post(this.loadHistoricalDataUrl, bodyString, options)
        return this.http.get(this.loadHistoricalDataUrl, options)
            .map((res: Response) => {
                _this.log.debug('StockService.loadHistoricalData count historical data loaded : ', res);
                return res.json();
            })
            .catch(this.handleError);
    }

    private handleError(error: Response) {
        this.log.error('StockService.handleError()', error);
        return Observable.throw(error.json() || 'StockService : Server error');
    }
}
