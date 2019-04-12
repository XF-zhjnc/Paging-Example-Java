package io.github.xfzhjnc.pagingsample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import io.github.xfzhjnc.pagingsample.R;
import io.github.xfzhjnc.pagingsample.database.ArticleEntity;

public class ArticleListAdapter extends PagedListAdapter<ArticleEntity, ArticleListAdapter.ArticleListViewHolder> {
    private final ArticleListItemClickListener mOnItemClickListener;

    public ArticleListAdapter(ArticleListItemClickListener onItemClickListener) {
        super(new DiffUtil.ItemCallback<ArticleEntity>() {
            @Override
            public boolean areItemsTheSame(@NonNull ArticleEntity oldItem, @NonNull ArticleEntity newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull ArticleEntity oldItem, @NonNull ArticleEntity newItem) {
                return oldItem.getId().equals(newItem.getId()) && oldItem.getDesc().equals(newItem.getDesc());
            }
        });
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ArticleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleListViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public interface ArticleListItemClickListener {
        void onArticleItemClick(String id);
    }

    class ArticleListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mTitleView;
        final TextView mAuthorView;
        final TextView mDateView;

        ArticleListViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleView  = itemView.findViewById(R.id.tv_article_title);
            mAuthorView = itemView.findViewById(R.id.tv_article_author);
            mDateView   = itemView.findViewById(R.id.tv_article_date);
            itemView.setOnClickListener(this);
        }

        void bind(ArticleEntity entity) {
            mTitleView.setText(entity.getDesc());
            mAuthorView.setText(entity.getWho());
            mDateView.setText(entity.getPublishedAt());
        }

        @Override
        public void onClick(View view) {
            int    position = getAdapterPosition();
            String id       = getItem(position).getId();
            mOnItemClickListener.onArticleItemClick(id);
        }
    }

}
