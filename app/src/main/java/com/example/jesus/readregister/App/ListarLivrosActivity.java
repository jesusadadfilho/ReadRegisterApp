package com.example.jesus.readregister.App;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesus.readregister.Conta;
import com.example.jesus.readregister.Livro;

import model.adapters.LivrosAdapter;

import com.example.jesus.readregister.Livro_;
import com.example.jesus.readregister.R;

import java.util.List;

import io.objectbox.Box;
import model.App;

public class ListarLivrosActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Box<Livro> livrosBox;
    private Livro livro;
    private RecyclerView recyclerView;
    private LivrosAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private TextView buscaResult;
    private ImageButton search;
    private ActionBarDrawerToggle toggle;
    private int option;
    private List<Livro> livroList;
    private Box<Conta> contasBox;
    private long userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_livros);
        setupViews();
        setupDrawer();
        contasBox = ((App) getApplication()).getBoxStore().boxFor(Conta.class);
        livrosBox = ((App) getApplication()).getBoxStore().boxFor(Livro.class);


    }



    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupViews() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_livros);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        buscaResult = findViewById(R.id.buscaResult);
        search = findViewById(R.id.search);
        buscaResult = findViewById(R.id.buscaResult);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("reader.file", MODE_PRIVATE);
        userId = preferences.getLong("userId",userId);
        livroList = livrosBox.query().equal(Livro_.donoId,userId).build().find();
        LivrosAdapter adapter = new LivrosAdapter(this,livroList,livrosBox);
        buscaResult.setText("Todos os livros");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }


    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    public void novoLivro(View view) {
        Intent intent = new Intent(this,CasdastroLivrosActivity.class);
        intent.putExtra("contasId", userId);
        startActivity(intent);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.generos){
            livroList.clear();
            buscaResult.setText("Todos os livros");
            livroList = livrosBox.query().equal(Livro_.donoId,userId).build().find();
            LivrosAdapter adapter = new LivrosAdapter(this, livroList,livrosBox);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
        }
        if (id == R.id.açao) {
            livroList.clear();
            buscaResult.setText("Genero - ação");
            for (Livro l : livrosBox.query().equal(Livro_.donoId,userId).build().find()) {
                if (l.getGenero().equalsIgnoreCase("açao")) {
                    livroList.add(l);
                }
            }
            LivrosAdapter adapter = new LivrosAdapter(this, livroList,livrosBox);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
        }
        if (id == R.id.aventura) {
            livroList.clear();
            buscaResult.setText("Genero - aventura");
            for (Livro l :  livrosBox.query().equal(Livro_.donoId,userId).build().find()) {
                if (l.getGenero().equalsIgnoreCase("aventura")) {
                    livroList.add(l);
                }
            }
            LivrosAdapter adapter = new LivrosAdapter(this, livroList,livrosBox);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
        }
        if (id == R.id.terror) {
            livroList.clear();
            buscaResult.setText("Genero - terror");
            for (Livro l :  livrosBox.query().equal(Livro_.donoId,userId).build().find()) {
                if (l.getGenero().equalsIgnoreCase("terror")) {
                    livroList.add(l);
                }
            }
            LivrosAdapter adapter = new LivrosAdapter(this, livroList,livrosBox);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            }
        if (id == R.id.drama) {
            livroList.clear();
            buscaResult.setText("Genero - drama");
            for (Livro l :  livrosBox.query().equal(Livro_.donoId,userId).build().find()) {
                if (l.getGenero().equalsIgnoreCase("drama")) {
                    livroList.add(l);
                }
            }
            Toast.makeText(this,"drama",Toast.LENGTH_SHORT).show();
            LivrosAdapter adapter = new LivrosAdapter(this, livroList,livrosBox);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            }
        if (id == R.id.lido) {
            livroList.clear();
            buscaResult.setText("Situação - Lido(s)");
            for (Livro l :  livrosBox.query().equal(Livro_.donoId,userId).build().find()) {
                if (l.getSituacion().equalsIgnoreCase("lido")) {
                    livroList.add(l);
                }
            }
            Toast.makeText(this,"lido(s)",Toast.LENGTH_SHORT).show();
            LivrosAdapter adapter = new LivrosAdapter(this, livroList,livrosBox);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
        }
        if (id == R.id.lendo) {
            livroList.clear();
            buscaResult.setText("Situação - Lendo");
            for (Livro l :  livrosBox.query().equal(Livro_.donoId,userId).build().find()) {
                if (l.getSituacion().equalsIgnoreCase("lendo")) {
                    livroList.add(l);
                }
            }
            Toast.makeText(this,"Em processo de leitura",Toast.LENGTH_SHORT).show();
            LivrosAdapter adapter = new LivrosAdapter(this, livroList,livrosBox);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
        }

        if (id == R.id.desejo) {
            livroList.clear();
            buscaResult.setText("Situação - Desejo");
            for (Livro l :  livrosBox.query().equal(Livro_.donoId,userId).build().find()) {
                if (l.getSituacion().equalsIgnoreCase("desejo")) {
                    livroList.add(l);
                }
            }
            Toast.makeText(this,"Desejo ler",Toast.LENGTH_SHORT).show();
            LivrosAdapter adapter = new LivrosAdapter(this, livroList,livrosBox);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
        }

        if(id == R.id.sair){
            SharedPreferences preferences = getSharedPreferences("reader.file", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("logado", false);
            editor.putLong("userId",-1);
            editor.apply();
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
        return false;

    }

    public void buscar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View viewDialog = getLayoutInflater().inflate(R.layout.activity_search, null);
        builder.setView(viewDialog)
                .setTitle("Procurar")
                .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editSearch = (EditText) viewDialog.findViewById(R.id.pesquisa);
                        String busca = editSearch.getText().toString();
                        buscar(busca);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();

    }

    private void buscar(String busca) {
        if (option == 1) {
            livroList.clear();
            for (Livro l : livrosBox.query().equal(Livro_.donoId, userId).build().find()) {
                if (l.getAutor().equalsIgnoreCase(busca)) {
                    livroList.add(l);
                }
            }
            Toast.makeText(this, "Resultado para " + busca, Toast.LENGTH_SHORT).show();
            LivrosAdapter adapter = new LivrosAdapter(this, livroList, livrosBox);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
        }else if (option == 2) {
            livroList.clear();
            for (Livro l : livrosBox.query().equal(Livro_.donoId, userId).build().find()) {
                if (l.getTitulo().equalsIgnoreCase(busca)) {
                    livroList.add(l);
                }
            }
            Toast.makeText(this, "Resultado para " + busca, Toast.LENGTH_SHORT).show();
            LivrosAdapter adapter = new LivrosAdapter(this, livroList, livrosBox);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
        }

    }

    public void setType(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.filtro, popup.getMenu());
        popup.setOnMenuItemClickListener((menuItem -> {
            if (menuItem.getItemId() == R.id.s_autor) {
                option = 1;
            }
            if (menuItem.getItemId() == R.id.s_titulo) {
                option = 2;
            }return false;
        }));
        popup.show();
    }

}


