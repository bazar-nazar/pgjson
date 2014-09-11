package com.bazarnazar.pgjson;

/**
 * Created by Bazar on 27.08.14.
 */
public class JsonMapType extends PGJsonObject {

    @Override
    public Class returnedClass() {
        return Object.class;
    }

}
