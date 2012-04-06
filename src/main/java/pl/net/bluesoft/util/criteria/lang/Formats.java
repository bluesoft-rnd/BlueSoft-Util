package pl.net.bluesoft.util.criteria.lang;

import java.util.Collection;

public class Formats {
    public static String underscoreToCamel(String str) {
        char[] characters = str.toCharArray();
        StringBuilder sb = new StringBuilder().append(Character.toLowerCase(characters[0]));
        for (int i = 1; i < characters.length; ++i) {
            char c = characters[i];
            if (c == '_' || c == ' ') {
                c = Character.toUpperCase(characters[++i]);
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String camelToUnderscore(String str) {
        char[] characters = str.toCharArray();
        StringBuilder sb = new StringBuilder().append(Character.toUpperCase(characters[0]));
        for (int i = 1; i < characters.length; ++i) {
            char c = characters[i];
            if (Character.isUpperCase(c)) {
                sb.append('_');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String join(String separator, Collection tokens) {
        return join(separator, tokens.toArray());
    }

    public static String join(String separator, Object... tokens) {
        StringBuffer sb = new StringBuffer();
        if (tokens != null && tokens.length > 0) {
            for (int i = 0; i < tokens.length; ++i) {
                Object token = tokens[i];
                if (token != null) {
                    sb.append(token);
                    if (i < tokens.length - 1) {
                        sb.append(separator);
                    }
                }
            }
        }
        return sb.toString();
    }
}
