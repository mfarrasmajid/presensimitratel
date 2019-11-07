package com.example.presensimitratel.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.presensimitratel.Model.DataMonitoring;
import com.example.presensimitratel.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MonitoringAdapter extends RecyclerView.Adapter<MonitoringAdapter.MyViewHolder> {

    private List<DataMonitoring> monitoringList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, datetime_in, distance_in;
        public CircleImageView nik_tg;

        public MyViewHolder(View view) {
            super(view);
            nik_tg = (CircleImageView) view.findViewById(R.id.profile_image);
            name = (TextView) view.findViewById(R.id.name);
            datetime_in = (TextView) view.findViewById(R.id.jam);
            distance_in = (TextView) view.findViewById(R.id.distance);
        }
    }


    public MonitoringAdapter(List<DataMonitoring> monitoringList) {
        this.monitoringList = monitoringList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_monitoring, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DataMonitoring monitoring = monitoringList.get(position);
        String url = holder.itemView.getResources().getString(R.string.profile_image) + monitoring.getNIK() + ".jpg";
//        Bitmap myImage = getBitmapFromURL(url);
//        if (myImage != null){
//            holder.nik_tg.setImageBitmap(myImage);
            Picasso.get().load(url).resize(300,300).placeholder(R.drawable.def_user).error(R.drawable.def_user).into(holder.nik_tg);
//        }
        holder.name.setText(monitoring.getName());
        String time, time_in;
        if (monitoring.getDatetimeIn() != null){
            time = monitoring.getDatetimeIn().split(" ")[1];
            time_in = time.split(":")[0] + ":" + time.split(":")[1];
        } else {
            time_in = "00:00";
        }
        holder.datetime_in.setText(time_in);
        if (monitoring.getDistanceIn() != null){
            holder.distance_in.setText(monitoring.getDistanceIn());
        } else {
            holder.distance_in.setText("-");
        }
    }

    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return monitoringList.size();
    }
}
