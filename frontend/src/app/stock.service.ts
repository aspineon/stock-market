import { Observable } from 'rxjs/Rx';
import { Http, Response } from '@angular/http';
import { Injectable } from '@angular/core';
import { Logger } from 'angular2-logger/core';

import { Stock } from './models/stock';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class StockService {

    private stocksUrl = 'http://localhost:8090/api/stocks';

    constructor(private log: Logger, private http: Http) {
    }

    public getAll(): Observable<Stock[]> {
        this.log.debug('StockService.getAll()');

        return this.http.get(this.stocksUrl)
            .map((res: Response) => res.json())
            .map((stocks: Array<any>) => {
                let result: Array<Stock> = [];
                if (stocks) {
                    stocks.forEach((stock) => {
                        result.push(new Stock(stock.id, stock.isin, stock.code, stock.name));
                    });
                }
                return result;
            })
            .catch(this.handleError);
    }

    private handleError(error: Response) {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }
}
