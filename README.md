# Kafe application

- This application was made to allow any user to see products and its prices
- This app allows the administrator to save, update, delete and fetch products from a database.

### Run
- To run locally just clone the project,
- Create a database with the name you want
- Update the url in the application.properties file

### Api

- Login:
```
  curl --location --request POST 'localhost:8080/api/login' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "username":"mariafmaceab",
  "password":"sasha"
  }'
```
As a response from this endpoint you will get something like this
```json
{
  "access": "",
  "refresh": ""
}
```
For the rest of the queries you must add a Header like this:

```
"Authorization":"Bearer {access_token_obtained_below}"
```

When the access token expires you should make this query
```
curl --location --request GET 'localhost:8080/api/token/refresh' \
--header 'Authorization: Bearer {the_refresh_token_obtained_in_login}'
```

- The categories allowed for the products are RUG, BLANKET, CUSHION, HUGGER

- Save a product:
```
  curl --location --request POST 'localhost:8080/api/product/save' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "name":"String",
  "category":"String",
  "price_by_cm":BigDecimal,
  "description":"String",
  "height":BigDecimal,
  "width":BigDecimal,
  "image_link":"String"}
```

- Get a product by id
```
curl --location --request GET 'localhost:8080/api/product/{id}'
```

- Update product
```
curl --location --request PUT 'localhost:8080/api/product/{id}' \
--header 'Content-Type: application/json' \
--data-raw '{
"name":"String",
"category":"String",
"price_by_cm":BigDecimal,
"description":"String",
"height":BigDecimal,
"width":BigDecimal,
"image_link":"String"
}'
```

- Delete a product
```
curl --location --request DELETE 'localhost:8080/api/product/{id}'
```

- Get all the products
```
curl --location --request GET 'localhost:8080/api/product/products'
```

- Get all the product of the same category
```
curl --location --request GET 'localhost:8080/api/product/category/{category}'
```

- Get all product in a price range
```
curl --location --request GET 'localhost:8080/api/product/prices/{max}/{min}'
```