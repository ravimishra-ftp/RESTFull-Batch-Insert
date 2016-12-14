# RESTFull-Batch-Insert

This project is a RESTFull Black Box logic project, which has a REST API to insert record in DB using Hibernate batch.
All this need is table name and record to be inserted in JSON format.

URL to Insert Data: http://127.0.0.1:8990/MyApp/api/insertRows/TABLE_NAME
-------------------
Sample Input JSON
-------------------
{
  "entityData": {
    "columnList": [
      {
        "key": "column_name",
        "value": "data_to_be_inserted"
      },
      {
        "key": "column_name",
        "value": "data_to_be_inserted"
      },
      {
        "key": "column_name",
        "value": "data_to_be_inserted"
      }
    ]
  }
}

URL to Retrieve Data
---------------------
http://127.0.0.1:8990/MyApp/api/fetchRows/TABLE_NAME

And it will get all record from table in JSON format.
