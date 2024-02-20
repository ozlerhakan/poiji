package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCellsJoinedByName;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.Objects;

/**
 * An Album POJO.
 */
public class Album {
    @ExcelCellsJoinedByName(expression = "Artist")
    private MultiValuedMap<String, String> artists = new ArrayListValuedHashMap<>();

    @ExcelCellsJoinedByName(expression = "Track[0-9]+")
    private MultiValuedMap<String, String> tracks = new ArrayListValuedHashMap<>();

    public void setArtists(MultiValuedMap<String, String> artists) {
        this.artists = artists;
    }

    public void setTracks(MultiValuedMap<String, String> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "Album{" +
                "artists=" + artists +
                ", tracks=" + tracks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(artists, album.artists) && Objects.equals(tracks, album.tracks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artists, tracks);
    }
}
