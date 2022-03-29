package atp.auto.wallet.fn;

import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.oraclecloud.function.OciFunction;
import io.micronaut.oraclecloud.core.TenancyIdProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.sql.DataSource;

@Singleton
public class Function extends OciFunction {
    @Inject
    TenancyIdProvider tenantIdProvider;

    @Inject
    DataSource dataSource;

    @ReflectiveAccess
    public String handleRequest() {
        String tenancyId = tenantIdProvider.getTenancyId();
        return "Your tenancy is: " + tenancyId;
    }
}
