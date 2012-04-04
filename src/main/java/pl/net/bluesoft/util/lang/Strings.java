package pl.net.bluesoft.util.lang;

public abstract class Strings {
    private Strings() {}

    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }

    public static boolean hasText(String str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

	public static String withEnding(String str, String ending) {
		return str.endsWith(ending) ? str : str + ending;
	}

	public static String withoutEnding(String str, String ending) {
		return str != null && str.endsWith(ending) ? str.substring(0, str.length() - ending.length()) : str;
	}

    public static String withRequestParameter(String str, String parameterName, String parameterValue) {
        char separator = str.indexOf('?') != -1 ? '&' : '?';
        return str + separator + parameterName + "=" + parameterValue;
    }
}
