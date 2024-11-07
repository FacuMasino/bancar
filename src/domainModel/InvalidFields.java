package domainModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase para almacenar y manejar campos inválidos y sus mensajes de error.
 */
public class InvalidFields {
    private final Map<String, String> fields;

    public InvalidFields() {
    	// Utilizamos hashmap para que no haya repetidos
        this.fields = new HashMap<>();
    }

    public void addField(String fieldName, String errorMessage) {
        fields.put(fieldName, errorMessage);
    }

    /**
     * Verifica si el campo específico es inválido.
     * Si existe en la lista, entonces es inválido
     */
    public boolean isFieldInvalid(String fieldName) {
        return fields.containsKey(fieldName);
    }

    /**
     * Obtiene el mensaje de error del campo.
     */
    public String getMsg(String fieldName) {
        return fields.get(fieldName);
    }

    public Map<String, String> getInvalidFields() {
        return fields;
    }
}
