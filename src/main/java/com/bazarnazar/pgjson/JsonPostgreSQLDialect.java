package com.bazarnazar.pgjson;

import org.hibernate.dialect.PostgreSQL9Dialect;

import java.sql.Types;

/**
 * Created by Bazar on 26.08.14.
 */
public class JsonPostgreSQLDialect extends PostgreSQL9Dialect {

    public JsonPostgreSQLDialect() {
        super();
        this.registerColumnType(Types.JAVA_OBJECT, "json");
    }
}
