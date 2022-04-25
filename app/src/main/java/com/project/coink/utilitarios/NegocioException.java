package com.project.coink.utilitarios;

import androidx.annotation.Nullable;

/**Clase creada para obtener las excepciones**/
public class NegocioException extends Exception {

    private int codigo;
    private String message;


    public NegocioException(int codigo, String message) {
        super();
        this.codigo = codigo;
        this.message = message;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
