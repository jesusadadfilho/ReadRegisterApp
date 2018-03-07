package com.example.jesus.readregister.App;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.jesus.readregister.Conta;
import com.example.jesus.readregister.Livro;
import com.example.jesus.readregister.R;

import java.util.Calendar;

import io.objectbox.Box;
import model.App;

public class CasdastroLivrosActivity extends AppCompatActivity {

    private EditText editTitulo;
    private EditText editGenero;
    private EditText editAutor;
    private EditText editPaginas, editPaginaAtual;
    private EditText editData, editAno;
    private EditText editDataTerm;
    private ImageView editImageData, switchGenero;
    private EditText  editTag;
    private int year,month,day;
    private RadioButton lido,lendo,desejo;

    private Box<Livro> livrosBox;
    private Box<Conta> contasBox;
    private Livro livro;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casdastro_livros);

        setupView();

        Intent intent = getIntent();
        long livroId = intent.getLongExtra("livroId",-1);
        SharedPreferences preferences = getSharedPreferences("reader.file", MODE_PRIVATE);
        userId = preferences.getLong("userId",userId);
        livrosBox = ((App)getApplication()).getBoxStore().boxFor(Livro.class);
        contasBox = ((App)getApplication()).getBoxStore().boxFor(Conta.class);
        editGenero.setEnabled(false);
        Toast.makeText(this, ""+userId, Toast.LENGTH_SHORT).show();

        if(livroId == -1){
            livro = new Livro();
        }else{
            livro = livrosBox.get(livroId);
            preencher(livro);
        }

    }

    /*private Conta getUsuarioLogado() {
        long id = getIntent().getLongExtra("contasId", -1);
        return contasBox.get(id);
    }*/

    private void preencher(Livro livro) {
        editTitulo.setText(livro.getTitulo());
        editGenero.setText(livro.getGenero());
        editAutor.setText(livro.getAutor());
        editData.setText(livro.getData());
        editAno.setText(livro.getAno());
        editDataTerm.setText(livro.getDataTermino());
        editPaginas.setText("" +livro.getPaginas());
        editPaginaAtual.setText("" +livro.getPaginaAtual());
        editTag.setText(livro.getTag());
    }

    private void setupView() {
        editTitulo = findViewById(R.id.editTitulo);
        editGenero = findViewById(R.id.editGenero);
        editAutor = findViewById(R.id.editAutor);
        editPaginas = findViewById(R.id.editPaginas);
        editAno = findViewById(R.id.editAno);
        editPaginaAtual = findViewById(R.id.editPaginasAtual);
        editData = findViewById(R.id.editDataIni);
        editDataTerm = findViewById(R.id.editDataTerm);
        editImageData = findViewById(R.id.editImageDataIni);
        editTag = findViewById(R.id.editTag);
        switchGenero = findViewById(R.id.switch_genero);
        lido = findViewById(R.id.lido);
        lendo = findViewById(R.id.lendo);
        desejo = findViewById(R.id.desejo);
    }


    public void showDatePickerDialog(View v) {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        if(v == findViewById(R.id.editImageDataIni)){
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editData.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, year, month, day);
            datePickerDialog.show();
        }else if (v == findViewById(R.id.editImageDataTerm)){
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editDataTerm.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, year, month, day);
            datePickerDialog.show();
        }
    }



    public void salvar(View view) {
        if(!editTitulo.getText().toString().equals("")
                && !editGenero.getText().toString().equals("")
                && !editAutor.getText().toString().equals("")
                && !editData.getText().toString().equals("")
                && !editPaginas.getText().toString().equals("")
                && !editAno.getText().toString().equals("")
                &&  !editPaginaAtual.getText().toString().equals("")){

            String titulo = editTitulo.getText().toString();
            String genero = editGenero.getText().toString();
            String autor = editAutor.getText().toString();
            String data = editData.getText().toString();
            String dataTerm = editDataTerm.getText().toString();
            String ano = editAno.getText().toString();
            int paginas = Integer.parseInt(editPaginas.getText().toString());
            int paginaAtual = Integer.parseInt(editPaginaAtual.getText().toString());
            String tag = editTag.getText().toString();

            livro.setTitulo(titulo);
            livro.setData(data);
            livro.setAutor(autor);
            livro.setGenero(genero);
            livro.setPaginas(paginas);
            livro.setPaginaAtual(paginaAtual);
            livro.setTag(tag);
            livro.setDataTermino(dataTerm);
            livro.setAno(ano);
            livro.getDono().setTarget(contasBox.get(userId));
            if (lido.isChecked()){
                livro.setSituacion("lido");
            }else if (lendo.isChecked()){
                livro.setSituacion("lendo");
            }else if (desejo.isChecked()){
                livro.setSituacion("desejo");
            }
        }else {
            Toast.makeText(this,"Preencha todos os campos!",Toast.LENGTH_SHORT).show();
            return;
        }
        livrosBox.put(livro);
        Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show();
        finish();
    }

    public void changeGenero(View view) {
        PopupMenu popup = new PopupMenu(this,view);
        popup.getMenuInflater().inflate(R.menu.generos_options,popup.getMenu());
        popup.setOnMenuItemClickListener((menuItem -> {
            if(menuItem.getItemId() == R.id.açaoM){
                editGenero.setText("açao");
            }
            if(menuItem.getItemId() == R.id.aventuraM){
                editGenero.setText("aventura");
            }
            if(menuItem.getItemId() == R.id.terrorM){
                editGenero.setText("terror");
            }
            if(menuItem.getItemId() == R.id.dramaM){
                editGenero.setText("drama");
            }
            if(menuItem.getItemId() == R.id.outroM){
                editGenero.setEnabled(true);
            }
            return false;
    }));
        popup.show();
    }


}
