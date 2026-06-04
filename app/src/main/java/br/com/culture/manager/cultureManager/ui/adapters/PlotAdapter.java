package br.com.culture.manager.cultureManager.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.culture.manager.cultureManager.R;
import br.com.culture.manager.cultureManager.domain.entities.PlotEntity;

public class PlotAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<PlotEntity> plotEntities;

    private static class PlotHolder{
        public TextView textViewName;
        public TextView textViewArea;
    }

    public PlotAdapter(Context context, ArrayList<PlotEntity> plots){
        this.context = context;
        this.plotEntities = plots;
    }

    @Override
    public int getCount() {
        return plotEntities.size();
    }

    @Override
    public Object getItem(int i) {
        return plotEntities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return plotEntities.get(i).hashCode();
    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        PlotHolder holder;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.plot_component, parent, false);

            holder = new PlotHolder();
            holder.textViewName = view.findViewById(R.id.textViewPlotName);
            holder.textViewArea = view.findViewById(R.id.textViewPlotArea);

            view.setTag(holder);
        }else{
         holder = (PlotHolder) view.getTag();
        }

        PlotEntity weather = plotEntities.get(position);

        holder.textViewName.setText(weather.getName());
        holder.textViewArea.setText(weather.getAreaSize().toString());

        return view;
    }
}
