import { Stock } from './../models/stock';
import { StockService } from './../services/stock.service';
import { Logger } from 'angular2-logger/core';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-stock-details',
    templateUrl: './stock-details.component.html',
    styleUrls: ['./stock-details.component.css'],
    providers: [StockService]
})
export class StockDetailsComponent implements OnInit, OnDestroy {

    paramIsin: string;
    stock: Stock;
    private routeParams: any;

    constructor(private log: Logger, private route: ActivatedRoute, private stockService: StockService) {
        this.log.debug('StockDetailsComponent(log=' + log.constructor.name
            + ', route=' + route
            + ', stockService=' + stockService.constructor.name + ')');
    }

    ngOnInit() {
        this.log.debug('StockDetailsComponent.ngOnInit()');

        this.routeParams = this.route.params.subscribe(params => {
            this.paramIsin = params['isin'];

            this.stockService.getStockByIsin(this.paramIsin).subscribe((data: Stock) => {
                this.stock = data;
            }, error => this.log.error('StockDetailsComponent error', error),
                () => this.log.debug('StockDetailsComponent : retrieve stock complete'));
        });
    }

    ngOnDestroy() {
        this.log.debug('StockDetailsComponent.ngOnDestroy()');
        this.routeParams.unsubscribe();
    }
}
