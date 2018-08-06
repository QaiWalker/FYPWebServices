package com.rp.qai.fypwebservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PoemAdapter extends ArrayAdapter<Poem> {
    private ArrayList<Poem> poems;
    private Context context;
    private TextView tvTitle;
    Poem poem;

    public PoemAdapter(Context context, int resource, ArrayList<Poem> objects){
        super(context, resource, objects);
        poems = objects;
        this.context = context;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // The usual way to get the LayoutInflater object to
        //  "inflate" the XML file into a View object
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // "Inflate" the row.xml as the layout for the View object
        View rowView = inflater.inflate(R.layout.row, parent, false);


        tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
        Poem currentPoem = poems.get(position);
        tvTitle.setText(currentPoem.getTitle());

        return rowView;
    }
}
