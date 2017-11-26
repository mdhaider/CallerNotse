package com.bluerocket.callernotse.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluerocket.callernotse.R;
import com.bluerocket.callernotse.activities.HomeActivity;
import com.bluerocket.callernotse.adapters.RecyclerViewAdapter;
import com.bluerocket.callernotse.models.NoteModel;
import com.bluerocket.callernotse.viewmodel.NoteListViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nehal on 11/23/2017.
 */

public class CallerNotesListFragment extends Fragment implements View.OnLongClickListener {
    private View mRootView;
    private NoteListViewModel viewModel;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private Bundle mBundle;

    public CallerNotesListFragment() {
    }


    public static CallerNotesListFragment newInstance() {
        CallerNotesListFragment fragment = new CallerNotesListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_caller_notes_list, container, false);
        recyclerView =  mRootView.findViewById(R.id.recyclerView);

        mBundle = savedInstanceState;

        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<NoteModel>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(recyclerViewAdapter);

        viewModel = ViewModelProviders.of(this).get(NoteListViewModel.class);

        viewModel.getItemAndPersonList().observe(getActivity(), new Observer<List<NoteModel>>() {
            @Override
            public void onChanged(@Nullable List<NoteModel> itemAndPeople) {
                recyclerViewAdapter.addItems(itemAndPeople);
            }
        });
        return mRootView;
    }
    @Override
    public boolean onLongClick(View v) {
        NoteModel noteModel = (NoteModel) v.getTag();
        viewModel.deleteItem(noteModel);
        return true;
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            onResume();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }

        HomeActivity mainActivity = (HomeActivity)getActivity();
        mainActivity.fab.setImageResource(R.drawable.ic_add_white_24dp);
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddNoteFragment()).commitAllowingStateLoss();
            }
        });
    }
}

