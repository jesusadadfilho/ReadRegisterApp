package model.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.jesus.readregister.App.CasdastroLivrosActivity;
import com.example.jesus.readregister.App.LivroDetail;
import com.example.jesus.readregister.Livro;
import com.example.jesus.readregister.R;

import java.util.Collections;
import java.util.List;

import io.objectbox.Box;

public class LivrosAdapter extends RecyclerView.Adapter<LivrosAdapter.LivrosViewHolder> {

    //private List<Livro> livroList;
    private Context context;
    private List<Livro> livroList;
    private Box<Livro> livrosBox;

    public LivrosAdapter(Context context, List<Livro> livroList,Box<Livro> livrosBox) {
        this.context = context;
        Collections.sort(livroList);
        this.livrosBox = livrosBox;
        this.livroList = livroList;
    }

    public LivrosAdapter(Context context, Box<Livro> box) {
        this.context = context;
        this.livrosBox = box;
        this.livroList = box.getAll();
        Collections.sort(this.livroList);
    }

    @Override
    public LivrosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);

        View linha = inflater.inflate(R.layout.activity_rv_item_livro, parent, false);

        return new LivrosViewHolder(linha) ;
    }

    @Override
    public void onBindViewHolder(LivrosViewHolder holder, int position) {
        Livro livro = this.livroList.get(position);

        holder.titulo.setText(livro.getTitulo());
        holder.nPaginas.setText("" + livro.getPaginaAtual());
        holder.autor.setText(livro.getAutor());

        holder.itemView.setOnLongClickListener( (View view) -> {
            PopupMenu popup = new PopupMenu(context,view);
            popup.getMenuInflater().inflate(R.menu.options_livro,popup.getMenu());
            popup.setOnMenuItemClickListener((menuItem -> {
                if (menuItem.getItemId() == R.id.edit){
                    final Intent intent = new Intent(context,CasdastroLivrosActivity.class);
                    intent.putExtra("livroId", livro.getId());
                    context.startActivity(intent);
                }
                else if(menuItem.getItemId() == R.id.delete){
                    livrosBox.remove(livro.getId());
                    livroList.remove(position);
                    notifyDataSetChanged();
                }
                return false;
            }));
            popup.show();
            return true;
        });

        holder.itemView.setOnClickListener( (view -> {
            final Intent intent = new Intent(context,LivroDetail.class);
            intent.putExtra("livroId", livro.getId());
            context.startActivity(intent);
        }));
    }

    @Override
    public int getItemCount() {
        return this.livroList.size();
    }

    public class LivrosViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        private TextView nPaginas;
        private TextView autor;
        public LivrosViewHolder(View itemView) {
           super(itemView);
           titulo = itemView.findViewById(R.id.titulo);
           nPaginas = itemView.findViewById(R.id.progresso);
           autor = itemView.findViewById(R.id.autor);
       }
   }
    public void setLivros(List<Livro> livroList) {
        this.livroList = livroList;
    }
}
