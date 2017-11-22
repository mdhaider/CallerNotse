package com.bluerocket.callernotse.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluerocket.callernotse.R;
import com.bluerocket.callernotse.models.NoteModel;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<NoteModel> noteModelList;
    private View.OnLongClickListener longClickListener;

    public RecyclerViewAdapter(List<NoteModel> noteModelList, View.OnLongClickListener longClickListener) {
        this.noteModelList = noteModelList;
        this.longClickListener = longClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        NoteModel noteModel = noteModelList.get(position);
        holder.itemTextView.setText(noteModel.getItemName());
        holder.nameTextView.setText(noteModel.getPersonName());
        holder.dateTextView.setText(noteModel.getBorrowDate().toLocaleString().substring(0, 11));
        holder.itemView.setTag(noteModel);
        holder.itemView.setOnLongClickListener(longClickListener);
    }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }

    public void addItems(List<NoteModel> noteModelList) {
        this.noteModelList = noteModelList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTextView;
        private TextView nameTextView;
        private TextView dateTextView;

        RecyclerViewHolder(View view) {
            super(view);
            itemTextView = view.findViewById(R.id.itemTextView);
            nameTextView = view.findViewById(R.id.nameTextView);
            dateTextView = view.findViewById(R.id.dateTextView);
        }
    }
}