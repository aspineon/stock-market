#stock-market
Stock market application which aims to test and learn Angular 2 in frontend with REST API in backend.

#Technical stack
Frontend
- Angular 2.1.0

Backend
- Spring Boot 1.4.1
- Spring 4.3.3
- Hibernate 5.0.11

#Installation
```bash
git clone https://github.com/sbouclier/stock-market.git
```
Install MySQL and create a database named 'stockmarket'. Configuration of database can be changed on file backend/src/main/resources/application.properties.

#Start-up
Run backend with (you need maven installed):
```bash
cd stock-market
mvn install
cd backend
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

Run frontend with (you need node.js installed):
```bash
cd stock-market/frontend
npm install
ng serve
```