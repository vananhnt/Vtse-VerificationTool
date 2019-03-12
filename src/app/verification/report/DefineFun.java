package app.verification.report;

public class DefineFun {
    private String name;
    private String type;
    private String value;

    public DefineFun() {

    }

    public DefineFun(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public DefineFun(String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    public String getExpression() {
        return name + " = " + value;
    }

    @Override
    public String toString() {
        return name + " " + type + " (" + value + ")";
    }
}
