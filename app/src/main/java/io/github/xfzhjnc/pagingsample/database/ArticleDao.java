package io.github.xfzhjnc.pagingsample.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(ArticleEntity... entities);

    @Query("SELECT * FROM articles ORDER BY `index` ASC")
    LiveData<ArticleEntity[]> getArticles();

    @Query("SELECT * FROM articles WHERE id = :id")
    LiveData<ArticleEntity> getArticleById(String id);
}
