package com.example.presensimitratel.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.presensimitratel.Model.DataUlangTahun;
import com.example.presensimitratel.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UlangTahunAdapter extends RecyclerView.Adapter<UlangTahunAdapter.MyViewHolder> {
    private List<DataUlangTahun> ulangTahunList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, unit, posisi;
        public CircleImageView nik;

        public MyViewHolder(View view) {
            super(view);
            nik = (CircleImageView) view.findViewById(R.id.profile_image);
            name = (TextView) view.findViewById(R.id.name);
            unit = (TextView) view.findViewById(R.id.unit);
            posisi= (TextView) view.findViewById(R.id.posisi);
        }
    }


    public UlangTahunAdapter(List<DataUlangTahun> ulangTahunList) {
        this.ulangTahunList = ulangTahunList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_ulang_tahun, parent, false);

        return new UlangTahunAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UlangTahunAdapter.MyViewHolder holder, int position) {
        DataUlangTahun ulangtahun = ulangTahunList.get(position);
        if (ulangtahun.getNIK() != null){
            String url = holder.itemView.getResources().getString(R.string.profile_image) + ulangtahun.getNIK() + ".jpg";
//            Bitmap myImage = getBitmapFromURL(url);
//            if (myImage != null){
//                holder.nik.setImageBitmap(myImage);
                Picasso.get().load(url).resize(300,300).placeholder(R.drawable.def_user).error(R.drawable.def_user).into(holder.nik);
//            }
        }
        if (ulangtahun.getName() != null){
            holder.name.setText(ulangtahun.getName());
        } else {
            holder.name.setText("-");
        }
        if (ulangtahun.getUnit() != null){
            holder.unit.setText(ulangtahun.getUnit());
        } else {
            holder.unit.setText("-");
        }
        if (ulangtahun.getPosisi() != null){
            holder.posisi.setText(ulangtahun.getPosisi());
        } else {
            holder.posisi.setText("-");
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
        return ulangTahunList.size();
    }
}
