package sample;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.List;

public class FileStructure {
    private List<sample.Keymap> KeyValuePairs = null;
    private String groupname = null;
    private String filename = null;


    public List <Keymap> getKeyValuePairs()
    {
        return (List<Keymap>) KeyValuePairs;
    }

    public String getGroupname()
    {
        return groupname;
    }

    public void setGroupname(String g)
    {
        groupname = g;
    }

    public void setKeyValuePairs(List<Keymap> newPairs)
    {
        KeyValuePairs = newPairs;
    }

    public void addToKeyValuePairs(Keymap k)
    {
        KeyValuePairs.add(k);
    }

    public void addFilename(String f)
    {
        filename = f;
    }

    public String getFilename()
    {
        return filename;
    }



}
