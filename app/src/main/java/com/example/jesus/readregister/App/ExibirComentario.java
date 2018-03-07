package com.example.jesus.readregister.App;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jesus.readregister.Comentario;
import com.example.jesus.readregister.Livro;
import com.example.jesus.readregister.R;

import io.objectbox.Box;
import model.App;

public class ExibirComentario extends AppCompatActivity {

    private Box<Comentario> comentarioBox;
    private long comentarioId;
    private TextView capText;
    private TextView comentText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_comentario);
        setupView();
        Intent intent = getIntent();
        comentarioBox = ((App)getApplication()).getBoxStore().boxFor(Comentario.class);
        comentarioId = intent.getLongExtra("comentarioId",-1);
        preencher(comentarioBox.get(comentarioId));

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void preencher(Comentario comentario) {
        capText.setText("Capitulo: " + comentario.getCapitulo());
        comentText.setText(comentario.getTexto());
    }

    private void setupView() {
        capText = findViewById(R.id.exi_cap);
        comentText = findViewById(R.id.exi_com);
    }
}
