package model;

import java.util.*;

/**
 * Parser JSON mínimo, sin dependencias externas.
 * Convierte un String JSON en estructuras nativas de Java:
 * Map<String,Object>, List<Object>, String, Double, Boolean, null.
 */
public class SimpleJsonParser {

    private final String json;
    private int pos = 0;

    private SimpleJsonParser(String json) {
        this.json = json;
    }

    public static Object parse(String json) {
        SimpleJsonParser parser = new SimpleJsonParser(json);
        parser.skipWhitespace();
        Object result = parser.parseValue();
        return result;
    }

    private Object parseValue() {
        skipWhitespace();
        char c = json.charAt(pos);

        if (c == '{') return parseObject();
        if (c == '[') return parseArray();
        if (c == '"') return parseString();
        if (c == 't' || c == 'f') return parseBoolean();
        if (c == 'n') { pos += 4; return null; } // "null"
        return parseNumber();
    }

    private Map<String, Object> parseObject() {
        Map<String, Object> map = new LinkedHashMap<>();
        pos++; // {
        skipWhitespace();

        if (json.charAt(pos) == '}') { pos++; return map; }

        while (true) {
            skipWhitespace();
            String key = parseString();
            skipWhitespace();
            pos++; // :
            Object value = parseValue();
            map.put(key, value);
            skipWhitespace();

            if (json.charAt(pos) == ',') { pos++; continue; }
            if (json.charAt(pos) == '}') { pos++; break; }
        }
        return map;
    }

    private List<Object> parseArray() {
        List<Object> list = new ArrayList<>();
        pos++; // [
        skipWhitespace();

        if (json.charAt(pos) == ']') { pos++; return list; }

        while (true) {
            list.add(parseValue());
            skipWhitespace();

            if (json.charAt(pos) == ',') { pos++; continue; }
            if (json.charAt(pos) == ']') { pos++; break; }
        }
        return list;
    }

    private String parseString() {
        pos++; // "
        StringBuilder sb = new StringBuilder();

        while (json.charAt(pos) != '"') {
            char c = json.charAt(pos);
            if (c == '\\') {
                pos++;
                char esc = json.charAt(pos);
                switch (esc) {
                    case 'n': sb.append('\n'); break;
                    case 't': sb.append('\t'); break;
                    case 'r': sb.append('\r'); break;
                    case '"': sb.append('"'); break;
                    case '\\': sb.append('\\'); break;
                    case '/': sb.append('/'); break;
                    default: sb.append(esc);
                }
            } else {
                sb.append(c);
            }
            pos++;
        }
        pos++; // "
        return sb.toString();
    }

    private Double parseNumber() {
        int start = pos;
        while (pos < json.length() &&
               (Character.isDigit(json.charAt(pos)) || "-+.eE".indexOf(json.charAt(pos)) >= 0)) {
            pos++;
        }
        return Double.parseDouble(json.substring(start, pos));
    }

    private Boolean parseBoolean() {
        if (json.charAt(pos) == 't') { pos += 4; return true; }
        pos += 5;
        return false;
    }

    private void skipWhitespace() {
        while (pos < json.length() && Character.isWhitespace(json.charAt(pos))) pos++;
    }
}