import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { LOG_LOGGER_PROVIDERS } from 'angular2-logger/core';

import { AppComponent } from './app.component';
import { StockListComponent } from './stock-list/stock-list.component';
import { StockAddComponent } from './stock-add/stock-add.component';
import { StockDetailsComponent } from './stock-details/stock-details.component';
import { HomePageComponent } from './home-page/home-page.component';
import { StockPageComponent } from './stock-page/stock-page.component';
import { StockHistoricalDataLoadComponent } from './stock-historical-data-load/stock-historical-data-load.component';

const routes: Routes = [
    { path: '', component: HomePageComponent },
    { path: 'stock', component: StockPageComponent },
    { path: 'stock/:isin', component: StockDetailsComponent }
];

export const routing = RouterModule.forRoot(routes);

@NgModule({
    declarations: [
        AppComponent,
        StockListComponent,
        StockAddComponent,
        StockDetailsComponent,
        HomePageComponent,
        StockPageComponent,
        StockHistoricalDataLoadComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpModule,
        routing
    ],
    providers: [LOG_LOGGER_PROVIDERS],
    bootstrap: [AppComponent]
})
export class AppModule { }
