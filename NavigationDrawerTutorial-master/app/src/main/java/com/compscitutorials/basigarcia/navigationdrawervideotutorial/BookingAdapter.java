package com.compscitutorials.basigarcia.navigationdrawervideotutorial;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suraj on 13-11-2017.
 */
public class BookingAdapter extends ArrayAdapter {
    public BookingAdapter(Context context, int resource) {
        super(context, resource);
    }

    List list=new ArrayList();
    @Override
    public void add(Object object) {
        super.add(object);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
