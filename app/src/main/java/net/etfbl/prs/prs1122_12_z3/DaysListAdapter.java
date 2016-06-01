package net.etfbl.prs.prs1122_12_z3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.etfbl.prs.prs1122_12_z3.dao.Day;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public static final String D01 = "01d";
    public static final String D02 = "02d";
    public static final String D03 = "03d";
    public static final String D04 = "04d";
    public static final String D05 = "09d";
    public static final String D06 = "10d";
    public static final String D07 = "11d";
    public static final String D08 = "13d";
    public static final String D09 = "50d";
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
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageDrawable(mContext.getResources().getDrawable(getDrawableId(day.getWeather().getIcon())));

        TextView date = (TextView) view.findViewById(R.id.dateField);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        String text = format.format(day.getDate());
        date.setText(text);

        TextView tempMin = (TextView) view.findViewById(R.id.temp_min);
        tempMin.setText(String.format("%.2f", day.getTemperature().getMin()));

        TextView tempMax = (TextView) view.findViewById(R.id.temp_max);
        tempMax.setText(String.format("%.2f", day.getTemperature().getMax()));
        return view;
    }

    public static int getDrawableId(String icon) {
        int id ;
        switch (icon) {
            case D01:
                id = R.drawable.w01d;
                break;
            case D02:
                id = R.drawable.w02d;
                break;
            case D03:
                id = R.drawable.w03d;
                break;
            case D04:
                id = R.drawable.w04d;
                break;
            case D05:
                id = R.drawable.w09d;
                break;
            case D06:
                id = R.drawable.w10d;
                break;
            case D07:
                id = R.drawable.w11d;
                break;
            case D08:
                id = R.drawable.w13d;
                break;
            case D09:
                id = R.drawable.w50d;
                break;
            default:
                id = R.drawable.w10d;
                break;
        }
        return id;
    }

    public void setList(List<Day> list) {
        mList = list;
        notifyDataSetInvalidated();
    }


}
