package com.flyavis.android.ui.addnewbill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.flyavis.android.R;
import com.flyavis.android.databinding.CategorySpinnerBinding;

import androidx.databinding.DataBindingUtil;

public class CategoryAdapter extends ArrayAdapter<String> {
    private Context ctx;
    private String[] contentArray;
    private Integer[] imageArray;

    public CategoryAdapter(Context context, int resource, String[] objects, Integer[] imageArray) {
        super(context, R.layout.category_spinner, R.id.categoryTextView, objects);
        this.ctx = context;
        this.contentArray = objects;
        this.imageArray = imageArray;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        CategorySpinnerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(ctx)
                , R.layout.category_spinner, null, false);
        binding.categoryTextView.setText(contentArray[position]);
        binding.categoryImageView.setImageResource(imageArray[position]);

        return binding.getRoot();
    }
}
