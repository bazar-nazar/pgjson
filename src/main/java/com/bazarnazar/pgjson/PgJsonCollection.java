package com.bazarnazar.pgjson;

import org.codehaus.jackson.type.TypeReference;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public abstract class PgJsonCollection extends PGJsonObject {


    /**
     * The type reference of collection returned by <tt>nullSafeGet()</tt>.
     * override this method with
     * example:
     *
     * <pre>
     * protected TypeReference getReturnedTypeReference() {
     *      return new TypeReference&lt;Map&lt;String, SomeType&gt;&gt;() {};
     * }
     * </pre>
     * where SomeType is needed type
     *
     * @return TypeReference
     */
    protected abstract TypeReference getReturnedTypeReference();

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        if (resultSet.getObject(names[0]) == null) {
            return null;
        }
        PGobject pGobject = (PGobject) resultSet.getObject(names[0]);
        Object jsonObject = null;
        try {
            jsonObject = objectMapper.readValue(pGobject.getValue(), getReturnedTypeReference());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        Object copy = null;
        try {
            copy = objectMapper.readValue(objectMapper.writeValueAsBytes(o), this.getReturnedTypeReference());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return copy;
    }
}
