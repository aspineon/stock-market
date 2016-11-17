import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import {LOG_LOGGER_PROVIDERS} from "angular2-logger/core";

import { AppComponent } from './app.component';
import { StocksComponent } from './stocks/stocks.component';

@NgModule( {
    declarations: [
        AppComponent,
        StocksComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule
    ],
    providers: [LOG_LOGGER_PROVIDERS],
    bootstrap: [AppComponent]
})
export class AppModule { }
