package com.example.tanishka.project;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEvent extends Fragment {
    EventHelper eventHelper;
    EditText editText;
    String Day;
    NumberPicker hours;
    NumberPicker hours2;
    NumberPicker minutes;
    NumberPicker minutes2;
    Spinner spinner;
    List<String> list;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AddEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEvent newInstance(String param1, String param2) {
        AddEvent fragment = new AddEvent();
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

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Day",Day);
        outState.putString("Event",editText.getText().toString());
        outState.putInt("hours",hours.getValue());
        outState.putInt("minutes",minutes.getValue());
        outState.putInt("hours2",hours2.getValue());
        outState.putInt("minutes2",minutes2.getValue());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity){
            a=(Activity) context;
        }
        eventHelper=new EventHelper(context.getApplicationContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_add_event, container,false);
        editText=(EditText) v.findViewById(R.id.event_name);
        Button button=(Button) v.findViewById(R.id.add_this_event);
         spinner=(Spinner) v.findViewById(R.id.spinner_day);
        list=new ArrayList<String>();
        list.add("Monday");
        list.add("Tuesday");
        list.add("Wednesday");
        list.add("Thursday");
        list.add("Friday");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        hours=(NumberPicker) v.findViewById(R.id.hours);
        minutes=(NumberPicker) v.findViewById(R.id.minutes);
        hours2=(NumberPicker) v.findViewById(R.id.hours2);
        minutes2=(NumberPicker) v.findViewById(R.id.minutes2);
        hours.setWrapSelectorWheel(true);
        hours2.setWrapSelectorWheel(true);
        minutes.setWrapSelectorWheel(true);
        minutes2.setWrapSelectorWheel(true);
        hours.setMinValue(00);
        hours.setMaxValue(23);
        minutes.setMinValue(00);
        minutes.setMaxValue(59);
        hours2.setMinValue(00);
        hours2.setMaxValue(23);
        minutes2.setMinValue(00);
        minutes2.setMaxValue(59);
        if (savedInstanceState!=null)
        {  String day=savedInstanceState.getString("Day");
            spinner.setSelection(list.indexOf(day));
            editText.setText(savedInstanceState.getString("Event"));
            hours.setValue(savedInstanceState.getInt("hours"));
            minutes.setValue(savedInstanceState.getInt("minutes"));
            hours2.setValue(savedInstanceState.getInt("hours2"));
            minutes2.setValue(savedInstanceState.getInt("minutes2"));
        }
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Day="Monday";
                        break;
                    case 1:
                        Day="Tuesday";
                        break;
                    case 2:
                        Day="Wednesday";
                        break;
                    case 3:
                        Day="Thursday";
                        break;
                    case 4:
                        Day="Friday";
                        break;
                    
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               Day="Monday";
            }
        });
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
          boolean check;
               if(editText.getText().toString().equals(""))
               {
                   editText.setError("Event name is required");
               }
               else {
                   if(hours.getValue()>hours2.getValue()) {
                       FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                       MyDialog myDialog=new MyDialog();
                       myDialog.setTargetFragment(AddEvent.this,0);
                       myDialog.show(fragmentManager,"add_event");
                   }
                   else {
                       if (hours.getValue() == hours2.getValue() && minutes.getValue() > minutes2.getValue()){
                           FragmentManager fragmentManager=getFragmentManager();
                           MyDialog myDialog=new MyDialog();
                           myDialog.setTargetFragment(AddEvent.this,0);
                           myDialog.show(fragmentManager,"add_event");
                       }

                       else {
                           check = eventHelper.insertData(Day, editText.getText().toString(), String.format("%02d",hours.getValue()), String.format("%02d",minutes.getValue()), String.format("%02d",hours2.getValue()), String.format("%02d",minutes2.getValue()));
                           if (check)
                               Toast.makeText(getActivity(), "Event Added", Toast.LENGTH_LONG).show();

                           hours.setValue(0);
                           minutes.setValue(0);
                           hours2.setValue(0);
                           minutes2.setValue(0);
                           editText.setText("");
                           spinner.setSelection(0);
                       }
                   }
                }
               }

       });
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
