package br.ufpe.cin.if710.podcast.services;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SyncFailedException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import br.ufpe.cin.if710.podcast.db.PodcastProvider;
import br.ufpe.cin.if710.podcast.db.PodcastProviderContract;

/**
 * Created by Daniel Maida on 16/10/2017.
 */

public class DownloadService extends IntentService{

    public static final String DOWNLOAD_COMPLETE = "br.ufpe.cin.if710.services.action.DOWNLOAD_COMPLETE";

    public DownloadService(){
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            //checar se tem permissao... Android 6.0+
            Log.d("baixando","iniciou o service");
            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            root.mkdirs();
            File output = new File(root, intent.getData().getLastPathSegment());
            if (output.exists()) {
                output.delete();
            }
            URL url = new URL(intent.getData().toString());
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            FileOutputStream fos = new FileOutputStream(output.getPath());
            BufferedOutputStream out = new BufferedOutputStream(fos);
            try {
                InputStream in = c.getInputStream();
                byte[] buffer = new byte[8192];
                int len = 0;
                while ((len = in.read(buffer)) >= 0) {
                    out.write(buffer, 0, len);
                }
                out.flush();
            }
            finally {
                fos.getFD().sync();
                out.close();
                c.disconnect();
            }
            String downloadLink = intent.getStringExtra("download_link");
            URI uri = output.toURI();
            ContentResolver contentResolver = getContentResolver();

            ContentValues values = new ContentValues();
            values.put(PodcastProviderContract.EPISODE_FILE_URI,uri.toString());
            int result = contentResolver.update(
                    PodcastProviderContract.EPISODE_LIST_URI,
                    values,
                    PodcastProviderContract.EPISODE_DOWNLOAD_LINK + " =?",
                    new String[]{downloadLink}
            );

            Intent broadcastInfo = new Intent(DOWNLOAD_COMPLETE);
            broadcastInfo.putExtra("position", intent.getIntExtra("position",-1));

            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastInfo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
