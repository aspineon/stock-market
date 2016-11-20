export class Stock {
    constructor(
        public id: number,
        public isin: string,
        public code: string,
        public name: string,
        public createdDate: Date
        ) {}
}
