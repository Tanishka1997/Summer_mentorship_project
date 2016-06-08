package layout;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tanishka.project.DeleteDialog;
import com.example.tanishka.project.Event;
import com.example.tanishka.project.EventHelper;
import com.example.tanishka.project.EventLab;
import com.example.tanishka.project.R;
import com.example.tanishka.project.SelectHow;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Wednesday#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Wednesday extends Fragment {
        int ctr;
        EventHelper db;
        RecyclerView recyclerView_wednesday;
        EventLab eventLab;
        Cursor cursor;
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;
    private List<Event> day_events;
    private int flag;


    public Wednesday() {
            // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Monday.
         */
        // TODO: Rename and change types and number of parameters
        public static Wednesday newInstance(String param1, String param2) {
            Wednesday fragment = new Wednesday();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
         setHasOptionsMenu(true);
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);

            Activity a;

            if (context instanceof Activity) {
                a = (Activity) context;
            }
            db=new EventHelper(getActivity());
            cursor=db.findEvents("Wednesday");
            cursor.moveToFirst();
            eventLab=new EventLab();
            while (!cursor.isAfterLast())
            {
                eventLab.generate_list(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
                cursor.moveToNext();
            }

            cursor.close();
        }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            flag = 1;
            deleting_it();
        } else {
            flag = 0;
            EventHolder h;
            for (int i = 0; i < day_events.size(); i++) {
                h = (EventHolder) recyclerView_wednesday.findViewHolderForAdapterPosition(i);
                if (h.relativeLayout.isSelected())
                    h.relativeLayout.setSelected(false);
            }
        }
    }
    public void deleting_it(){
        EventHolder h;int dell=0;
        List<Event> my_day_events=new ArrayList<>();
        for(int i=0;i<day_events.size();i++) {
            h = (EventHolder) recyclerView_wednesday.findViewHolderForAdapterPosition(i);
            if (h.relativeLayout.isSelected()) {
                Event event = day_events.get(i);
                dell=1;
                String event_name=event.getEvent_Name();
                String from_hour=event.getFrom_Hour();
                String from_minute=event.getFrom_Minute();
                String to_hour=event.getTo_Hour();
                String to_minute=event.getTo_Minute();
                EventHelper data=new EventHelper(getActivity());
                data.delete_it("Wednesday",event_name,from_hour,from_minute,to_hour,to_minute);
            }
            else
                my_day_events.add(day_events.get(i));
        }
        if (dell==1)
        {  day_events=my_day_events;
            EventAdapter eventAdapter=new EventAdapter(my_day_events);
            recyclerView_wednesday.setAdapter(eventAdapter);}
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_for_delete,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_it: {
                int num = 0;
                EventHolder h;
                for (int i = 0; i < day_events.size(); i++) {
                    h = (EventHolder) recyclerView_wednesday.findViewHolderForAdapterPosition(i);
                    if (h.relativeLayout.isSelected())
                        num++;
                }
                if (num != 0) {
                    FragmentManager manager = getFragmentManager();
                    DeleteDialog deleteDialog = DeleteDialog.newInstance();
                    deleteDialog.setTargetFragment(Wednesday.this, 3);
                    deleteDialog.show(manager, "Monday_Dialog");
                    return true;
                }
                else {
                    FragmentManager manager = getFragmentManager();
                    SelectHow selectHow = SelectHow.newInstance();
                    selectHow.setTargetFragment(Wednesday.this, 8);
                    selectHow.show(manager, "Wednesday_Message");
                    return true;
                }

            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v= inflater.inflate(R.layout.fragment_wednesday, container, false);
            recyclerView_wednesday=(RecyclerView) v.findViewById(R.id.wednesday_recycler_view);
            recyclerView_wednesday.setLayoutManager(new LinearLayoutManager(getActivity()));
            if(cursor.getCount()==0)
            {
                v= inflater.inflate(R.layout.not_available,container,false);
            }
            launch();
            return v;
        }

        public void launch()
        {
             day_events=eventLab.getEvent_list();
            EventAdapter adapter=new EventAdapter(day_events);
            recyclerView_wednesday.setAdapter(adapter);
        }
    private class EventHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener{
        TextView event_name_textview;
        TextView time_duration_textview;
        Event mevent;
        RelativeLayout relativeLayout;
        public EventHolder(View itemView) {
            super(itemView);
            event_name_textview=(TextView) itemView.findViewById(R.id.heading);
            time_duration_textview=(TextView) itemView.findViewById(R.id.time_duration);
            relativeLayout=(RelativeLayout) itemView.findViewById(R.id.selected);
        }
        private void bindEvents(Event event){
            mevent=event;
            event_name_textview.setText(mevent.getEvent_Name());
            String string="Time-  "+mevent.getFrom_Hour()+":"+mevent.getFrom_Minute()+"hrs"+"-"+mevent.getTo_Hour()+":"+mevent.getTo_Minute()+"hrs";
            time_duration_textview.setText(string);
            itemView.setLongClickable(true);
            itemView.setClickable(true);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
            ctr=0;
        }
        @Override
        public void onClick(View v) {
            if (!relativeLayout.isSelected()&&ctr!=0)
            {relativeLayout.setSelected(true);ctr++;}
            else if(ctr!=0)
            {  relativeLayout.setSelected(false);ctr--;}
        }

        @Override
        public boolean onLongClick(View v) {
            ctr=1;
            relativeLayout.setSelected(true);
            return true;
        }
    }
        private class EventAdapter extends RecyclerView.Adapter<EventHolder>{

            List<Event> events;
            public EventAdapter(List<Event> day_events){
                events=day_events;
            }
            @Override
            public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
                View view=layoutInflater.inflate(R.layout.list_of_events,parent,false);
                return new EventHolder(view);
            }

            @Override
            public void onBindViewHolder(EventHolder holder, int position) {
                Event day_events=events.get(position);
                holder.bindEvents(day_events);
            }
            @Override
            public int getItemCount() {
                return events.size();
            }
        }

        public void onActivityCreated(@Nullable Bundle savedInstanceState) {

            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        @Override
        public void onDetach() {
            super.onDetach();
        }
    }
