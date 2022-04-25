package com.project.coink.utilitarios;

import android.widget.EditText;
import android.widget.Spinner;

public enum EnumTextos {

    UN_DESARROLLO("MODULO EN DESARROLLO"),
    CELULAR("CELULAR"),
    TP_DOCUMENTO("TIPO_DOCUMENTO"),
    NUM_DOCUEMNTO("NUMERO_DOCUMENTO"),
    FECHA_DOCUMENTO("FECHA_DOCUMENTO"),
    FECHA_NACIMIENTO("FECHA_NACIMIENTO"),
    GENERO("GENERO"),
    CORRERO("CORREO"),
    CORREO_CONF("CONFIRMAR_CORREO"),
    PIN("PIN"),
    PIN_CONF("CONFIRMAR_PIN"),
    VACIOS("VACIOS"),
    VALOR_CAMPO("EL CAMPO {} NO TIENE VALOR, FAVOR REVISE"),
    ERROR_PIN("EL PIN NO CONFIRMA"),
    MENSAJE("MENSAJE"),
    SI("SI"),
    DESCRIPCION("description"),
    GET("GET"),

    ;

    private final String value;

    private EnumTextos(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String replaceMessage(String... args) throws NegocioException {
        try {
            String mensaje = value;
            Integer cont = 0;
            while (mensaje.contains("{}")) {
                mensaje = mensaje.replaceFirst("\\{\\}", args[cont]);
                cont++;
            }
            return mensaje;
        } catch (Exception e) {
            throw new NegocioException(EnumErrores.TOO_MANY_ROWS.getKey(),
                    EnumErrores.TOO_MANY_ROWS.getValue());
        }
    }

}
