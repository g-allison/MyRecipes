package com.codepath.myrecipes.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.myrecipes.R;
import com.codepath.myrecipes.ui.WeeklyMenu;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private Context mContext;
    private List<WeeklyMenu> mDaysOfWeek;

    public ProfileAdapter(Context mContext, List<WeeklyMenu> mDaysOfWeek) {
        this.mContext = mContext;
        this.mDaysOfWeek = mDaysOfWeek;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_weekday, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeeklyMenu dayOfWeek = mDaysOfWeek.get(position);
        holder.bind(dayOfWeek);

    }

    @Override
    public int getItemCount() {
        return mDaysOfWeek.size();
    }

    public void clear() {
        mDaysOfWeek.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvWeekday;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvWeekday = itemView.findViewById(R.id.tvWeekday);
        }

        public void bind(WeeklyMenu dayOfWeek) {
            mTvWeekday.setText(dayOfWeek.getDay());
        }
    }












}
