package com.fornax.bought.utils;

import com.fornax.bought.common.CompraVO;
import com.fornax.bought.common.UsuarioVO;

/**
 * Created by Fabia on 08/03/2016.
 */
public class SessionUtils {
    private static UsuarioVO usuario;
    private static CompraVO compra;

    public static UsuarioVO getUsuario() {
        return usuario;
    }

    public static void setUsuario(UsuarioVO usuario) {
        SessionUtils.usuario = usuario;
    }

    public static CompraVO getCompra() {
        return compra;
    }

    public static void setCompra(CompraVO compra) {
        SessionUtils.compra = compra;
    }
}
