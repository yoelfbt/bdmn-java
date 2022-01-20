package com.yujiro.bdmn_java;

import static com.yujiro.bdmn_java.DatabaseHelper.TABLE_NAME;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ModelViewHolder> {
    Context context;
    ArrayList<UserModel>modelArrayList = new ArrayList<>();
    SQLiteDatabase database;


    public UserAdapter(Context context, int uset_item, ArrayList<UserModel> modelArrayList, SQLiteDatabase database) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        this.database = database;
    }


    public class ModelViewHolder extends RecyclerView.ViewHolder {
        TextView id,username,email,role;
        Button edit,delete;
        public ModelViewHolder(@NonNull View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.tvId);
            username = (TextView) itemView.findViewById(R.id.tvUsername);
            email = (TextView) itemView.findViewById(R.id.tvEmail);
            role = (TextView) itemView.findViewById(R.id.tvRole);
            edit = (Button) itemView.findViewById(R.id.bt_update);
            delete = (Button) itemView.findViewById(R.id.bt_delete);
        }
    }

    @NonNull
    @Override
    public UserAdapter.ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.uset_item, null);
        return new ModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ModelViewHolder holder, int position) {
        final UserModel model = modelArrayList.get(position);
        holder.id.setText(model.getId());
        holder.username.setText(model.getUsername());
        holder.email.setText(model.getEmail());
        holder.role.setText(model.getRole());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id",model.getId());
                bundle.putString("username", model.getUsername());
                bundle.putString("email",model.getEmail());
                bundle.putString("role",model.getRole());
                Intent intent = new Intent(context, SignUpActivity.class);
                intent.putExtra("userdata",bundle);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            @Override
            public void onClick(View view) {
                database = databaseHelper.getReadableDatabase();
                long delete = database.delete(TABLE_NAME, "id="+model.getId(),null);
                if (delete!=-1){
                    Toast.makeText(context,"Deleted", Toast.LENGTH_SHORT).show();
                    modelArrayList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

}
