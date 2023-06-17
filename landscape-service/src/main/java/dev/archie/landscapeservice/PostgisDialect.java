package dev.archie.landscapeservice;

import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.spatial.dialect.postgis.PostgisPG10Dialect;
import org.hibernate.type.StandardBasicTypes;

public class PostgisDialect extends PostgisPG10Dialect {
    public PostgisDialect() {
        super();
        registerFunction("ST_Area", new StandardSQLFunction("ST_Area", StandardBasicTypes.DOUBLE));
    }
}
