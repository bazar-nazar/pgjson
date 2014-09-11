# PGJson #

### Hibernate extension for PostgreSQL json type support ###

This projects provide some classes to simplify work with Postgres json data type in hibernate. It includes extended postgres dialect and objects for type def support. This small library allows to store in json field both HashMap(with any nesting level) and custom objects. Also it supports scheme validation and update

### Requirements ###

Library expects that in your project you have following dependencies
* hibernate core(4.3 or higher)
* jackson (1.9.13) want to add opportunity to support custom converter
* PostgreSQL driver(9.2 or higher)

### How to use ###
* include library into your project
```xml
        <dependency>
            <groupId>com.bazarnazar</groupId>
            <artifactId>pgjson</artifactId>
            <version>1.1</version>
        </dependency>
```
* set com.bazarnazar.pgjson.JsonPostgreSQLDialect as dialect for hibernate
* if you want to store custom object
    * create object that extends com.bazarnazar.pgjson.PGJsonObject
    * add this object as type in @Type to your @Entity and your annotate field or getter for this type where object is stored
* if you want to store HashMap
    * simply add com.bazarnazar.pgjson.JsonMapType as type in @Type to your @Entity and your annotate field or getter for this type where Hash map is stored

### WARNING!!! ####
* If you have native queries in your project, be sure that you adding entity for this query
* Be careful while editing or passing object for json field. Since it's not immutable changing data directly in this object, can change data in database. It's strongly recommended to create copies

