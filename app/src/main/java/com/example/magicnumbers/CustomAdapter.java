package com.example.magicnumbers;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<NumberModel> {
        Context context;

    public CustomAdapter(@NonNull Context context, List<NumberModel> numbers) {
        super(context, 0, numbers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NumberModel numberModel = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }
        TextView name = convertView.findViewById(R.id.name);
        ImageView image = convertView.findViewById(R.id.list_image);
        name.setText(numberModel.name);
        Picasso.with(convertView.getContext()).load(numberModel.image)
                .into(image);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println();
                Intent intent = new Intent(view.getContext(), DetailWindowNumber.class);
                intent.putExtra("name", numberModel.getName());
                view.getContext().startActivity(intent);

            }
        });

        return convertView;
    }

    //=========
/*    List<NumberModel> numberModelList;
    Context context;

    public CustomAdapter(Context context,List<NumberModel> numberModelList) {
        this.numberModelList = numberModelList;
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return numberModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        NumberModel numberModel = numberModelList.get(position);
        if (view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.list_row, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println();
                }
            });
            TextView name = view.findViewById(R.id.name);
            ImageView image = view.findViewById(R.id.list_image);
            name.setText(numberModel.name);
            Picasso.with(context).load(numberModel.image)
                    .into(image);
        }
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return numberModelList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }*/
    //=========
}
