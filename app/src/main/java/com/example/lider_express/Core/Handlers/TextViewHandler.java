package com.example.lider_express.Core.Handlers;

import android.view.View;
import android.widget.Button;

import com.example.lider_express.Core.Item;
import com.example.lider_express.Core.Types;

public class TextViewHandler {

    public static void setActionSingleHidden(Item mainItem, final Item hiddenItem) {
        mainItem.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("PRESSSSSSSSSSSS)" + "   " + hiddenItem.getVisibility() + "    " + View.GONE + "    " + View.VISIBLE);
                if(hiddenItem.getVisibility() == View.GONE){
                    hiddenItem.hidden(View.VISIBLE);
                }else{
                    System.out.println("VISIBLE_______");
                    hiddenItem.hidden(View.GONE);
                }
            }
        });
    }
}
