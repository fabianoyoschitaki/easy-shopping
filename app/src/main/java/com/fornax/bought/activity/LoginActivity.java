package com.fornax.bought.activity;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fornax.bought.common.LoginVO;
import com.fornax.bought.rest.WSRestService;
import com.fornax.bought.utils.Constants;
import com.fornax.bought.utils.SharedPreferencesUtil;
import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;

import bought.fornax.com.bought.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity{

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_email)
    EditText emailText;

    @Bind(R.id.input_password)
    EditText senhaText;

    @Bind(R.id.btn_login)
    Button loginButton;

    @Bind(R.id.link_signup)
    TextView signupLink;

    @Bind(R.id.ckbManterLogado)
    CheckBox ckbManterLogado;

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Bind(R.id.progressBarHolder)
    FrameLayout progressBarHolder;

    //@Bind(R.id.fab) FloatingActionButton fab;

    private SharedPreferencesUtil sharedPreferencesUtil;

    //private GoogleApiClient mGoogleApiClient;

    private Location location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        /** resgatando dados do email e senha caso tenha **/
        sharedPreferencesUtil = new SharedPreferencesUtil(this);
        if (sharedPreferencesUtil.contains("Email") && sharedPreferencesUtil.contains("Senha")) {
            //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            emailText.setText(sharedPreferencesUtil.getString("Email"));
            senhaText.setText(sharedPreferencesUtil.getString("Senha"));
            ckbManterLogado.setChecked(true);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        /** action para botao de cadastro **/
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroUsuarioActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.trans_up_in, R.anim.trans_up_out);
            }
        });

        /**fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Cala Boca fdp", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });**/
    }


    private class LoginTask extends AsyncTask<String, Void, LoginVO> {

        AlphaAnimation inAnimation;
        AlphaAnimation outAnimation;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loginButton.setEnabled(false);
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(200);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(LoginVO login) {
            super.onPostExecute(login);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(200);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);
            loginButton.setEnabled(true);
            onLoginResult(login);
        }

        @Override
        protected LoginVO doInBackground(String... params) {
            LoginVO retorno = null;
            try {
                Thread.sleep(3000);
                WSRestService restClient = new WSRestService();
                retorno = restClient.getRestAPI().autenticar(params[0], params[1]);
            } catch (Exception e) {
                retorno = new LoginVO();
                retorno.setMsg("Desculpe. Tente novamente mais tarde! :(");
                retorno.setStatus(Constants.LOGIN_CODIGO_ERRO);
                e.printStackTrace();
            }
            return retorno;
        }
    }

    /**
     * Após o retorno do login pela Task
     *
     * @param login
     */
    private void onLoginResult(LoginVO login) {
        if (login != null) {
            if (Constants.LOGIN_CODIGO_SUCESSO.equals(login.getStatus())) {
                salvarLogin();
                Intent intent = new Intent(getApplicationContext(), TelaPrincipalActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.trans_up_in, R.anim.trans_up_out);
            } else {
                Snackbar snack = Snackbar.make(coordinatorLayout, login.getMsg(), Snackbar.LENGTH_LONG);
                ((TextView)snack.getView().findViewById(android.support.design.R.id.snackbar_text)).setGravity(Gravity.CENTER_HORIZONTAL);
                snack.show();
                //Toast.makeText(getApplicationContext(), login.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Quando clicar no botão login
     */
    public void doLogin() {
        Log.d(TAG, "Login");
        if ("mock".equalsIgnoreCase(emailText.getText().toString())){
            Intent intent = new Intent(getApplicationContext(), TelaPrincipalActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.trans_up_in, R.anim.trans_up_out);
        }
        if (validate()) {
            new LoginTask().execute(emailText.getText().toString(), senhaText.getText().toString());
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * Salva dados do login
     */
    private void salvarLogin() {
        if (ckbManterLogado.isChecked()){
            sharedPreferencesUtil.putString("Email", emailText.getText().toString());
            sharedPreferencesUtil.putString("Senha", senhaText.getText().toString());
        } else {
            sharedPreferencesUtil.remove("Email");
            sharedPreferencesUtil.remove("Senha");
        }
    }

    public void onLoginFailed() {
        Snackbar.make(coordinatorLayout, "Falha ao efetuar login", Snackbar.LENGTH_LONG).show();
        //Toast.makeText(getBaseContext(), "Falha ao efetuar login", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = senhaText.getText().toString();

        String mensagemErro = null;
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //emailText.setError("Entre com um e-mail válido.");
            mensagemErro = "Entre com um e-mail válido!";
            valid = false;
            YoYo.with(Techniques.Bounce).duration(700).playOn(findViewById(R.id.input_email));
        }
        if (password.isEmpty() || password.length() < 3) {
            if (mensagemErro == null){
                mensagemErro = "A senha deve conter no mínimo 3 caracteres.";
            } else {
                mensagemErro = mensagemErro + "\nA senha deve conter no mínimo 3 caracteres.";
            }
            //senhaText.setError("A senha deve conter no mínimo 3 caracteres.");
            valid = false;
            YoYo.with(Techniques.Bounce).duration(700).playOn(findViewById(R.id.input_password));
        }

        if (!valid) {
            Snackbar snack = Snackbar.make(coordinatorLayout, mensagemErro, Snackbar.LENGTH_LONG);
            ((TextView) snack.getView().findViewById(android.support.design.R.id.snackbar_text)).setGravity(Gravity.CENTER_HORIZONTAL);
            ((TextView) snack.getView().findViewById(android.support.design.R.id.snackbar_text)).setMaxLines(2);
            snack.show();
        }

        return valid;
    }
}