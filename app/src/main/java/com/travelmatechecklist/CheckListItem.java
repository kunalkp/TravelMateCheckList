package com.travelmatechecklist;

/**
 * Created by KunalPardeshi on 26-Oct-17.
 */

public class CheckListItem {

    boolean itemChecked;
    String itemName;

    public CheckListItem(boolean itemChecked, String itemName) {
        this.itemChecked = itemChecked;
        this.itemName = itemName;
    }

    public boolean isItemChecked() {
        return itemChecked;
    }

    public void setItemChecked(boolean itemChecked) {
        this.itemChecked = itemChecked;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
