package tdl.code.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import tdl.code.services.DataServices.DataService;
import tdl.code.services.DataServices.MySqlService;
import tdl.code.services.HashServices.HashService;
import tdl.code.services.HashServices.Sha1HashService;

@Singleton
public class ConfigModule extends AbstractModule {
    @Override
    protected void configure(){
        bind(DataService.class).to(MySqlService.class);
        bind(HashService.class).to(Sha1HashService.class);
    }
}
