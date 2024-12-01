package utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class MapCovert 
{
	/**
     * Metodo que transforma un Map, en un String tipo JSON (no la estamos usando...)
     **/
    public static String toJsonString(Map<String, Integer> map) 
    {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        for (Map.Entry<String, Integer> input : map.entrySet()) {
            // Cambiamos a formato JSON
            jsonBuilder.append("\"")
                       .append(input.getKey().replace("\"", "\\\"")) //saca comillas
                       .append("\": ")
                       .append(input.getValue())
                       .append(", ");
        }

        // Eliminar la última coma y espacio si el mapa no está vacío
        if (!map.isEmpty()) {
            jsonBuilder.setLength(jsonBuilder.length() - 2);
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
    
    /**
     * Metodo que transforma las keys de un Map, en un String para ser leido por JavaScript
     **/
    public static String mapKeysToLiteralString(Map<String, Integer> map) {
        return map.keySet().stream()
                .map(key -> "'" + key + "'")
                .collect(Collectors.joining(", ", "[", "]")); 
    }

    /**
     * Metodo que transforma los values de un Map, en un String para ser leido por JavaScript
     **/
    public static String mapValuesToLiteralString(Map<String, Integer> map) {
        return map.values().stream()
                .map(Object::toString) 
                .collect(Collectors.joining(", ", "[", "]")); 
    }

}
