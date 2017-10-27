package com.travelmatechecklist;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckListAdapter checkListAdapter;
    private ArrayList<CheckListItem> checkListItemArrayList = new ArrayList<>();
    private Button buttonAddItem;
    private String PREF_NAME_CHECK_LIST = "CheckList";
    private String CHECK_LIST_ITEMS = "CheckListItems";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_items);
        buttonAddItem = findViewById(R.id.button_add_item);

        checkListItemArrayList = getCheckListItemArrayList();
//        if (checkListItemArrayList != null && checkListItemArrayList.size() > 0) {
//            checkListAdapter = new CheckListAdapter(checkListItemArrayList);
//        }
        checkListAdapter = new CheckListAdapter(checkListItemArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(checkListAdapter);

        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.dialog_add_item, null);

                final EditText editText_item_name = view.findViewById(R.id.editText_item_name);

                builder.setView(view)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String itemName = editText_item_name.getText().toString().trim();
                                prepareCheckList(itemName);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.create();
                builder.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ArrayList<CheckListItem> listItems = getCheckListItemArrayList();
//        if (listItems.size() > 0){
//            checkListItemArrayList = listItems;
//        }
    }

    private void prepareCheckList(String itemName) {
        CheckListItem checkListItem;

        checkListItem = new CheckListItem(false, itemName);
        checkListItemArrayList.add(checkListItem);
        saveCheckList(checkListItemArrayList);
        checkListAdapter.notifyDataSetChanged();
    }

    public void saveCheckList(ArrayList<CheckListItem> checkListItems) {
        SharedPreferences mPrefs = this.getSharedPreferences(PREF_NAME_CHECK_LIST, 0);
        SharedPreferences.Editor editor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(checkListItems);
        editor.putString(CHECK_LIST_ITEMS, json);
        editor.apply();
    }

    public ArrayList<CheckListItem> getCheckListItemArrayList() {
        SharedPreferences mPrefs = this.getSharedPreferences(PREF_NAME_CHECK_LIST, 0);
        ArrayList<CheckListItem> itemList;

        Gson gson = new Gson();
        String json = mPrefs.getString(CHECK_LIST_ITEMS, "");
        if (json.isEmpty()) {
            itemList = new ArrayList<CheckListItem>();
        } else {
            Type type = new TypeToken<List<CheckListItem>>() {
            }.getType();
            itemList = gson.fromJson(json, type);
        }
        return itemList;
    }
}
