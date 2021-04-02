package com.example.shakeball;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shakeball.R;

import java.util.ArrayList;

public class BallAdviseAdapter extends RecyclerView.Adapter<BallAdviseAdapter.ViewHolder> {

    private ArrayList<BallAdvice> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_advice);
        }

        public CardView getTextView() {
            return cardView;
        }
    }

    public BallAdviseAdapter(ArrayList<BallAdvice> dataSet) {
        localDataSet = dataSet;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.advice_card, viewGroup, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        TextView t = (TextView) viewHolder.cardView.findViewById(R.id.card_advice_text);
        t.setText(localDataSet.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
