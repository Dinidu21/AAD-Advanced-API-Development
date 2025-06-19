package bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton") // Default scope is singleton, but explicitly defining it
public interface Agreement {
    void chat();
}
