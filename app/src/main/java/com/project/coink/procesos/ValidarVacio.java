package com.project.coink.procesos;

import com.project.coink.utilitarios.EnumErrores;
import com.project.coink.utilitarios.EnumTextos;
import com.project.coink.utilitarios.NegocioException;

import java.util.HashMap;
import java.util.Map;

public class ValidarVacio {

    public Map<String, Object> validarVacio(Map<String, Object> campos) throws NegocioException
    {
        Map<String, Object> respuesta = new HashMap<>();

        if (campos.isEmpty())
        {
            respuesta.put(EnumTextos.VACIOS.getValue(), EnumErrores.ERROR_CONTEXTO.getValue());
        }else
        {
            for (Map.Entry<String, Object> m : campos.entrySet())
            {
                String key = m.getKey();
                String value = m.getValue().toString();
                if (value.isEmpty() || value.equals(""))
                {
                    respuesta.put(EnumTextos.VACIOS.getValue(), EnumErrores.ERROR_CAMPO.replaceMessage(key));
                    break;
                }
            }
        }
        return respuesta;

    }

}
