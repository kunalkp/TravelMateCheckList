package com.travelmatechecklist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KunalPardeshi on 26-Oct-17.
 */

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.MyViewHolder> {

    private ArrayList<CheckListItem> checkListItems;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public CheckBox checkedItem;
        public Button delete;

        public MyViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.textView_item_name);
            checkedItem = view.findViewById(R.id.checkbox_item_status);
            delete = view.findViewById(R.id.button_item_delete);
        }
    }

    public CheckListAdapter(ArrayList<CheckListItem> checkListItems) {
        this.checkListItems = checkListItems;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CheckListItem checkListItem = checkListItems.get(position);
        holder.itemName.setText(checkListItem.getItemName());
        holder.checkedItem.setChecked(checkListItem.itemChecked);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkListItems.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return checkListItems.size();
    }
}
