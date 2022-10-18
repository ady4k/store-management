## Store Managament

### Project for the Advanced Object Oriented Programming course.

A project about managing a store company, the products of a store, its employees and different details about the company, such as key people, headquarters of the company and the customers. The main technologies used in the making of the projects are Java and JBDC, connected at a PostgreSQL server for data persistance.

The project includes CRUD operations for almost all the objects modifiable in the database, an audit service which logs every action applied to the database into a specified CSV file. The objects also persist into a local CSV database, data is saved to the database and to the CSV files at every exit of the program. At each start, the objects are loaded into local data structures, ready to be modified. 

More actions include placing orders for products to be added to the shop, transportation simulation based on the real data, information about the inventory of a store, returnals for orders, (presumed Admin role) complete administration over the entire operations, employees and shops. 

The operations are done using an interactive menu in the console, the program's flow works flawlessly and has been tested several times. 

There are several design patterns that have been used, including Builder, Singleton and Factory. 
