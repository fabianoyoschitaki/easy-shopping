package com.fornax.bought.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fornax.bought.common.LoginVO;
import com.fornax.bought.personalviews.CpfEditText;
import com.fornax.bought.rest.WSRestService;
import com.fornax.bought.utils.Constants;
import com.rey.material.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import bought.fornax.com.bought.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.http.Path;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_nome)
    EditText etNome;

    @Bind(R.id.input_email)
    EditText etEmail;

    @Bind(R.id.input_senha)
    EditText etSenha;

    @Bind(R.id.input_dataNascimento)
    EditText etDataNascimento;

    @Bind(R.id.input_cpf)
    CpfEditText etCpf;

    @Bind(R.id.btn_cadastrar)
    Button btnCadastrar;

    @Bind(R.id.link_login)
    TextView tvLinkLogin;

    @Bind(R.id.coordinatorLayoutSignup)
    CoordinatorLayout coordinatorLayout;

    @Bind(R.id.progressBarHolder)
    FrameLayout progressBarHolder;

    private DatePickerDialog.OnDateSetListener date;
    private Calendar dataAniversarioCalendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        //Datepicker do campo Data de Nascimento
        dataAniversarioCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                dataAniversarioCalendar.set(Calendar.YEAR, year);
                dataAniversarioCalendar.set(Calendar.MONTH, monthOfYear);
                dataAniversarioCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                atualizaCampoData();
            }

        };
        etDataNascimento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignupActivity.this, date,
                        dataAniversarioCalendar.get(Calendar.YEAR) - 18,
                        dataAniversarioCalendar.get(Calendar.MONTH),
                        dataAniversarioCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        tvLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                onBackPressed();
            }
        });
    }

    private void atualizaCampoData() {
        String formato = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        etDataNascimento.setText(sdf.format(dataAniversarioCalendar.getTime()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_down_in, R.anim.trans_down_out);
    }

    public void signup() {
        Log.d(TAG, "Signup");
        if (validate()) {
            new SignupTask().execute(
                    etNome.getText().toString(),
                    etEmail.getText().toString(),
                    etSenha.getText().toString(),
                    etCpf.getText().toString(),
                    etDataNascimento.getText().toString());
        }
    }

    public void onSignupSuccess() {
        btnCadastrar.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Falha no login.", Toast.LENGTH_LONG).show();

        btnCadastrar.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String nome = etNome.getText().toString();
        String cpf = etCpf.getText().toString();
        String email = etEmail.getText().toString();
        String senha = etSenha.getText().toString();
        String dataNascimento = etDataNascimento.getText().toString();

        String mensagemErro = null;
        View viewErro = null;

        if (nome.isEmpty()) {
            mensagemErro = "Nome não pode ser vazio!";
            viewErro = etNome;
            valid = false;
        }
        else if (cpf.isEmpty() || cpf.replace("-","").replace(".","").length() < 11) {
            mensagemErro = "CPF inválido!!";
            viewErro = etCpf;
            valid = false;
        }
        else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mensagemErro = "Entre com um e-mail válido!";
            viewErro = etEmail;
            valid = false;
        }
        else if (senha.isEmpty() || senha.length() < 3) {
            mensagemErro = "A senha deve conter no mínimo 3 dígitos.";
            viewErro = etSenha;
            valid = false;
        }
        else if (dataNascimento.isEmpty()) {
            mensagemErro = "Data de nascimento deve ser preenchida!.";
            viewErro = etDataNascimento;
            valid = false;
        }

        if (!valid){
            YoYo.with(Techniques.Bounce).duration(700).playOn(viewErro);
            Snackbar snack = Snackbar.make(coordinatorLayout, mensagemErro, Snackbar.LENGTH_LONG);
            ((TextView) snack.getView().findViewById(android.support.design.R.id.snackbar_text)).setGravity(Gravity.CENTER_HORIZONTAL);
            snack.show();
        }
        /**if (name.isEmpty() || name.length() < 3) {
            etNome.setError("at least 3 characters");
            valid = false;
        } else {
            etNome.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Entre com um e-mail válido.");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            etSenha.setError("A senha deve ter no mínimo 4 caracteres.");
            valid = false;
        } else {
            etSenha.setError(null);
        } **/
        return valid;
    }

    private class SignupTask extends AsyncTask<String, Void, LoginVO> {

        AlphaAnimation inAnimation;
        AlphaAnimation outAnimation;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnCadastrar.setEnabled(false);
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
            btnCadastrar.setEnabled(true);
            onSignupSuccess();;
        }

        @Override
        protected LoginVO doInBackground(String... params) {
            LoginVO retorno = null;
            try {
                Thread.sleep(3000);
                WSRestService restClient = new WSRestService();
                retorno = restClient.getRestAPI().cadastrarNovoUsuario(
                        params[0],
                        params[1],
                        params[2],
                        params[3],
                        params[4]);
            } catch (Exception e) {
                retorno = new LoginVO();
                retorno.setMsg("Desculpe. Tente novamente mais tarde! :(");
                retorno.setStatus(Constants.LOGIN_CODIGO_ERRO);
                e.printStackTrace();
            }
            return retorno;
        }
    }
}