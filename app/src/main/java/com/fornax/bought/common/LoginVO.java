package com.fornax.bought.common;

/**
 * Created by Administrador on 10/01/2016.
 */
public class LoginVO {

    private Integer status;
    private String msg;
    private UsuarioVO usuario;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UsuarioVO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioVO usuario) {
        this.usuario = usuario;
    }
}

