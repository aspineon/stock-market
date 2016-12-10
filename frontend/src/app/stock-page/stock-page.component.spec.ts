import { MockLogger } from './../mock-logger';
import { HttpModule } from '@angular/http';
import { Logger } from 'angular2-logger/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { RouterModule } from '@angular/router';
import { StockListComponent } from './../stock-list/stock-list.component';
import { StockAddComponent } from './../stock-add/stock-add.component';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { StockPageComponent } from './stock-page.component';

describe('StockPageComponent', () => {
  let component: StockPageComponent;
  let fixture: ComponentFixture<StockPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StockPageComponent, StockAddComponent, StockListComponent ],
      providers: [
        { provide: Logger, useClass: MockLogger },
      ],
      imports: [ HttpModule, RouterModule, RouterTestingModule, FormsModule, ReactiveFormsModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StockPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
