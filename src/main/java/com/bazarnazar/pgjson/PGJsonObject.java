package com.bazarnazar.pgjson;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by Bazar on 26.08.14.
 */
public class PGJsonObject implements UserType {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class returnedClass() {
        return this.getClass();
    }

    @Override
    public boolean equals(Object o, Object o2) throws HibernateException {
        if (o == null) {
            return o2 == null;
        }
        return o.equals(o2);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        if (resultSet.getObject(names[0]) == null) {
            return null;
        }
        PGobject pGobject = (PGobject) resultSet.getObject(names[0]);
        Object jsonObject = null;
        try {
            jsonObject = objectMapper.readValue(pGobject.getValue(), this.returnedClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.JAVA_OBJECT);
            return;
        }
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PGobject pGobject = new PGobject();
        pGobject.setType("json");
        pGobject.setValue(jsonString);
        preparedStatement.setObject(index, pGobject);
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        Object copy = null;
        try {
            copy = objectMapper.readValue(objectMapper.writeValueAsBytes(o), this.returnedClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return copy;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) this.deepCopy(o);
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return this.deepCopy(serializable);
    }

    @Override
    public Object replace(Object o, Object o2, Object o3) throws HibernateException {
        return this.deepCopy(o);
    }
}
