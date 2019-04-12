package io.github.xfzhjnc.pagingsample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import io.github.xfzhjnc.pagingsample.database.ArticleEntity;
import io.github.xfzhjnc.pagingsample.R;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleListViewHolder> {
    private       List<ArticleEntity>          mEntityList;
    private final ArticleListItemClickListener mOnItemClickListener;

    public ArticleListAdapter(ArticleListItemClickListener onItemClickListener) {
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
        holder.bind(mEntityList.get(position));
    }

    @Override
    public int getItemCount() {
        if (null == mEntityList) return 0;
        return mEntityList.size();
    }

    public void swapArticleList(final List<ArticleEntity> articleEntities) {
        if (mEntityList == null) {
            mEntityList = articleEntities;
            notifyDataSetChanged();
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mEntityList.size();
                }

                @Override
                public int getNewListSize() {
                    return articleEntities.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mEntityList.get(oldItemPosition).getId().equals(articleEntities.get(newItemPosition).getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    ArticleEntity oldEntity = mEntityList.get(oldItemPosition);
                    ArticleEntity newEntity = articleEntities.get(newItemPosition);
                    return oldEntity.getId().equals(newEntity.getId())
                            && oldEntity.getDesc().equals(newEntity.getDesc());
                }
            });
            mEntityList = articleEntities;
            result.dispatchUpdatesTo(this);
        }
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
            String id       = mEntityList.get(position).getId();
            mOnItemClickListener.onArticleItemClick(id);
        }
    }

}
