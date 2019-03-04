package com.example.daggersample.data.local;


import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(primaryKeys = ("id"))
public class MovieEntity implements Parcelable {

    private int userId;
    private int id;
    private String title;

    //completed":false


    public MovieEntity() {
    }

    protected MovieEntity(Parcel in) {
        userId = in.readInt();
        id = in.readInt();
        title = in.readString();
    }

    public static final Creator<MovieEntity> CREATOR = new Creator<MovieEntity>() {
        @Override
        public MovieEntity createFromParcel(Parcel in) {
            return new MovieEntity(in);
        }

        @Override
        public MovieEntity[] newArray(int size) {
            return new MovieEntity[size];
        }
    };

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeInt(id);
        parcel.writeString(title);
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    /*  @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName(value="header", alternate={"title", "name"})
    @Expose
    private String header;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName(value="description", alternate={"overview", "synopsis"})
    private String description;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("runtime")
    @Expose
    private Long runtime;

    @SerializedName("status")
    @Expose
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPosterPath() {
        if(posterPath != null && !posterPath.startsWith("http")) {
            posterPath = String.format("https://image.tmdb.org/t/p/w500%s", posterPath);
        }
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.header);
        dest.writeString(this.posterPath);
        dest.writeString(this.description);
        dest.writeString(this.releaseDate);
        dest.writeValue(this.runtime);
        dest.writeString(this.status);
    }

    public MovieEntity() {
    }

    protected MovieEntity(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.header = in.readString();
        this.posterPath = in.readString();
        this.description = in.readString();
        this.releaseDate = in.readString();
        this.runtime = (Long) in.readValue(Long.class.getClassLoader());
        this.status = in.readString();
    }

    public static final Creator<MovieEntity> CREATOR = new Creator<MovieEntity>() {
        @Override
        public MovieEntity createFromParcel(Parcel source) {
            return new MovieEntity(source);
        }

        @Override
        public MovieEntity[] newArray(int size) {
            return new MovieEntity[size];
        }
    };*/
}
