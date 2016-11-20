import { StockService } from './../services/stock.service';
import { Logger } from 'angular2-logger/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { Component } from '@angular/core';

@Component({
  selector: 'app-add-stock',
  templateUrl: './add-stock.component.html',
  styleUrls: ['./add-stock.component.css'],
  providers: [StockService]
})
export class AddStockComponent {

  stockForm: FormGroup;
  isin: FormControl;
  code: FormControl;
  name: FormControl;

  constructor(private log: Logger, private fb: FormBuilder, private stockService: StockService) {

    // controls
    this.isin = new FormControl('', [Validators.required, Validators.minLength(12), Validators.maxLength(12)]);
    this.code = new FormControl('', [Validators.required]);
    this.name = new FormControl('', [Validators.required]);

    // build form
    this.stockForm = fb.group(
      {
        isin: this.isin,
        code: this.code,
        name: this.name
      }
    );
  }

  public addStock() {
    this.log.debug('AddStockComponent.addStock()', this.stockForm.value);

    this.stockService.addStock(this.stockForm.value).subscribe(
      res => {
        this.log.debug('AddStockComponent Stock added', res);
      },
      err => {
        this.log.error('AddStockComponent Can\'t add stock', err);
      });
  }
}
