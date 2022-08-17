package me.hani.ausbildung.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import me.hani.ausbildung.R;
import me.hani.ausbildung.activities.lists.AusesListActivity;
import me.hani.ausbildung.activities.lists.CategoriesListActivity;
import me.hani.ausbildung.activities.lists.PagesListActivity;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private CardView cvAusInfo, cvCat, cvAllAus, cvBestAus,cvBestSalary;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        getActivity().setTitle("الرئيسية");


        cvAusInfo = view.findViewById(R.id.cv_aus_info);
        cvCat = view.findViewById(R.id.cv_cat);
        cvBestAus = view.findViewById(R.id.cv_best_aus);
        cvBestSalary = view.findViewById(R.id.cv_best_salary);
        cvAllAus = view.findViewById(R.id.cv_all_aus);


        cvAusInfo.setOnClickListener(this);
        cvCat.setOnClickListener(this);
        cvBestAus.setOnClickListener(this);
        cvBestSalary.setOnClickListener(this);
        cvAllAus.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //-----------------First Card-----------------
            case R.id.cv_all_aus:
                Intent allAusIntent = new Intent(getContext(), AusesListActivity.class);
                startActivity(allAusIntent);
                break;

            case R.id.cv_cat:
                Intent catIntent = new Intent(getContext(), CategoriesListActivity.class);
                startActivity(catIntent);
                break;
           //----------------Second Card------------------
            case R.id.cv_best_aus:
                Intent bestAusIntent = new Intent(getContext(), AusesListActivity.class);
                bestAusIntent.putExtra("auses_type", "best_aus");
                startActivity(bestAusIntent);
                break;

            case R.id.cv_best_salary:
                Intent bestSalaryIntent = new Intent(getContext(), AusesListActivity.class);
                bestSalaryIntent.putExtra("auses_type", "best_salary");
                startActivity(bestSalaryIntent);
                break;
            //---------------Third Card-------------------
            case R.id.cv_aus_info:
                //getFragmentManager().beginTransaction().replace(R.id.fragments_container, new PagesFragment()).addToBackStack(null).commit();
                Intent pagesIntent = new Intent(getContext(), PagesListActivity.class);
                startActivity(pagesIntent);

                //Toast.makeText(getActivity(), "not working!", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
