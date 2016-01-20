package com.fornax.bought.mock;

import com.fornax.bought.common.CompraVO;
import com.fornax.bought.common.MinhaCompraVO;
import com.fornax.bought.common.MercadoVO;
import com.fornax.bought.enums.StatusCompraEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrador on 04/12/2015.
 */
public class ComprasMock {


    public static List<MinhaCompraVO> getCompras(){
        List<MinhaCompraVO> retorno = new ArrayList<MinhaCompraVO>();
        retorno.add(new MinhaCompraVO("6516D5FGXAVASCA", BigDecimal.valueOf(56.35), StatusCompraEnum.PAGO, "http://res.cloudinary.com/networkmi/image/upload/v1449290439/139881916514_fnrky7.jpg"));
        retorno.add(new MinhaCompraVO("65GA1SXAXAVASCA",BigDecimal.valueOf(53.35), StatusCompraEnum.AGUARDANDO_PAGAMENTO, "http://res.cloudinary.com/networkmi/image/upload/v1449290439/139881916514_fnrky7.jpg"));
        retorno.add(new MinhaCompraVO("651SDAFSXAVASCA",BigDecimal.valueOf(82.35), StatusCompraEnum.CANCELADO, "http://res.cloudinary.com/networkmi/image/upload/v1449290439/139881916514_fnrky7.jpg"));
        return retorno;
    }

    public static CompraVO getCompraVO(BigDecimal valorTotal) {
        CompraVO retorno = new CompraVO();
        retorno.setValorTotalCompra(valorTotal);
        retorno.setDataCompra(Calendar.getInstance());
        //TODO colocar os itens
        return retorno;
    }

    /** public static List<MercadoVO> getMercados() {
        List<MercadoVO> retorno = new ArrayList<MercadoVO>();
        retorno.add(new MercadoVO(1, "Carrefour", "O mercado carrefurto", "São Paulo", "SP", "Brasil", "100", "Avenida", "12345-000", "http://res.cloudinary.com/networkmi/image/upload/v1449290328/carrefour1_fwqwfq.gif"));
        retorno.add(new MercadoVO(2, "Extra", "O mercado a mais", "São Paulo", "SP", "Brasil", "200", "Avenida", "12345-000", "http://res.cloudinary.com/networkmi/image/upload/v1449290439/139881916514_fnrky7.jpg"));
        retorno.add(new MercadoVO(3, "Pão de Açúcar", "O mercado do pão diabético", "São Paulo", "SP", "Brasil", "300", "Avenida", "12345-000", "http://res.cloudinary.com/networkmi/image/upload/v1449290461/pao-de-acucar-logo_oy5zhs.jpg"));
        return retorno;
    }

    public static MercadoVO getMercadoPeloQRCode(String qrCode) {

        MercadoVO retorno = null;
        if (qrCode != null){
            if (qrCode.equals("carrefour0001")){
                retorno = ComprasMock.getMercados().get(0);
            } else if (qrCode.equals("extra0001")){
                retorno = ComprasMock.getMercados().get(1);
            } else {
                retorno = ComprasMock.getMercados().get(2);
            }
        }
        return retorno;
    }**/
}
