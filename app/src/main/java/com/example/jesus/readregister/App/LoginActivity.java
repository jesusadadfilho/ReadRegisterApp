package com.example.jesus.readregister.App;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jesus.readregister.Conta;
import com.example.jesus.readregister.Conta_;
import com.example.jesus.readregister.R;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;
import model.App;

public class LoginActivity extends AppCompatActivity {

    private Box<Conta> contasBox;
    private EditText editUsuario;
    private EditText editSenha;
    private Switch aSwitch;
    private String usuario;
    private String senha;
    private long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupView();
        contasBox = ((App)getApplication()).getBoxStore().boxFor(Conta.class);
        aSwitch.setChecked(verifyLogado());
        SharedPreferences preferences = getSharedPreferences("reader.file", MODE_PRIVATE);
        if(verifyLogado() && preferences.getLong("userId", -1) != -1){
            Toast.makeText(this, "permanecer",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,ListarLivros.class));
            finish();
        }else{
            Toast.makeText(this, "não permanecer",Toast.LENGTH_LONG).show();
        }


    }


    private void realizarLogin(Conta conta){
        contasBox.put(conta);
    }


    private boolean verifyLogado(){
        SharedPreferences preferences = getSharedPreferences("reader.file", MODE_PRIVATE);
        return preferences.getBoolean("logado", false);
    }

    public void mensage(int condition){
        if(condition == 1){
            Toast.makeText(this, "Senhas diferentes", Toast.LENGTH_SHORT).show();
        }else if(condition == 2){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }else if(condition == 3){
            Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT).show();
        }
    }
    public void setupView(){
        this.editUsuario = (EditText) findViewById(R.id.editLogin);
        this.editSenha = (EditText) findViewById(R.id.editSenha);
        this.aSwitch = (Switch) findViewById(R.id.switch1);

    }

    public void login(View view) {
        if(!editUsuario.getText().toString().equals("") && !editSenha.getText().toString().equals("")){
            usuario = editUsuario.getText().toString();
            senha = editSenha.getText().toString();
            Query query = contasBox.query().equal(Conta_.password,senha).equal(Conta_.username,usuario).build();
            List<Conta> result = query.find();
            if (result.size() > 0) {
                Intent intent = new Intent(this, ListarLivros.class);
                SharedPreferences preferences = getSharedPreferences("reader.file", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong("userId", result.get(0).getId());
                editor.commit();
                finish();
                startActivity(intent);
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Senha e/ou usuario incorretos!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "preencha todos os campos",Toast.LENGTH_SHORT).show();
        }

    }

    public void RealizarCadastro(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View viewDialog = getLayoutInflater().inflate(R.layout.activity_cadastro, null);
        SharedPreferences preferences = getSharedPreferences("reader.file", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();



        builder.setView(viewDialog)
                .setTitle("Cadastro")
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editEmail = (EditText) viewDialog.findViewById(R.id.editEmail);
                        EditText editSenha1 = (EditText) viewDialog.findViewById(R.id.editSenha1);
                        EditText editSenha2 = (EditText) viewDialog.findViewById(R.id.editSenha2);
                        if(!editEmail.getText().toString().equals("")
                                && !editSenha1.getText().toString().equals("")
                                && !editSenha2.getText().toString().equals("")){

                            String email = editEmail.getText().toString();
                            String senha1 = editSenha1.getText().toString();
                            String senha2 = editSenha2.getText().toString();

                            if(senha1.equals(senha2)){
                                Conta conta = new Conta(email, senha1);
                                mensage(3);
                                realizarLogin(conta);
                            }else {
                                mensage(1);
                            }
                        }else{
                            mensage(2);
                        }



                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();

    }

    public void logado(View view) {
        SharedPreferences preferences = getSharedPreferences("reader.file", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("logado", aSwitch.isChecked());
        editor.commit();
    }
}
