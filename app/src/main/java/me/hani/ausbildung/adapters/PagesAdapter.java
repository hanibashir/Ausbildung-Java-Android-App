package me.hani.ausbildung.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import me.hani.ausbildung.R;
import me.hani.ausbildung.activities.show.ShowPageActivity;
import me.hani.ausbildung.models.PageItem;

public class PagesAdapter extends RecyclerView.Adapter<PagesAdapter.PagesViewHolder>{
    private Context mContext;
    private ArrayList<?> mPagesList;

    public PagesAdapter(Context context, ArrayList<?> pagesList) {
        this.mContext = context;
        this.mPagesList = pagesList;
    }

    @NonNull
    @Override
    public PagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.page_item_list, parent, false);

        return new PagesViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull PagesViewHolder holder, int position) {

        PageItem pageItem = (PageItem) mPagesList.get(position);

        String pageTitle = pageItem.getPageTitle();
        //substring article to fit the card on the page item list


        String articleSummary = pageItem.getArticleSummary();
        int strLength = 110;
        if (articleSummary.length() < 110){
            strLength = articleSummary.length();
        }
        articleSummary.substring(0, strLength);

        Picasso.get().load(pageItem.getPageImgUrl()).into(holder.pageImg);
        holder.pageTitle.setText(pageTitle);
        holder.pageSummaryArticle.setText(articleSummary +"..");
    }

    @Override
    public int getItemCount() {
        return mPagesList.size();
    }

    public class PagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView pageImg;
        private TextView pageTitle;
        private TextView pageSummaryArticle;
        private MaterialButton itemPageMoreBtn;


        public PagesViewHolder(@NonNull View itemView) {
            super(itemView);

            pageImg = itemView.findViewById(R.id.item_page_img);
            pageTitle = itemView.findViewById(R.id.item_page_title);
            pageSummaryArticle = itemView.findViewById(R.id.item_page_summary_article);
            itemPageMoreBtn = itemView.findViewById(R.id.item_page_more_btn);

            itemView.setOnClickListener(this);
            itemPageMoreBtn.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){

                PageItem pageItem = (PageItem) mPagesList.get(position);

                Intent intent = new Intent(mContext, ShowPageActivity.class);

                intent.putExtra("page_title", pageItem.getPageTitle());
                //intent.putExtra("page_summary", pageItem.getArticleSummary());
                intent.putExtra("page_article", pageItem.getPageArticle());
                intent.putExtra("page_img", pageItem.getPageImgUrl());
                intent.putExtra("page_video_id", pageItem.getVideoId());
                intent.putExtra("page_update_date", pageItem.getPageLastUpdate());

                mContext.startActivity(intent);

            }
        }
    }
}
