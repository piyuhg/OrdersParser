# OrdersParser

Console based spring boot application Jar used for parsing orders from csv and json files in a multithreaded approach

Command to build: mvn clean install

Steps to run
1. Place the csv and json file at same location as generated jars (orders_parser.jar) location
2. example file names: orders1.csv orders2.json
3. Command to run:
  java -jar orders_parser.jar orders1.csv orders2.json
  
# Input data

# CSV file.
Columns:

Order ID, amount, currency, comment

Example:

1,100,USD,order payment

2,123,EUR,order payment


## JSON file.
Example:

{&quot;orderId&quot;:3, &quot;amount&quot;:1.23, &quot;currency&quot;: &quot;USD&quot;, &quot;comment&quot;: &quot;order payment&quot;}

{&quot;orderId&quot;:4, &quot;amount&quot;:1.24, &quot;currency&quot;: &quot;EUR&quot;, &quot;comment&quot;: &quot;order payment&quot;}
