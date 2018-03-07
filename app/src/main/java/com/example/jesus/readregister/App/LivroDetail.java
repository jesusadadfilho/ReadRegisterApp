package com.example.jesus.readregister.App;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesus.readregister.Comentario;
import com.example.jesus.readregister.Comentario_;
import com.example.jesus.readregister.Livro;
import com.example.jesus.readregister.R;

import java.util.List;

import io.objectbox.Box;
import model.App;
import model.adapters.ComentarioAdapter;

public class LivroDetail extends AppCompatActivity {

    private TextView autorText, dataIniText, dataTermText;
    private TextView titleText, progressoText, tagText;
    private Box<Livro> livrosBox;
    private RecyclerView recyclerView;
    private Box<Comentario> comentarioBox;
    private List<Comentario> comentarioList;
    private ProgressBar progressBar;
    private RatingBar ratingBar;
    private long livroId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro__detail);
        setupView();
        Intent intent = getIntent();
        livrosBox = ((App)getApplication()).getBoxStore().boxFor(Livro.class);
        comentarioBox = ((App)getApplication()).getBoxStore().boxFor(Comentario.class);
        livroId = intent.getLongExtra("livroId",-1);
        preencher(livrosBox.get(livroId));
    }

    @Override
    protected void onResume() {
        super.onResume();
        comentarioList = comentarioBox.query().equal(Comentario_.livroToOneId,livroId).build().find();
        ComentarioAdapter adapter = new ComentarioAdapter(this,comentarioList,comentarioBox);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ratingBar.setRating(livrosBox.get(livroId).getRatin());
    }

    private void preencher(Livro livro) {
        titleText.setText(livro.getTitulo());
        autorText.setText(livro.getAutor());
        progressBar.setProgress(livro.getPaginaAtual());
        progressBar.setMax(livro.getPaginas());
        progressoText.setText(livro.getSituacion());
        tagText.setText(livro.getTag());
        if(!livro.getData().equals("")){
            dataIniText.setText("Data de inicio: " + livro.getData());
        }
        if(!livro.getDataTermino().equals("")){
            dataTermText.setText("Data de Termino: " + livro.getDataTermino());
        }
        Toast.makeText(this,"" + livro.getSituacion(),Toast.LENGTH_SHORT).show();
    }

    private void setupView() {
        titleText = findViewById(R.id.livro_title);
        autorText = findViewById(R.id.livro_autor);
        recyclerView = findViewById(R.id.rv_comentarios);
        ratingBar = findViewById(R.id.ratin);
        progressoText = findViewById(R.id.situacion);
        progressBar = findViewById(R.id.progress_bar);
        tagText = findViewById(R.id.tag);
        dataIniText = findViewById(R.id.dataIni);
        dataTermText = findViewById(R.id.dataTerm);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Livro livro = livrosBox.get(livroId);
                livro.setRatin(v);
                livrosBox.put(livro);
            }
        });
    }


    public void novoCommentario(View view) {
        Intent intent = new Intent(this,AdicionarComentario.class);
        intent.putExtra("livroId", livroId);
        startActivity(intent);
    }


}
