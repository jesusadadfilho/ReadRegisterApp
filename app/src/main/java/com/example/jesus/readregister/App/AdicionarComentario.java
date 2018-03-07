package com.example.jesus.readregister.App;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jesus.readregister.Comentario;
import com.example.jesus.readregister.Livro;
import com.example.jesus.readregister.R;

import io.objectbox.Box;
import model.App;

public class AdicionarComentario extends AppCompatActivity {


    private EditText editCapitulo ;
    private EditText editComentario;
    private Box<Livro> livroBox;
    private Box<Comentario> comentarioBox;
    private Comentario  comentario;
    private long livroId, comentarioId;
    private boolean atualizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_comentario);
        setupView();
        livroBox = ((App)getApplication()).getBoxStore().boxFor(Livro.class);
        comentarioBox = ((App)getApplication()).getBoxStore().boxFor(Comentario.class);
        Intent intent = getIntent();
        livroId = intent.getLongExtra("livroId",-1);
        comentarioId = intent.getLongExtra("comentarioId",-1);
        Toast.makeText(this,""+ comentarioId,Toast.LENGTH_SHORT).show();

        if(comentarioId == -1){
            comentario = new Comentario();
            atualizar = false;
        }else{
            comentario = comentarioBox.get(comentarioId);
            preencher(comentario);
            atualizar = true;
        }
    }

    private void preencher(Comentario comentario) {
        editCapitulo.setText(comentario.getCapitulo());
        editComentario.setText(comentario.getTexto());
    }


    private void setupView() {
        this.editCapitulo = findViewById(R.id.cap_var);
        this.editComentario = findViewById(R.id.com_var);
    }

    public void savar(View view) {
        if(atualizar){
            comentario.setCapitulo(editCapitulo.getText().toString());
            comentario.setTexto(editComentario.getText().toString());
            comentarioBox.put(comentario);
        }else{
            comentario.setCapitulo(editCapitulo.getText().toString());
            comentario.setTexto(editComentario.getText().toString());
            comentario.getLivroToOne().setTarget(livroBox.get(livroId));
            comentarioBox.put(comentario);
        }
        Toast.makeText(this,"Comentario salvo",Toast.LENGTH_SHORT).show();
        finish();
    }
}
