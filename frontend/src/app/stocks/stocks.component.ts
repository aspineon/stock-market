import { Component, OnInit } from '@angular/core';

import { StockService } from '../stock.service';

import { Observable } from 'rxjs/Rx';

@Component( {
    selector: 'app-stocks',
    templateUrl: './stocks.component.html',
    styleUrls: ['./stocks.component.css'],
    providers: [StockService]
})
export class StocksComponent implements OnInit {

    private stocks: Object[];

    constructor( private stockService: StockService ) { }

    ngOnInit() {
        this.stockService.getAll()
            .subscribe(( data: Object[] ) => { this.stocks = data },
            error => console.log( "err " + error ),
            () => console.log( 'Get all stocks complete' ) );
    }
}