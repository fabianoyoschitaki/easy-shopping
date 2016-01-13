package com.fornax.bought.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fornax.bought.common.LoginVO;
import com.fornax.bought.rest.WSRestService;
import com.fornax.bought.utils.Constants;
import com.fornax.bought.utils.SharedPreferencesUtil;

import bought.fornax.com.bought.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity{

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_email) EditText emailText;
    @Bind(R.id.input_password) EditText senhaText;
    @Bind(R.id.btn_login) Button loginButton;
    @Bind(R.id.link_signup) TextView signupLink;
    @Bind(R.id.ckbManterLogado) CheckBox ckbManterLogado;

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

        /** action para botao login **/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        /** action para botao de cadastro **/
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog  dialog = new ProgressDialog(this);
        dialog.setMessage("Autenticando...");
        dialog.show();

        final String email = emailText.getText().toString();
        final String senha = senhaText.getText().toString();

        new android.os.Handler().postDelayed(
                 new Runnable() {
                     public void run() {
                         dialog.dismiss();
                         efetuarLogin(email, senha,dialog);
                     }
                 }, 1000);
    }

    public void efetuarLogin(String email, String senha, final ProgressDialog dialog){
        WSRestService restClient = new WSRestService();
        restClient.getRestAPI().autenticar(email, senha, new Callback<LoginVO>() {
            @Override
            public void success(LoginVO loginResponse, Response response) {
                if (loginResponse != null) {

                    if (Constants.LOGIN_CODIGO_SUCESSO.equals(loginResponse.getStatus())) {
                        onLoginSuccess();
                        Intent intent = new Intent(getApplicationContext(), TelaPrincipalActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), loginResponse.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
                loginButton.setEnabled(true);
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        salvarLogin();
        loginButton.setEnabled(true);
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
        Toast.makeText(getBaseContext(), "Falha ao efetuar login", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = senhaText.getText().toString();

           if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
               if (email.isEmpty()) {
                   emailText.setError("Entre com um e-mail válido.");
                   valid = false;
               } else {
                   emailText.setError(null);
               }

               if (password.isEmpty() || password.length() < 3 || password.length() > 10) {
                   senhaText.setError("A senha deve conter no mínimo 3 caracteres.");
                   valid = false;
               } else {
                   senhaText.setError(null);
               }
           }

        return valid;
    }
}