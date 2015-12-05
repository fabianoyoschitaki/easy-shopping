package com.fornax.bought.mock;

import com.fornax.bought.common.CompraVO;
import com.fornax.bought.common.MercadoVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 04/12/2015.
 */
public class ComprasMock {


    public static List<CompraVO> getCompras(){
        List<CompraVO> retorno = new ArrayList<CompraVO>();
        retorno.add(new CompraVO("1", BigDecimal.valueOf(56.35), Boolean.TRUE));
        retorno.add(new CompraVO("2",BigDecimal.valueOf(53.35), Boolean.TRUE));
        retorno.add(new CompraVO("3",BigDecimal.valueOf(82.35), Boolean.FALSE));
        retorno.add(new CompraVO("4",BigDecimal.valueOf(56.35), Boolean.TRUE));
        retorno.add(new CompraVO("5",BigDecimal.valueOf(92.35), Boolean.FALSE));
        retorno.add(new CompraVO("6",BigDecimal.valueOf(76.35), Boolean.TRUE));
        return retorno;
    }

    public static List<MercadoVO> getMercados() {
        List<MercadoVO> retorno = new ArrayList<MercadoVO>();
        retorno.add(new MercadoVO(1, "Carrefour", "O mercado carrefurto", "São Paulo", "SP", "Brasil", "100", "Avenida", "12345-000", "http://res.cloudinary.com/networkmi/image/upload/v1449290328/carrefour1_fwqwfq.gif"));
        retorno.add(new MercadoVO(2, "Extra", "O mercado a mais", "São Paulo", "SP", "Brasil", "200", "Avenida", "12345-000", "http://res.cloudinary.com/networkmi/image/upload/v1449290439/139881916514_fnrky7.jpg"));
        retorno.add(new MercadoVO(3, "Pão de Açúcar", "O mercado do pão diabético", "São Paulo", "SP", "Brasil", "300", "Avenida", "12345-000", "http://res.cloudinary.com/networkmi/image/upload/v1449290461/pao-de-acucar-logo_oy5zhs.jpg"));
        return retorno;
    }
}
