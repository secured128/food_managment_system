

package com.elalex.food.model;

        import com.fasterxml.jackson.annotation.JsonIgnore;
        import com.fasterxml.jackson.annotation.JsonProperty;
        import com.fasterxml.jackson.databind.ObjectMapper;

        import javax.persistence.Column;
        import javax.persistence.Entity;
        import javax.persistence.Id;
        import javax.persistence.Transient;
        import java.io.IOException;
        import java.io.Serializable;
        import java.io.StringWriter;

@Entity
public class GetSequenceDB implements Serializable
{

    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();

    @JsonProperty("sequenceId")
    @Id
    @Column(name = "sequence_id")
    private long sequenceId;


    public GetSequenceDB()
    {

    }



    @Override
    public String toString() {
        StringWriter sw = new StringWriter();
        try {
            new ObjectMapper().writeValue(sw, this);
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
    }
}
