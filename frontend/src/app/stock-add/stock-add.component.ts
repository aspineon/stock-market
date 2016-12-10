import { Stock } from './../models/stock';
import { StockService } from './../services/stock.service';
import { Logger } from 'angular2-logger/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-stock-add',
  templateUrl: './stock-add.component.html',
  styleUrls: ['./stock-add.component.css'],
  providers: [StockService]
})
export class StockAddComponent {

  @Output() onStockAdded = new EventEmitter<Stock>();

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
    this.log.debug('StockAddComponent.addStock()', this.stockForm.value);

    this.stockService.addStock(this.stockForm.value).subscribe(
      res => {
        this.log.debug('StockAddComponent Stock added', res);
        this.onStockAdded.emit(res);
      },
      err => {
        this.log.error('StockAddComponent Can\'t add stock', err);
      });
  }
}
