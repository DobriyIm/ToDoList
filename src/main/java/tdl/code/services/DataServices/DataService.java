package tdl.code.services.DataServices;

import com.google.inject.Singleton;

import java.sql.Connection;

public interface DataService {
    Connection getConnection();
}
