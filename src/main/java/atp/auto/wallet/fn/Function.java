package atp.auto.wallet.fn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.BootstrapContextCompatible;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.oraclecloud.function.OciFunction;
import io.micronaut.oraclecloud.core.TenancyIdProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Singleton
public class Function extends OciFunction {
    @Inject
    DataSource dataSource;

    @ReflectiveAccess
    public String handleRequest() throws SQLException, JsonProcessingException {
        Connection conn = dataSource.getConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select id, first_name, last_name from users");
        return new ObjectMapper().writeValueAsString(convertResultSetToList(resultSet));
    }

    private List<HashMap<String,Object>> convertResultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        while (rs.next()) {
            HashMap<String,Object> row = new HashMap<String, Object>(columns);
            for(int i=1; i<=columns; ++i) {
                row.put(md.getColumnName(i),rs.getObject(i));
            }
            list.add(row);
        }
        return list;
    }

}
