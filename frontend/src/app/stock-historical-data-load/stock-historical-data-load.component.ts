import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { StockService } from './../services/stock.service';
import { Logger } from 'angular2-logger/core';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-stock-historical-data-load',
  templateUrl: './stock-historical-data-load.component.html',
  styleUrls: ['./stock-historical-data-load.component.css']
})
export class StockHistoricalDataLoadComponent {

  @Input() isin: string;

  loadForm: FormGroup;
  startDate: FormControl;
  endDate: FormControl;

  constructor(private log: Logger, private fb: FormBuilder, private stockService: StockService) {

    // controls
    this.startDate = new FormControl('', [Validators.required]);
    this.endDate = new FormControl('', [Validators.required]);

    // build form
    this.loadForm = fb.group(
      {
        startDate: this.startDate,
        endDate: this.endDate
      }
    );
  }

  public load() {
    let form = this.loadForm.value;
    form.isin = this.isin;

    //form.startDate = '2016-01-01';
    //form.endDate = '2016-03-01';

    this.log.debug('StockHistoricalDataLoadComponent.load()', form);

    this.stockService.loadHistoricalData(form.isin,form.startDate,form.endDate).subscribe(
      res => {
        this.log.debug('StockHistoricalDataLoadComponent historical data loaded', res);
      },
      err => {
        this.log.error('StockHistoricalDataLoadComponent Can\'t load historical data', err);
      });
  }

}
