package com.example.appmobile.Common;

import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;


import io.reactivex.annotations.NonNull;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
    private int itemoffset;

    public ItemOffsetDecoration(int itemoffset) {
        this.itemoffset = itemoffset;
    }

    public ItemOffsetDecoration(@NonNull Context context,@DimenRes int dimens){
        this(context.getResources().getDimensionPixelSize(dimens));

    }

    @Override
    public void getItemOffsets(Rect outRect, View view,RecyclerView parent,RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(itemoffset,itemoffset,itemoffset,itemoffset);
    }
}
