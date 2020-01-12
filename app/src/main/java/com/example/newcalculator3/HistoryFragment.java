package com.example.newcalculator3;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements ActionSender {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private String result;
    private ArrayList<String> list = new ArrayList<>();

    TextView textView;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((MainActivity) Objects.requireNonNull(getActivity())).passVal(new ActionSender() {
                @Override
                public void send(String text) {
                    Log.e("TAG", "send: " + text);
                    result = text;
                    if (result!=null){
                        adapter.addText(result);
                    }
                }
            });
        }
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        recycler();
        //   textView = view.findViewById(R.id.result_txt_view);
    }

    public void recycler() {
        adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void send(String text) {
        if (text != null) {
            result = text;
            Log.e("TAG", "send: " + text);
        }
    }
}

