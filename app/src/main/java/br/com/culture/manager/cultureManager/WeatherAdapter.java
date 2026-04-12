package br.com.culture.manager.cultureManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.culture.manager.cultureManager.entities.Weather;

public class WeatherAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<Weather> weathers;

    private static class WeatherHolder{
        public TextView textViewDate;
        public TextView textViewWeather;
        public TextView textViewName;
        public TextView textViewWind;
    }

    WeatherAdapter(Context context, ArrayList<Weather> weathers){
        this.context = context;
        this.weathers = weathers;
    }

    @Override
    public int getCount() {
        return weathers.size();
    }

    @Override
    public Object getItem(int i) {
        return weathers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return weathers.get(i).hashCode();
    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {
        WeatherHolder holder;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.weather_component, parent, false);

            holder = new WeatherHolder();
            holder.textViewDate = view.findViewById(R.id.textViewDate);
            holder.textViewWeather = view.findViewById(R.id.textViewWeather);
            holder.textViewName = view.findViewById(R.id.textViewName);
            holder.textViewWind = view.findViewById(R.id.textViewWind);

            view.setTag(holder);
        }else{
         holder = (WeatherHolder) view.getTag();
        }

        Weather weather = weathers.get(position);

        holder.textViewDate.setText(weather.getDate().toString());
        holder.textViewWeather.setText(weather.getWeather());
        holder.textViewName.setText(weather.getName());

        String windStregth = "";
        switch (weather.getWindStrength()){
            case STRONG:
                windStregth = context.getText(R.string.strongh).toString();
                break;
            case MODERATE:
                windStregth = context.getText(R.string.moderate).toString();
                break;
            case WEAK:
                windStregth = context.getText(R.string.weak).toString();
                break;
        }

        holder.textViewWind.setText(windStregth);

        return view;
    }
}
