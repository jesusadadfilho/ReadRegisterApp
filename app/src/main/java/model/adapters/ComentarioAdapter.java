package model.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.jesus.readregister.App.AdicionarComentario;
import com.example.jesus.readregister.App.ExibirComentarioActivity;
import com.example.jesus.readregister.Comentario;
import com.example.jesus.readregister.R;

import java.util.Collections;
import java.util.List;

import io.objectbox.Box;

/**
 * Created by jesus on 06/03/2018.
 */

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ComentariosViewHolder> {


    private Context context;
    private List<Comentario> comentarioList;
    private Box<Comentario> comentariosBox;

    public ComentarioAdapter(Context context, List<Comentario> comentarioList, Box<Comentario> comentariosBox) {
        this.context = context;
        Collections.sort(comentarioList);
        this.comentarioList = comentarioList;
        this.comentariosBox = comentariosBox;
    }

    @Override
    public ComentariosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);

        View linha = inflater.inflate(R.layout.activity_item__cadastro, parent, false);

        return new ComentarioAdapter.ComentariosViewHolder(linha) ;
    }

    @Override
    public void onBindViewHolder(ComentariosViewHolder holder, int position) {
        Comentario comentario = this.comentarioList.get(position);
        holder.capitulo.setText("Capitulo: " + comentario.getCapitulo());
        //TODO: ver comentario
        holder.itemView.setOnLongClickListener( (View view) -> {
            PopupMenu popup = new PopupMenu(context,view);
            popup.getMenuInflater().inflate(R.menu.options_livro,popup.getMenu());
            popup.setOnMenuItemClickListener((menuItem -> {
                if (menuItem.getItemId() == R.id.edit){
                    final Intent intent = new Intent(context,AdicionarComentario.class);
                    intent.putExtra("comentarioId", comentario.getId());
                    context.startActivity(intent);
                }
                else if(menuItem.getItemId() == R.id.delete){
                    comentariosBox.remove(comentario.getId());
                    comentarioList.remove(position);
                    notifyDataSetChanged();
                }
                return false;
            }));
            popup.show();
            return true;
        });

        holder.itemView.setOnClickListener( (view -> {
            final Intent intent = new Intent(context,ExibirComentarioActivity.class);
            intent.putExtra("comentarioId", comentario.getId());
            context.startActivity(intent);
        }));


    }

    @Override
    public int getItemCount() {
        return this.comentarioList.size();
    }

    public class ComentariosViewHolder extends RecyclerView.ViewHolder{
        private TextView capitulo;
        public ComentariosViewHolder(View itemView) {
            super(itemView);
            capitulo = itemView.findViewById(R.id.capitulo);
        }
    }
}
