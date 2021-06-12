package model;

import java.io.Serializable;
import java.util.Locale;


public enum Color implements Serializable {
    BLACK("black"),
    WHITE("white"),
    BROWN("brown");

    private String name;

    Color(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Color fromStr(String cmd) {
//        if(cmd != null) {
//            cmd = cmd.toLowerCase();
//        }
        for (Color s : Color.values()) {
            if (cmd != null && cmd.equals(s.name())) {
                return s;
            }
        }
        return null;
    }

}
