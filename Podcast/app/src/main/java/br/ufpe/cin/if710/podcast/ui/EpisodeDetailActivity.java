package br.ufpe.cin.if710.podcast.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import br.ufpe.cin.if710.podcast.R;

public class EpisodeDetailActivity extends Activity {

    private TextView title;
    private TextView date;
    private TextView description;
    private TextView downloadLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_detail);

        title = findViewById(R.id.episode_title);
        date = findViewById(R.id.episode_date);
        description = findViewById(R.id.episode_desc);
        downloadLink = findViewById(R.id.episode_download_link);

        Intent i = getIntent();
        title.setText("Title: "+ i.getStringExtra("title"));
        date.setText("Date: "+ i.getStringExtra("date"));
        description.setText("Description: " + i.getStringExtra("desc"));
        downloadLink.setText("Download Link: " + i.getStringExtra("dlink"));
    }
}
