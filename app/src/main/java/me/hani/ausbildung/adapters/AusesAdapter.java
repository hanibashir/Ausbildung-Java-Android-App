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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.hani.ausbildung.R;
import me.hani.ausbildung.activities.show.ShowAusActivity;
import me.hani.ausbildung.models.AusItem;

public class AusesAdapter extends RecyclerView.Adapter<AusesAdapter.AllAusListViewHolder> {


    private Context mContext;
    private ArrayList<?> mAllAusList;

    public AusesAdapter(Context context, ArrayList<?> allAusList) {
        mContext = context;
        mAllAusList = allAusList;
    }

    @NonNull
    @Override
    public AllAusListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.aus_list_item, viewGroup, false);

        return new AllAusListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAusListViewHolder allAusViewHolder, int position) {

        AusItem currentItem = (AusItem) mAllAusList.get(position);

        String title = currentItem.getmTitle();
        String arTitle = currentItem.getmArTitle();
        String thumbnail = currentItem.getmImageUrl();

        allAusViewHolder.title.setText(title);
        allAusViewHolder.arTitle.setText(arTitle);

        Picasso.get().load(thumbnail).into(allAusViewHolder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return mAllAusList.size();
    }

    //ViewHolder
    public class AllAusListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private TextView arTitle;
        private ImageView thumbnail;

        public AllAusListViewHolder(@NonNull View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.cv_all_aus_title);
            arTitle = itemView.findViewById(R.id.cv_all_aus_ar_title);
            thumbnail = itemView.findViewById(R.id.cv_all_aus_thumbnail);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){

                Intent intent = new Intent(mContext, ShowAusActivity.class);

                AusItem clickedItem = (AusItem) mAllAusList.get(position);

                intent.putExtra("title", clickedItem.getmTitle());
                intent.putExtra("ar_title", clickedItem.getmArTitle());
                intent.putExtra("certificate", clickedItem.getmCertificate());
                intent.putExtra("duration", clickedItem.getmDuration());
                intent.putExtra("img_url", clickedItem.getmImageUrl());

                intent.putExtra("salary1", clickedItem.getmSalary1());
                intent.putExtra("salary2", clickedItem.getmSalary2());
                intent.putExtra("salary3", clickedItem.getmSalary3());
                intent.putExtra("salary4", clickedItem.getmSalary4());

                intent.putExtra("description", clickedItem.getmDescription());

                intent.putExtra("aus_link", clickedItem.getmLink());

                mContext.startActivity(intent);

            }
        }
    }

}
