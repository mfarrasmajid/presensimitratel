package com.example.presensimitratel.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.presensimitratel.R;

public class DataKaryawanAdapter extends ArrayAdapter {
    private final Activity context;
    private final String[] labelArray;
    private final String[] dataArray;
    public DataKaryawanAdapter(Activity context, String[] labelArray, String[] dataArray){
        super(context, R.layout.data_karyawan, dataArray);
        this.context = context;
        this.labelArray = labelArray;
        this.dataArray = dataArray;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.data_karyawan,null, true);

        TextView labelField = rowView.findViewById(R.id.dataLabel);
        TextView dataField = rowView.findViewById(R.id.data);

        labelField.setText(labelArray[position]);
        dataField.setText(dataArray[position]);

        return rowView;

    }
}
