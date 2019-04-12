package io.github.xfzhjnc.pagingsample;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.github.xfzhjnc.pagingsample.adapter.ArticleListAdapter;
import io.github.xfzhjnc.pagingsample.viewmodel.ArticleViewModel;
import io.github.xfzhjnc.pagingsample.viewmodel.ArticleViewModelFactory;

public class MainActivity extends AppCompatActivity
        implements ArticleListAdapter.ArticleListItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // =============================
        // init RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_article_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ArticleListAdapter adapter = new ArticleListAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        // =============================

        // =============================
        // set data
        ArticleViewModelFactory factory = new ArticleViewModelFactory(InjectorUtils.provideGankRepository(this));
        ArticleViewModel articleViewModel = ViewModelProviders.of(this, factory).get(ArticleViewModel.class);
        articleViewModel.getArticleEntities().observe(this, adapter::submitList);
        // =============================
    }

    @Override
    public void onArticleItemClick(String id) {
        Toast.makeText(this, "id = " + id, Toast.LENGTH_SHORT).show();
    }
}
