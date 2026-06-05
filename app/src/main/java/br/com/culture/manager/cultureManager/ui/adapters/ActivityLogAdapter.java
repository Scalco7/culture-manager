package br.com.culture.manager.cultureManager.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.culture.manager.cultureManager.R;
import br.com.culture.manager.cultureManager.domain.mappers.LocalDateTimeMappers;
import br.com.culture.manager.cultureManager.ui.models.ActivityLogModel;

public class ActivityLogAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<ActivityLogModel> logEntities;

    private static class ActivityLogHolder {
        public TextView textViewName;
        public TextView textViewPlot;
        public TextView textViewTimeSpent;
        public TextView textViewDate;
    }

    public ActivityLogAdapter(Context context, ArrayList<ActivityLogModel> logs){
        this.context = context;
        this.logEntities = logs;
    }

    @Override
    public int getCount() {
        return logEntities.size();
    }

    @Override
    public Object getItem(int i) {
        return logEntities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return logEntities.get(i).hashCode();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ActivityLogAdapter.ActivityLogHolder holder;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_log_component, parent, false);

            holder = new ActivityLogAdapter.ActivityLogHolder();
            holder.textViewName = view.findViewById(R.id.textViewActivityLogName);
            holder.textViewPlot = view.findViewById(R.id.textViewActivityLogPlot);
            holder.textViewTimeSpent = view.findViewById(R.id.textViewActivityLogTimeSpent);
            holder.textViewDate = view.findViewById(R.id.textViewActivityLogDate);

            view.setTag(holder);
        }else{
            holder = (ActivityLogAdapter.ActivityLogHolder) view.getTag();
        }

        ActivityLogModel activityLog = logEntities.get(position);

        holder.textViewName.setText(activityLog.getName());
        holder.textViewTimeSpent.setText(activityLog.getTimeSpent().toString());
        holder.textViewDate.setText(LocalDateTimeMappers.toString(activityLog.getDate()));
        holder.textViewPlot.setText(activityLog.getPlot().getName());

        return view;
    }
}
