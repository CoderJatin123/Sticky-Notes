package com.example.pdfviwer.stickynotes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.zip.Inflater;

public class Notes_Adapter extends RecyclerView.Adapter<Notes_Adapter.ViewHolder> {
    Context context;
    List<User> NotesList;
    User temp;
    @NonNull
    @Override
    public Notes_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_list_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Notes_Adapter.ViewHolder holder, int position) {

        if(NotesList.get(position).getDrwable()!=null){
            holder.cardView.setCardBackgroundColor(Color.parseColor((NotesList.get(position).getDrwable())));
        }
        holder.Title.setText(NotesList.get(position).getTitle().toString());
        int x=position;
        holder.Body.setText(NotesList.get(position).getBody().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Note_Pad.class);
                intent.putExtra("uid",NotesList.get(x).getUid());
                intent.putExtra("title",NotesList.get(x).Title);
                intent.putExtra("body",NotesList.get(x).Body);
                intent.putExtra("drawable",NotesList.get(x).getDrwable());
                intent.putExtra("Data",NotesList.get(x).getDate());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(context, "Notes Deleted Successfully", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        holder.morebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(context,view);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.delet:
                                Toast.makeText(context, "Delete Clicked", Toast.LENGTH_SHORT).show();
                                temp=NotesList.get(x);
                                deleteItem(x,view);

                                break;
                        }
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return NotesList.size();
    }

    public Notes_Adapter(Context context, List<User> notesList) {
        this.context = context;
        NotesList = notesList;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton morebutton;
        CardView cardView;
        TextView Title,Body;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardview_item);
            Title=itemView.findViewById(R.id.Title_Text_item);
            Body=itemView.findViewById(R.id.Body_Text_item);
            morebutton=itemView.findViewById(R.id.more_button);
        }
    }
    void Noticed(List<User> x){
        NotesList=x;
        notifyDataSetChanged();
    }
    private class DeleteAcynctask extends AsyncTask<User,Void,Void> {

        @Override
        protected Void doInBackground(User... users) {
            AppDatabase.getInstance(context.getApplicationContext()).userDao().delete(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            notifyDataSetChanged();
        }
    }
    private void deleteItem(int position,View views){
        final Boolean[] bool = {false};
        NotesList.remove(position);
        notifyDataSetChanged();
        notifyItemChanged(position,NotesList.size());
        Snackbar.make(views,"Undo Deletion Of:"+temp.getTitle(),1000).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotesList.add(position,temp);
                notifyItemInserted(position);
                notifyItemRangeChanged(position,NotesList.size());
                bool[0] =true;
            }
        }).setActionTextColor(context.getResources().getColor(android.R.color.holo_purple))
                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        if (!bool[0]){
                            new DeleteAcynctask().execute(temp);
                        }

                    }
                })
                .show();
    }


}
