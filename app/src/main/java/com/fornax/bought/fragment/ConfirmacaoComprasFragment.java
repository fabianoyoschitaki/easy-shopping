package com.fornax.bought.fragment;



import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fornax.bought.R;
import com.fornax.bought.activity.CarrinhoComprasActivity;
import com.fornax.bought.activity.ConfirmacaoCompraActivity;
import com.fornax.bought.activity.ItensCarrinhoActivity;
import com.fornax.bought.activity.PagamentoEfetuadoActivity;
import com.fornax.bought.common.CompraVO;
import com.fornax.bought.enums.StatusCompraENUM;
import com.fornax.bought.rest.WSRestService;
import com.fornax.bought.utils.PayPalUtil;
import com.fornax.bought.utils.SessionUtils;
import com.fornax.bought.utils.Utils;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ConfirmacaoComprasFragment extends Fragment {

    @Bind(R.id.coordinatorLayout)CoordinatorLayout coordinatorLayout;
    @Bind(R.id.btnPagar)Button btnPagar;
    @Bind(R.id.txtValorConfirmacao)TextView txtValorConfirmacao;
    @Bind(R.id.btnConferirCarrinho)Button btnConferirCarrinho;

    public ConfirmacaoComprasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        PayPalPayment retorno = null;
        if(SessionUtils.getCompra() != null){
            String idCompra = SessionUtils.getCompra().getId() + "_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "_" + SessionUtils.getUsuario().getId();
            retorno = new PayPalPayment(SessionUtils.getCompra().getValorTotal(), "USD", idCompra,
                    paymentIntent);
        }
        return retorno;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_confirmacao_compras, container, false);
        ButterKnife.bind(this, rootView);

        if(SessionUtils.getCompra() != null && SessionUtils.getCompra().getValorTotal() != null){
            txtValorConfirmacao.setText(Utils.getValorFormatado(SessionUtils.getCompra().getValorTotal()));
        }

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
                if (thingToBuy != null) {
                    Intent intent = new Intent(getContext(), PaymentActivity.class);
                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPalUtil.config);
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
                    startActivityForResult(intent, PayPalUtil.REQUEST_CODE_PAYMENT);
                } else {
                    Toast.makeText(getContext(), "Compra não encontrada.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnConferirCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ItensCarrinhoActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            }
        });
        return rootView;
    }

    private static final String TAG = "paymentExample";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PayPalUtil.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));

                        SessionUtils.getCompra().setStatusCompraENUM(StatusCompraENUM.PAGA);
                        SessionUtils.getCompra().setIdPayPal(confirm.getProofOfPayment().getPaymentId());

                        new AtualizarCompraTask().execute(SessionUtils.getCompra());
                    } catch (JSONException e) {
                        Log.e(TAG, "Um erro ocorreu ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Intent intent = new Intent(getContext(), ConfirmacaoCompraActivity.class);
                startActivityForResult(intent, PayPalUtil.REQUEST_CODE_PAYMENT);

                Log.i(TAG, "O usuário cancelou a compra.");
                Toast.makeText(getContext(), "Compra cancelada.", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        TAG,
                        "Um tipo inválido de pagamento foi informado.");
            }
        }
    }


    private class AtualizarCompraTask extends AsyncTask<CompraVO, Void, CompraVO> {

        AlphaAnimation inAnimation;
        AlphaAnimation outAnimation;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
        }

        @Override
        protected void onPostExecute(CompraVO compra) {
            super.onPostExecute(compra);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            onCompraResult(compra);
        }

        protected CompraVO doInBackground(CompraVO... compra) {
            CompraVO retorno = null;
            try {
                WSRestService restClient = new WSRestService();
                retorno = restClient.getRestAPI().atualizarCompra(compra[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return retorno;
        }
    }

    /**
     * Após o retorno da compra pela Task
     *
     * @param compra
     */
    private void onCompraResult(CompraVO compra) {
        if (compra != null) {
            if (compra.getStatusCompraENUM() != null && compra.getStatusCompraENUM().equals(StatusCompraENUM.PAGA)) {
                Intent intent = new Intent(getContext(), PagamentoEfetuadoActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "não deu", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
