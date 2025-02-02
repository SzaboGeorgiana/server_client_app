package festival.model;
import java.io.Serializable;

public interface Identifier<ID> {
    ID getId();

    void setId(ID id);
}