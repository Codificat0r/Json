package com.example.json.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.json.R;
import com.example.json.pojo.Repo;

import java.util.ArrayList;

/**
 * Created by usuario on 6/02/18.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoHolder> {

    private ArrayList<Repo> repos;
    private OnClickListener listener;

    public interface OnClickListener {
        void OnItemClicked(Repo clicked);
        void OnItemLongClicked(Repo clicked);
    }

    public RepoAdapter(OnClickListener listener) {
        repos = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View repoView = inflater.inflate(R.layout.item_repo, null);

        RepoHolder holder = new RepoHolder(repoView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RepoHolder holder, final int position) {
        Repo tmp = repos.get(position);

        holder.txvNombre.setText(tmp.getName());
        holder.txvDescripcion.setText(tmp.getDescription());
        holder.txvCreacion.setText(tmp.getCreatedAt());

        //Informamos a quien quiera escuchar que se ha hecho un click o un long click sobre una de las vistas.
        holder.vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClicked(getRepo(position));
            }
        });

        holder.vista.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.OnItemLongClicked(getRepo(position));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public class RepoHolder extends RecyclerView.ViewHolder {
        private TextView txvNombre;
        private TextView txvDescripcion;
        private TextView txvCreacion;
        private View vista;

        public RepoHolder(View v) {
            super(v);
            txvNombre = v.findViewById(R.id.txvNombre);
            txvDescripcion = v.findViewById(R.id.txvDescripcion);
            txvCreacion = v.findViewById(R.id.txvCreacion);
            vista = v;
        }
    }

    public void addRepos(ArrayList<Repo> repos) {
        this.repos.addAll(repos);
        notifyDataSetChanged();
    }

    public void clear() {
        this.repos.clear();
        notifyDataSetChanged();
    }

    public Repo getRepo(int position) {
        try {
            return repos.get(position);
        } catch (IndexOutOfBoundsException e) {

        }
        return null;
    }
}

