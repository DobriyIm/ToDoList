package tdl.code.ioc;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import tdl.code.servlets.*;

@Singleton
public class ConfigServlets extends ServletModule {
    @Override
    protected void configureServlets() {
            serve("/").with(HomeServlet.class);
            serve("/signUp").with(SignUpServlet.class);
            serve("/signIn").with(SignInServlet.class);
            serve("/tasks").with(TasksListServlet.class);
            serve("/task").with(TaskServlet.class);
            serve("/projects").with(ProjectsListServlet.class);
            serve("/project").with(ProjectServlet.class);
    }
}
