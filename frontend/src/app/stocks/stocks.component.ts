import { Stock } from './../models/stock';
import { Logger } from 'angular2-logger/core';
import { Component, OnInit } from '@angular/core';

import { StockService } from '../services/stock.service';

@Component({
    selector: 'app-stocks',
    templateUrl: './stocks.component.html',
    styleUrls: ['./stocks.component.css'],
    providers: [StockService]
})
export class StocksComponent implements OnInit {

    public stocks: Stock[] = new Array();

    constructor(private log: Logger, private stockService: StockService) {
        this.log.debug('StocksComponent(log=' + log.constructor.name + ', stockService=' + stockService.constructor.name + ')');
    }

    ngOnInit() {
        this.log.debug('StocksComponent.ngOnInit()');
        this.initStocks();
    }

    public initStocks() {
        this.log.debug('StocksComponent.initStocks()');
        this.stockService.getAllStocks()
            .subscribe((data: Stock[]) => { this.stocks = data; },
            error => this.log.error('StocksComponent error', error),
            () => this.log.debug('StocksComponent : get all stocks complete'));
    }

    public addStock(stock: Stock) {
        this.log.debug('StocksComponent.addStock()', stock);
        this.stocks.push(stock);
    }
}
