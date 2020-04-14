package com.shinro.wallpaper.ui.photo.list_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shinro.wallpaper.R;
import com.shinro.wallpaper.bases.BaseFragment;

public class ListViewFragment extends BaseFragment implements ListViewContract.View {

    private ListViewContract.Presenter mPresenter = new ListViewPresenter(this);   // Presenter

    public ListViewFragment() {}

    public static ListViewFragment newInstance() {
        ListViewFragment fragment = new ListViewFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO: Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

    }

}
