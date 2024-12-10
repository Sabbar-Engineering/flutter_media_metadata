package com.alexmercerind.flutter_media_metadata;

import android.media.MediaMetadataRetriever;

import java.util.HashMap;

public class MetadataRetriever extends MediaMetadataRetriever {
    public MetadataRetriever() {
        super();
    }

    public void setFilePath(String filePath) {
        setDataSource(filePath);
    }

    public HashMap<String, Object> getMetadata() {
        HashMap<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("trackName", extractMetadata(METADATA_KEY_TITLE));
        metadata.put("trackArtistNames", extractMetadata(METADATA_KEY_ARTIST));
        metadata.put("albumName", extractMetadata(METADATA_KEY_ALBUM));
        metadata.put("albumArtistName", extractMetadata(METADATA_KEY_ALBUMARTIST));
        String trackNumber = extractMetadata(METADATA_KEY_CD_TRACK_NUMBER);
        try {
            metadata.put("trackNumber", trackNumber.split("/")[0].trim());
            metadata.put("albumLength", trackNumber.split("/")[trackNumber.split("/").length - 1].trim());
        } catch (Exception error) {
            metadata.put("trackNumber", null);
            metadata.put("albumLength", null);
        }
        String year = extractMetadata(METADATA_KEY_YEAR);
        String date = extractMetadata(METADATA_KEY_DATE);
        try {
            metadata.put("year", Integer.parseInt(year.trim()));
        } catch (Exception yearException) {
            try {
                metadata.put("year", date.split("-")[0].trim());
            } catch (Exception dateException) {
                metadata.put("year", null);
            }
        }
        metadata.put("genre", extractMetadata(METADATA_KEY_GENRE));
        metadata.put("authorName", extractMetadata(METADATA_KEY_AUTHOR));
        metadata.put("writerName", extractMetadata(METADATA_KEY_WRITER));
        metadata.put("discNumber", extractMetadata(METADATA_KEY_DISC_NUMBER));
        metadata.put("mimeType", extractMetadata(METADATA_KEY_MIMETYPE));
        metadata.put("trackDuration", extractMetadata(METADATA_KEY_DURATION));
        metadata.put("bitrate", extractMetadata(METADATA_KEY_BITRATE));
        return metadata;
    }

    public byte[] getAlbumArt() {
        try {
            byte[] albumArt = getEmbeddedPicture();
            if (albumArt == null) {
                // Handle the case where no album art is embedded in the media
                System.out.println("No album art found.");
            }
            return albumArt;
        } catch (IllegalArgumentException e) {
            // Specific exception when input is invalid (e.g., file format issues)
            System.err.println("Illegal argument exception while retrieving album art: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Catch any other general exceptions
            System.err.println("Error occurred while retrieving album art: " + e.getMessage());
            e.printStackTrace();
        }
        // Return null or you can return a default album art here
        return null;
    }
}
