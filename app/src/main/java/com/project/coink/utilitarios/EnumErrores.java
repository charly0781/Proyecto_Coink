package com.project.coink.utilitarios;

public enum EnumErrores {

    ERROR_CAMPO(100001, "El campo {} no tiene datos"),
    ERROR_CONTEXTO(100002, "Sin datos en contexto"),
    ERROR_NUM_CELULAR(100003, "Numero Celular Invalido"),
    ERROR_DATOS_CTA(100004, "ERROR AL VALIDAR LOS DATOS DE LA CUENTA"),
    ERROR_CONTRATO(100005, "DEBES ACEPTAR LOS TERMINOS DEL CONTRATO"),
    REGISTRO_OK(100006, "DATOS REGISTRADOS CON EXITO"),
    ERROR_DATOS(100007, "ERROR AL CARGAR DATOS"),
    ERROR_OPCION(100008, "OPCION NO DISPONIBLE"),
    TOO_MANY_ROWS(9003, "Error, demasiadas columnas para su remplazo"),
    ;

    private final String value;
    private final int key;

    private EnumErrores(int key, String value) {
        this.value = value;
        this.key = key;
    }

    public int getKey() {
        return key;
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
