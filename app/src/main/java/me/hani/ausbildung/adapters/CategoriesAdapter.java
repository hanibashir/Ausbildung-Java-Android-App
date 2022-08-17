package me.hani.ausbildung.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.hani.ausbildung.R;
import me.hani.ausbildung.activities.lists.AusesListActivity;
import me.hani.ausbildung.models.CategoryItem;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

    private Context mContext;
    private ArrayList<?> mCatList;


    public CategoriesAdapter(Context mContext, ArrayList<?> mCatList) {
        this.mContext = mContext;
        this.mCatList = mCatList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cat_item, viewGroup, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {

        CategoryItem categoryItem = (CategoryItem) mCatList.get(i);

        String catName = categoryItem.getmCatName();
        String ausCount = categoryItem.getmAusesCount();

        categoryViewHolder.catName.setText(catName);
        categoryViewHolder.ausCount.setText(ausCount + " " + "مهنة");


    }

    @Override
    public int getItemCount() {
        return mCatList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView catName;
        private TextView ausCount;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            catName = itemView.findViewById(R.id.cv_cat_name);
            ausCount = itemView.findViewById(R.id.aus_count);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                CategoryItem categoryItem = (CategoryItem) mCatList.get(position);



                Intent intent = new Intent(mContext, AusesListActivity.class);
                intent.putExtra("auses_type", "cat_auses");
                intent.putExtra("cat_item_id", categoryItem.getmId());
                intent.putExtra("cat_name", categoryItem.getmCatName());
                mContext.startActivity(intent);

            }
        }
    }

}
