export class Stock {

    public static toObject(json: any): Stock {
        return new Stock(json.id, json.isin, json.code, json.name, new Date(json.createdDate));
    }

    constructor(
        public id: number,
        public isin: string,
        public code: string,
        public name: string,
        public createdDate: Date
        ) {}
}
