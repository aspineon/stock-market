# stock-market
Stock market application which aims to test and learn Angular 2 in frontend with REST API in backend.

Technical stack:
- Angular 2 (frontend) Not yet implemented
- Spring Boot 1.4.1 (backend)

Run backend with:
```bash
git clone https://github.com/sbouclier/stock-market.git
cd stock-market/backend
mvn spring-boot:run
```

Go to http://localhost:8090/api/stocks, you should see:
```json
[
  {
    "id": 1,
    "isin": "US0378331005",
    "code": "AAPL",
    "name": "Apple"
  },
  {
    "id": 2,
    "isin": "US02079K3059",
    "code": "GOOGL",
    "name": "Google"
  },
  {
    "id": 3,
    "isin": "US5949181045",
    "code": "MSFT",
    "name": "Microsoft"
  }
]
```