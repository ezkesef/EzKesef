package com.hickry.ezkesef;

import android.view.View;
import android.widget.TextView;

public class Points implements View.OnClickListener {
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public Points(int amount){
        this.amount = amount;

    }

    @Override
    public void onClick(View v) {
    amount++;
    }
}
