import { Stock } from './../models/stock';
import { Logger } from 'angular2-logger/core';
import { Component, OnInit } from '@angular/core';

import { StockService } from '../services/stock.service';

@Component({
    selector: 'app-stock-list',
    templateUrl: './stock-list.component.html',
    styleUrls: ['./stock-list.component.css'],
    providers: [StockService]
})
export class StockListComponent implements OnInit {

    public stocks: Stock[] = new Array();

    constructor(private log: Logger, private stockService: StockService) {
        this.log.debug('StockListComponent(log=' + log.constructor.name + ', stockService=' + stockService.constructor.name + ')');
    }

    ngOnInit() {
        this.log.debug('StockListComponent.ngOnInit()');
        this.initStocks();
    }

    public initStocks() {
        this.log.debug('StockListComponent.initStocks()');
        this.stockService.getAllStocks()
            .subscribe((data: Stock[]) => { this.stocks = data; },
            error => this.log.error('StockListComponent error', error),
            () => this.log.debug('StockListComponent : get all stocks complete'));
    }

    public addStock(stock: Stock) {
        this.log.debug('StockListComponent.addStock()', stock);
        this.stocks.push(stock);
    }
}
