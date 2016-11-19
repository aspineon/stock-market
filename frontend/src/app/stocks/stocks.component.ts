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

    private stocks: Stock[];

    constructor(private log: Logger, private stockService: StockService) { }

    ngOnInit() {
        this.log.debug('StocksComponent.ngOnInit()');

        this.stockService.getAll()
            .subscribe((data: Stock[]) => { this.stocks = data; },
            error => this.log.debug('err ' + error),
            () => this.log.debug('Get all stocks complete'));
    }
}
