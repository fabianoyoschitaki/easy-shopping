package com.fornax.bought.mock;

import com.fornax.bought.common.CompraVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 04/12/2015.
 */
public class ComprasMock {


    public static List<CompraVO> getCompras(){
        List<CompraVO> retorno = new ArrayList<CompraVO>();
        retorno.add(new CompraVO(BigDecimal.valueOf(56.35), Boolean.TRUE));
        retorno.add(new CompraVO(BigDecimal.valueOf(53.35), Boolean.TRUE));
        retorno.add(new CompraVO(BigDecimal.valueOf(82.35), Boolean.FALSE));
        retorno.add(new CompraVO(BigDecimal.valueOf(56.35), Boolean.TRUE));
        retorno.add(new CompraVO(BigDecimal.valueOf(92.35), Boolean.FALSE));
        retorno.add(new CompraVO(BigDecimal.valueOf(76.35), Boolean.TRUE));
        return retorno;
    }
}
