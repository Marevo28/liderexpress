package com.example.lider_express.Core.Handlers;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.lider_express.Core.DefectTree.CustomListAdapter;
import com.example.lider_express.Core.DefectTree.DataList;
import com.example.lider_express.Core.Item;

import java.util.HashMap;
import java.util.List;

public class ExpandableListViewHandle {



    public static void addViewToAdapter(Activity activity, ExpandableListView expListView, DataList dataList, List<String> listTitle, String title){
        HashMap<String, List<String>> expListDetail = dataList.loadData();
        listTitle.add(title);
        ExpandableListAdapter expListAdapter = new CustomListAdapter(activity, listTitle, expListDetail);
        expListView.setAdapter(expListAdapter);
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {
        // Set Height dynamic ListView
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;
        } else {
            return false;
        }
    }

    public static void setExpand(final Item item){
        ((ExpandableListView) item.getView()).setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                setListViewHeightBasedOnItems((ExpandableListView) item.getView());
            }
        });
    }

    public static void setCollapse(final Item item){
        ((ExpandableListView) item.getView()).setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                setListViewHeightBasedOnItems((ExpandableListView) item.getView());
            }
        });
    }

    public static void setChildClick(final Item item){
        ((ExpandableListView) item.getView()).setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }


}
