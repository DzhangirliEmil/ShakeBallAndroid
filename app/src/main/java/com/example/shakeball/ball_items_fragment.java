package com.example.shakeball;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.InterfaceAddress;
import java.util.ArrayList;

public class ball_items_fragment extends Fragment {

    static interface AdviceManagerInterface{
        AdviceManager getAdviceManager();
    }
    private AdviceManagerInterface getParentManager;
    @Override
    public void onAttach(@NonNull Context _context) {
        super.onAttach(_context);
        getParentManager = (ball_items_fragment.AdviceManagerInterface) _context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView adviceRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_ball_items, container, false);


        BallAdviseAdapter adapter = new BallAdviseAdapter(getParentManager.getAdviceManager().getAdviceList());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adviceRecycler.setLayoutManager(layoutManager);

        adviceRecycler.setAdapter(adapter);
        return adviceRecycler;
    }
}