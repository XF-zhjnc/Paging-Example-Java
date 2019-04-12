package io.github.xfzhjnc.pagingsample.database;

import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "articles", indices = {@Index(value = {"id"}, unique = true)})
public class ArticleEntity {
    @PrimaryKey(autoGenerate = true)
    private int     index;
    @SerializedName("_id")
    private String  id;
    @SerializedName("createAt")
    private String  createdAt;
    @SerializedName("desc")
    private String  desc;
    @SerializedName("publishedAt")
    private String  publishedAt;
    @SerializedName("source")
    private String  source;
    @SerializedName("type")
    private String  type;
    @SerializedName("url")
    private String  url;
    @SerializedName("used")
    private boolean used;
    @SerializedName("who")
    private String  who;

    @Ignore
    public ArticleEntity(String id, String createdAt, String desc, String publishedAt, String source,
                         String type, String url, boolean used, String who) {
        this.id = id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
    }

    // Constructor used by Room to create ArticleEntries
    public ArticleEntity(int index, String id, String createdAt, String desc, String publishedAt,
                         String source, String type, String url, boolean used, String who) {
        this.index = index;
        this.id = id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
    }

    public int getIndex() {
        return index;
    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public boolean isUsed() {
        return used;
    }

    public String getWho() {
        return who;
    }
}
