package com.example.medalert;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.content.Context;
import androidx.core.content.ContextCompat;





public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
    private ArrayList<Model> mData;
    private Context mContext;

    public myAdapter(Context context, ArrayList<Model> data)
    {
        this.mData = data;
        this.mContext = context;
    }

    ///////////////////////////




    // Method to enable swipe-to-delete functionality
    public void enableSwipeToDelete(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                deleteItem(position);
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    // Method to delete item from ArrayList and RecyclerView
    private void deleteItem(int position)
    {
        mData.remove(position);
        notifyItemRemoved(position);

        String title = mData.get(position).getTitle(); // Assuming title is the unique identifier for your reminders
        SQLiteDatabase database = new dbManager(mContext).getWritableDatabase();
        database.delete("tbl_reminder", "title = ?", new String[]{title});
        database.close();
    }
       ////////////////////////
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView dateTextView;
        public TextView timeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            dateTextView = itemView.findViewById(R.id.date_text_view);
            timeTextView = itemView.findViewById(R.id.time_text_view);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = mData.get(position);
        holder.titleTextView.setText("Pill : " + model.getTitle());
        holder.titleTextView.setTextColor(ContextCompat.getColor(mContext, R.color.pill_name_color));
        holder.dateTextView.setText("DATE : " + model.getDate());
        holder.dateTextView.setTextColor(ContextCompat.getColor(mContext, R.color.date_color));
        holder.timeTextView.setText("TIME : " + model.getTime());
        holder.timeTextView.setTextColor(ContextCompat.getColor(mContext, R.color.time_color));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
