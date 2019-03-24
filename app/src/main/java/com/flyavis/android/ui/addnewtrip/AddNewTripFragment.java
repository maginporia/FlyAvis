package com.flyavis.android.ui.addnewtrip;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.flyavis.android.R;
import com.flyavis.android.data.database.MyTrip;
import com.flyavis.android.databinding.AddNewTripFragmentBinding;
import com.flyavis.android.util.FlyAvisUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.kc.splashpicker.SplashPicker;
import com.kc.unsplash.models.Photo;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;

public class AddNewTripFragment extends DaggerFragment implements ActionMode.Callback {

    @Inject
    ViewModelProvider.Factory factory;
    private AddNewTripViewModel mViewModel;
    private AddNewTripFragmentBinding binding;
    private ActionMode actionMode;
    private TextInputEditText startDate;
    private TextInputEditText endDate;
    private Calendar calendar = Calendar.getInstance();
    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH);
    private int day = calendar.get(Calendar.DAY_OF_MONTH);
    private int myTripId = 0;
    private Photo photo;

    public static AddNewTripFragment newInstance() {
        return new AddNewTripFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.add_new_trip_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, factory).get(AddNewTripViewModel.class);
        startDate = binding.startDate;
        endDate = binding.endDate;
        //啟動toolbar框架
        if (actionMode == null) {
            actionMode = ((AppCompatActivity) Objects.requireNonNull(getActivity()))
                    .startSupportActionMode(this);
        }
        //編輯Trip時
        if (getArguments() != null) {
            myTripId = AddNewTripFragmentArgs
                    .fromBundle(getArguments()).getMyTripId();
            binding.titleTextView.setText(getString(R.string.edit));
            mViewModel.getSpecificTrip(myTripId).observe(getViewLifecycleOwner(), myTrip -> {
                binding.tripName.setText(myTrip.getTripName());
                startDate.setText(String.valueOf(myTrip.getStartDate()));
                endDate.setText(String.valueOf(myTrip.getEndDate()));

                if (photo == null) {
                    Glide
                            .with(this)
                            .load(myTrip.getCoverPhoto())
                            .centerCrop()
                            .into(binding.selectedImageView);
                }
                mViewModel.getSpecificTrip(myTripId).removeObservers(getViewLifecycleOwner());
            });
        } else {
            binding.tripName.setText("美好的旅行");
            //自動帶入今天日期
            String dateTime
                    = String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(day);
            startDate.setText(dateTime);
            endDate.setText(dateTime);
        }
        binding.imagePicker.setOnClickListener(view -> {
            SplashPicker.open(this, getActivity()
                    , getString(R.string.unsplash_access_key), "選擇封面圖");
        });

        startDate.setOnClickListener(view -> showDatePicker(startDate));
        endDate.setOnClickListener(view -> showDatePicker(endDate));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SplashPicker.PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                photo = data.getParcelableExtra(SplashPicker.KEY_IMAGE);
                Glide
                        .with(this)
                        .load(photo.getUrls().getRegular())
                        .centerCrop()
                        .into(binding.selectedImageView);
            }
        }
    }

    private void showDatePicker(TextInputEditText view) {
        new DatePickerDialog(Objects.requireNonNull(getContext()), (datePicker, i, i1, i2) -> {
            String dateTime = String.valueOf(i) + "-" + String.valueOf(i1 + 1) + "-" + String.valueOf(i2);
            view.setText(dateTime);
        }, year, month, day).show();
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        MyTrip myTrip = new MyTrip();
        if (myTripId != 0) {
            myTrip.setMyTripId(myTripId);
        }
        myTrip.setTripName(String.valueOf(binding.tripName.getText()));
        myTrip.setStartDate(Date.valueOf(String.valueOf(startDate.getText())));
        myTrip.setEndDate(Date.valueOf(String.valueOf(endDate.getText())));

        //轉存byte[]
        if (binding.selectedImageView.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) binding.selectedImageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageInByte = baos.toByteArray();
            myTrip.setCoverPhoto(imageInByte);
        }

        switch (item.getItemId()) {
            case R.id.save:
                if (myTripId != 0) {
                    mViewModel.updateTrip(myTrip);
                } else {
                    mViewModel.insertTrip(myTrip);
                    mViewModel.insetMyTripRemote(myTrip);
                }
                mode.finish();
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        FlyAvisUtils.hideSoftKeyboard(Objects.requireNonNull(getActivity()));
        Objects.requireNonNull(getFragmentManager()).popBackStack();

    }
}
