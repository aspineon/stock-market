import { Http, Response } from '@angular/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs/Rx';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class StockService {

    constructor( private http: Http ) {
    }

    // TODO: Observable<Stock[]>
    public getAll(): Observable<Object> {
        console.log( "getAll()" );
        return this.http.get( "http://localhost:8090/api/stocks" )
            .map(( res: Response ) => res.json() )
            .do( function( data ) { console.log( "data received : " + data ) })
            .catch( this.handleError );
    }

    private handleError( error: Response ) {
        console.error( error );
        return Observable.throw( error.json().error || 'Server error' );
    }
}