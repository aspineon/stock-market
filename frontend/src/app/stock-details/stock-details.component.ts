import { StockService } from './../services/stock.service';
import { Logger } from 'angular2-logger/core';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-stock-details',
  templateUrl: './stock-details.component.html',
  styleUrls: ['./stock-details.component.css'],
  providers: [StockService]
})
export class StockDetailsComponent implements OnInit {

  constructor(private log: Logger, private stockService: StockService) {
    this.log.debug('StockDetailsComponent(log=' + log.constructor.name + ', stockService=' + stockService.constructor.name + ')');
  }

  ngOnInit() {
    this.log.debug('StockDetailsComponent.ngOnInit()');
  }

}
