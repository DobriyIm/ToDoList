package tdl.code.ioc;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import tdl.code.servlets.HomeServlet;

@Singleton
public class ConfigServlets extends ServletModule {
    @Override
    protected void configureServlets() {
            serve("/").with(HomeServlet.class);
    }
}
