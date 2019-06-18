package com.elalex.food.model;


        import com.fasterxml.jackson.annotation.JsonIgnore;
        import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
        import com.fasterxml.jackson.annotation.JsonProperty;
        import com.fasterxml.jackson.databind.ObjectMapper;

        import javax.persistence.*;
        import java.io.IOException;
        import java.io.Serializable;
        import java.io.StringWriter;

//@JsonPropertyOrder({"id", "name", "address","city", })

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "measure_conversion")
public class MeasureConversionDB implements Serializable
{
    /* idea is to take converting factor from excel.Value in units of from_unit multiply trans_value will give value in units of  to_unit*/
    static public final  int NUMBER_OF_PARAMS=3;
    @JsonIgnore
    @Transient
    private ObjectMapper jsonMapper = new ObjectMapper();

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @JsonProperty("from_unit")
    @Column(name = "from_unit")
    private String fromUnit;

    @JsonProperty("to_unit")
    @Column(name = "to_unit")
    private String toUnit;

    @JsonProperty("conversion_value")
    @Column(name = "conversion_value")
    private Double conversionValue;


    public MeasureConversionDB()
    {

    }
    public MeasureConversionDB(  String dbStructure[])
    {
        this.setFromUnit(dbStructure[0]);
        this.setToUnit(dbStructure[1]);
        this.setConversionValue(Double.valueOf(dbStructure[2]));
    }

    public MeasureConversionDB(String json) throws Exception
    {
        MeasureConversionDB measureTranslationDB = getJsonMapper().readValue(json, MeasureConversionDB.class);
        this.setFromUnit(measureTranslationDB.getFromUnit());
        this.setToUnit(measureTranslationDB.getToUnit());
        this.setConversionValue(measureTranslationDB.getConversionValue());

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

    public ObjectMapper getJsonMapper() {
        return jsonMapper;
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFromUnit() {
        return fromUnit;
    }

    public void setFromUnit(String fromUnit) {
        this.fromUnit = fromUnit;
    }

    public String getToUnit() {
        return toUnit;
    }

    public void setToUnit(String toUnit) {
        this.toUnit = toUnit;
    }

    public Double getConversionValue() {
        return conversionValue;
    }

    public void setConversionValue(Double conversionValue) {
        this.conversionValue = conversionValue;
    }
}
