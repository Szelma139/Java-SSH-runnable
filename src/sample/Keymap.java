package sample;


public class Keymap {

    private String key;
    private String value;

    public Keymap(){}
    public Keymap(String k, String v)
    {
        this.key=k;
        this.value=v;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String k) {
        this.key = k;
    }

    public String getValue()
    {
        return this.value;
    }

    public void setValue(String v)
    {
        this.value = v;
    }

    public String toString(){//overriding the toString() method
        return key+" "+value;
    }

}