package net.etfbl.prs.prs1122_12_z3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/****************************************************************************
 * Copyright (c) 2016 Elektrotehnicki fakultet
 * Patre 5, Banja Luka
 * <p>
 * All Rights Reserved
 * <p>
 * \file DaysListAdapter.java
 * \brief
 * This file contains a source code for class TaskAdapter
 * <p>
 * Created on 27.05.2016
 *
 * @Author Milan Maric
 * <p>
 * \notes
 * <p>
 * <p>
 * \history
 * <p>
 **********************************************************************/
public class DaysListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Day> mList = new ArrayList<>();

    public DaysListAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public DaysListAdapter(Context mContext, List<Day> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < mList.size())
            return mList.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.day_layout, null);
        }
        Day day = (Day) getItem(position);
        TextView date = (TextView) view.findViewById(R.id.dateField);
        DateFormat dateFormat = DateFormat.getDateInstance();
        String text = dateFormat.format(day.getDate());
        date.setText(text);
        TextView tempMin = (TextView) view.findViewById(R.id.temp_min);
        tempMin.setText(Double.toString(day.getTemperature().getMin()));
        return view;
    }

    public void setList(List<Day> list) {
        mList = list;
        notifyDataSetInvalidated();
    }


}
