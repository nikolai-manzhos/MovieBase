package com.defaultapps.moviebase.data.models.firebase;

public class Favorite {

    private Integer favoriteMovieId;
    private String posterPath;

    public Favorite() {}

    public Favorite(Integer favoriteMovieId, String posterPath) {
        this.favoriteMovieId = favoriteMovieId;
        this.posterPath = posterPath;
    }

    public Integer getFavoriteMovieId() {
        return favoriteMovieId;
    }

    public void setFavoriteMovieId(Integer favoriteMovieId) {
        this.favoriteMovieId = favoriteMovieId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
