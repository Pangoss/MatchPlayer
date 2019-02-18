package fr.fengdavid.matchplayer.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.fengdavid.matchplayer.R;
import fr.fengdavid.matchplayer.structs.Event;

public class EventsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Event> lEvents;

    public EventsAdapter(Context context, ArrayList<Event> lEvents) {
        this.context = context;
        this.lEvents = lEvents;
    }

    @Override
    public int getCount() {
        return lEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return lEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event event = (Event) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.event_layout,null);
        TextView tv_name = convertView.findViewById(R.id.tv_event_name);
        TextView tv_sport = convertView.findViewById(R.id.tv_sport);
        TextView tv_place = convertView.findViewById(R.id.tv_place);
        TextView tv_date = convertView.findViewById(R.id.tv_day);
        TextView tv_hour = convertView.findViewById(R.id.tv_hour);
        TextView tv_players = convertView.findViewById(R.id.tv_players);

        tv_name.setText(event.getName());
        tv_sport.setText(event.getSport());
        tv_place.setText(event.getPlace());
        tv_date.setText(event.getDate().substring(0, 10));
        tv_hour.setText(event.getDate().substring(11,16));
        tv_players.setText(event.getnPlayers() + "/" + event.getMaxPlayers());

        return convertView;
    }


}
