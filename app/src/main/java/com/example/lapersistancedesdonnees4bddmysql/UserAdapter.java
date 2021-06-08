package com.example.lapersistancedesdonnees4bddmysql;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
{
    private final ArrayList<User> listeUser; //Notre tableau de données.

    //Le viewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        //On y déclare les objets e la vue qui seront chargés.
        public TextView tvUtilisateur;
        public TextView tvEmail;
        public TextView tvDDN;
        public TextView tvLocalite;

        //Constructeur du holder : le view holder a une référence à tous les widgets de la liste
        public ViewHolder(View v)
        {
            super(v);
            tvUtilisateur = (TextView) v.findViewById(R.id.username);
            tvEmail = (TextView) v.findViewById(R.id.email);
            tvDDN = (TextView) v.findViewById(R.id.ddn);
            tvLocalite = (TextView) v.findViewById(R.id.localite);
        }
    }
    //Constructeur de l'adapteur : initialisation de l'adapteur et des données.
    public UserAdapter(ArrayList<User> listUser) { listeUser = listUser;}

    //Chargement du layout et initialisation du viewholder
    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ligne_utilisateur,parent,false);

        return new ViewHolder(v);
    }

    //Lien entre viewHolder et données
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position)
    {
        Log.i("Warning","Position : "+position);
        holder.tvUtilisateur.setText(listeUser.get(position).getUsername());
        holder.tvEmail.setText(listeUser.get(position).getEmail());
        holder.tvDDN.setText(listeUser.get(position).getDDN());
        holder.tvLocalite.setText(listeUser.get(position).getLocalite());
    }

    @Override
    public int getItemCount() {
        return listeUser.size();
    }

}
