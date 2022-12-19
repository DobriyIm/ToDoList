package tdl.code.ioc;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import tdl.code.servlets.HomeServlet;
import tdl.code.servlets.SignInServlet;
import tdl.code.servlets.SignUpServlet;

@Singleton
public class ConfigServlets extends ServletModule {
    @Override
    protected void configureServlets() {
            serve("/").with(HomeServlet.class);
            serve("/signUp").with(SignUpServlet.class);
            serve("/signIn").with(SignInServlet.class);
    }
}
