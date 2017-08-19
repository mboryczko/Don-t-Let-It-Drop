package com.example.majkiel.jumpproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.majkiel.jumpproject.Activities.HighScoreActivity;
import com.example.majkiel.jumpproject.Objects.Highscore;
import com.example.majkiel.jumpproject.R;

import java.util.List;

/**
 * Created by mjbor on 01.08.2017.
 */

public class HighscoresAdapter extends ArrayAdapter<Highscore> {

    public HighscoresAdapter(Context context, List<Highscore> highscores) {
        super(context, 0, highscores);

        if(highscores.size() == 0){
            Toast.makeText(getContext(), "You don't have any higscores saved yet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Highscore highscore = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.highscore_item, parent, false);
        }

        // Lookup view for data population
        TextView tvUsername = (TextView) convertView.findViewById(R.id.usernameTexView);
        TextView tvPoints = (TextView) convertView.findViewById(R.id.userPointsTextView);
        TextView tvDate = (TextView) convertView.findViewById(R.id.dateTextView);

        // Populate the data into the template view using the data object
        if(highscore.getName().equals(""))
            tvUsername.setText("-");
        else
            tvUsername.setText(highscore.getName());
        tvPoints.setText("Points: " + Integer.toString(highscore.getScore()));
        tvDate.setText(highscore.getDate());

        // Return the completed view to render on screen
        return convertView;
    }


}
