package sample;

import org.ini4j.Profile;
import org.ini4j.Wini;

import javax.swing.text.Keymap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    public void getListOfKeyValuePairs(String filepath, String filename) throws IOException {

        //<Keymap> List<Keymap
        List<Keymap> list = new ArrayList<>();
        Wini ini = new Wini(new File(filepath + filename));
        System.out.println("Ilosc grup w pliku to" + ini.size());


        System.out.println("Sections n" + ini.size());
        for (String sectionName : ini.keySet()) {
            Profile.Section section = ini.get(sectionName);
            for (String optionKey : section.keySet()) {
                System.out.println("\t"+optionKey+"="+section.get(optionKey));
                //Keymap keymap = new Keymap(optionKey, section.get(optionKey));
                // list.add(keymap);
                // list.add((Keymap) new sample.Keymap(optionKey, section.get(optionKey)));

            }

        }

        // return list;
    }


    public void writeToFile(String filename, String group, String key, String value) throws IOException {

        Wini ini = new Wini(new File(filename));
        ini.put(group, key, value);
//        ini.put("nowa", "klucz", "wartosc");
        Wini.Section section;
        section = ini.get(group);
        section.put(key, value);
        section.put("grupa", "wartosc");
        System.out.println("Group - " + group + " | key - " + key + " |value - " + value);
        ini.store();
    }

    public String getGroupName(String filename) throws IOException {
        Wini ini = new Wini(new File(filename));
        String group="";
        for (String sectionName : ini.keySet())
        {
            group = sectionName;
        }
        return group;

    }


}
