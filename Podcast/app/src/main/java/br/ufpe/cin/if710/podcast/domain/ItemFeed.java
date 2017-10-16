package br.ufpe.cin.if710.podcast.domain;

import android.database.Cursor;

import br.ufpe.cin.if710.podcast.db.PodcastProviderContract;

public class ItemFeed {
    private final String title;
    private final String link;
    private final String pubDate;
    private final String description;
    private final String downloadLink;
    public String episode_uri;


    public ItemFeed(String title, String link, String pubDate, String description, String downloadLink, String episode_uri) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.description = description;
        this.downloadLink = downloadLink;
        this.episode_uri = episode_uri;
    }

    public ItemFeed(Cursor cursor){

        int title_index = cursor.getColumnIndexOrThrow(PodcastProviderContract.EPISODE_TITLE);
        int date_index = cursor.getColumnIndexOrThrow(PodcastProviderContract.EPISODE_DATE);
        int desc_index = cursor.getColumnIndexOrThrow(PodcastProviderContract.EPISODE_DESC);
        int downloadlink_index = cursor.getColumnIndexOrThrow(PodcastProviderContract.EPISODE_DOWNLOAD_LINK);
        int link_index = cursor.getColumnIndexOrThrow(PodcastProviderContract.EPISODE_LINK);
        int episode_uri_index = cursor.getColumnIndexOrThrow(PodcastProviderContract.EPISODE_FILE_URI);


        this.title = cursor.getString(title_index);
        this.link = cursor.getString(link_index);
        this.pubDate = cursor.getString(date_index);
        this.description = cursor.getString(desc_index);
        this.downloadLink = cursor.getString(downloadlink_index);
        this.episode_uri = cursor.getString(episode_uri_index);
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getDescription() {
        return description;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    @Override
    public String toString() {
        return title;
    }
}