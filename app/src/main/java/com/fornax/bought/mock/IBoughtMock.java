package com.fornax.bought.mock;

import com.fornax.bought.common.CompraVO;
import com.fornax.bought.common.EstabelecimentoVO;
import com.fornax.bought.common.ItemCompraVO;
import com.fornax.bought.common.MinhaCompraVO;
import com.fornax.bought.common.ProdutoVO;
import com.fornax.bought.common.UsuarioVO;
import com.fornax.bought.enums.StatusCompraENUM;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrador on 04/12/2015.
 */
public class IBoughtMock {

    private static UsuarioVO usuario;
    public static boolean isMock = false;
    private static ItemCompraVO retorno;

    public static List<MinhaCompraVO> getCompras(){
        List<MinhaCompraVO> retorno = new ArrayList<MinhaCompraVO>();
        retorno.add(new MinhaCompraVO("6516D5FGXAVASCA", BigDecimal.valueOf(56.35), StatusCompraENUM.PAGA, "http://res.cloudinary.com/networkmi/image/upload/v1449290439/139881916514_fnrky7.jpg"));
        retorno.add(new MinhaCompraVO("65GA1SXAXAVASCA",BigDecimal.valueOf(53.35), StatusCompraENUM.AGUARDANDO_PAGAMENTO, "http://res.cloudinary.com/networkmi/image/upload/v1449290439/139881916514_fnrky7.jpg"));
        retorno.add(new MinhaCompraVO("651SDAFSXAVASCA",BigDecimal.valueOf(82.35), StatusCompraENUM.CANCELADO, "http://res.cloudinary.com/networkmi/image/upload/v1449290439/139881916514_fnrky7.jpg"));
        return retorno;
    }

    public static UsuarioVO getUsuarioMock() {
        UsuarioVO usuario = new UsuarioVO();
        usuario.setId(Long.valueOf(1));
        usuario.setSenha("123456");
        usuario.setNome("mock");
        usuario.setEmail("teste@bought.com.br");
        usuario.setCpf("12345678910");
        usuario.setAtivo(Boolean.FALSE);
        return usuario;
    }

    public static CompraVO getCompraMock(){
        CompraVO retorno = new CompraVO();
        retorno.setEstabelecimentoVO(getEstabelecimentoMock());
        retorno.setId(1L);
        retorno.setNumeroSessao("1");
        retorno.setUsuarioVO(getUsuarioMock());
        return retorno;
    }

    public static EstabelecimentoVO getEstabelecimentoMock() {
        EstabelecimentoVO retorno = new EstabelecimentoVO();
        retorno.setNome("Extra");
        retorno.setCodigoEstabelecimento("EXTRA01");
        retorno.setDescricao("O mercado a mais");
        retorno.setId(1L);
        retorno.setNomeCidade("São Paulo");
        retorno.setNumeroCep("01226010");
        retorno.setNumeroLogradouro("100");
        retorno.setTipoLogradouro("Rua");
        retorno.setSiglaEstado("SP");
        retorno.setUrlLogo("https://logodownload.org/wp-content/uploads/2014/12/extra-logo-mercado-1.jpg");
        return retorno;
    }

    public static ItemCompraVO getItemCompraMock() {
        ItemCompraVO retorno = new ItemCompraVO();
        retorno.setId(1L);
        retorno.setQuantidade(1);
        retorno.setValor(new BigDecimal(2.40));
        retorno.setProdutoVO(getProdutoMock());
        return retorno;
    }

    public static ProdutoVO getProdutoMock() {
        ProdutoVO retorno = new ProdutoVO();
        retorno.setId(1L);
        retorno.setCodigoBarra("123");
        retorno.setNome("Água Minalba");
        retorno.setUrlImagem("http://www.paodeacucar.com.br/img/uploads/1/227/481227.jpg");
        return retorno;
    }

   /* public static CompraVO getCompraVO(BigDecimal valorTotal) {
        CompraVO retorno = new CompraVO();
        retorno.setValorTotalCompra(valorTotal);
        retorno.setDataCompra(Calendar.getInstance());
        //TODO colocar os itens
        return retorno;
    }*/

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
                retorno = IBoughtMock.getMercados().get(0);
            } else if (qrCode.equals("extra0001")){
                retorno = IBoughtMock.getMercados().get(1);
            } else {
                retorno = IBoughtMock.getMercados().get(2);
            }
        }
        return retorno;
    }**/
}
