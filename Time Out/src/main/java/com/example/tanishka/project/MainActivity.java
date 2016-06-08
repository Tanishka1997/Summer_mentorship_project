package com.example.tanishka.project;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import layout.Friday;
import layout.Thursday;
import layout.Tuesday;
import layout.Wednesday;

public class MainActivity extends AppCompatActivity {
    private ActionBarDrawerToggle actionBarDrawerToggle;

    DrawerLayout drawerLayout;
    RecyclerView navlist;
    FragmentManager manager;
    FragmentTransaction transaction;
    View view1;
    int ctr=-1;
    private ArrayList<String> arrayList;
    private ActionBar actionBar;
    int view_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navlist = (RecyclerView) findViewById(R.id.navview);
         arrayList= new ArrayList<String>();
        arrayList.add("Add event");
        arrayList.add("Today");
        arrayList.add("Monday");
        arrayList.add("Tuesday");
        arrayList.add("Wednesday");
        arrayList.add("Thursday");
        arrayList.add("Friday");
        ViewAdapter viewHolder=new ViewAdapter(arrayList);
        navlist.setLayoutManager(new LinearLayoutManager(this));
        navlist.setAdapter(viewHolder);
        manager = MainActivity.this.getSupportFragmentManager();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
         if(savedInstanceState!=null)
         {
             actionBar.setTitle(savedInstanceState.getString("menu_selected"));
             view_no=savedInstanceState.getInt("view_no");
             ctr=savedInstanceState.getInt("counter");
         }
       if (ctr<0){
           ctr=0;
           HomeScreen fragment=new HomeScreen();
             transaction=manager.beginTransaction();
             transaction.replace(R.id.frame,fragment,"HomeScreen").commit();
         }
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("menu_selected",actionBar.getTitle().toString());
         outState.putInt("view_no",view_no);
        outState.putInt("counter",ctr);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(navlist)) {
                drawerLayout.closeDrawer(navlist);
            } else {
                drawerLayout.openDrawer(navlist);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(navlist))
            drawerLayout.closeDrawer(navlist);
        else
        super.onBackPressed();

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       public LinearLayout myBackground;
        public TextView choose_it;
        public ViewHolder(View itemView,int viewtype) {
            super(itemView);
            if (viewtype == 0) {
                myBackground=(LinearLayout) itemView.findViewById(R.id.myBackgroung);
                myBackground.setSelected(false);
                choose_it = (TextView) itemView.findViewById(R.id.nav_Title);

                itemView.setOnClickListener(this);
            }
        }


        @Override
        public void onClick(View v) {
            clicking(getAdapterPosition());
          view_no=getAdapterPosition();
            if(ctr!=0){
                view1.setSelected(false);
              ctr++;
            }
            myBackground.setSelected(true);
            view1=v;ctr++;
        }
        public void clicking(int position) {
            switch (position)
            {
                case 1: {
                    ActionBar actionBar=getSupportActionBar();
                    assert actionBar != null;
                    actionBar.setTitle("Add Event");
                    Fragment f1 = manager.findFragmentByTag("add_event");
                    transaction = manager.beginTransaction();
                    if (f1 == null) {
                        Fragment fragment = new AddEvent();
                        transaction.replace(R.id.frame, fragment, "add_event");
                        transaction.commit();
                    }
                    else {
                        transaction.replace(R.id.frame, f1, "add_event");
                        transaction.commit();
                    }

                    break;
                }
                case 2:{findDay();
                    break;}
                case 3:
                {
                    ActionBar actionBar=getSupportActionBar();
                    assert actionBar != null;
                    actionBar.setTitle("Monday");
                    Fragment f1 = manager.findFragmentByTag("monday");
                    transaction = manager.beginTransaction();
                    if (f1 == null) {
                        Fragment fragment = new Monday();
                        transaction.replace(R.id.frame, fragment, "monday");
                        transaction.commit();
                    }
                    else {
                        transaction.replace(R.id.frame, f1, "monday");
                        transaction.commit();
                    }
                    break;}
                case 4:
                {
                    ActionBar actionBar=getSupportActionBar();
                    assert actionBar != null;
                    actionBar.setTitle("Tuesday");
                    Fragment f1 = manager.findFragmentByTag("tuesday");
                    transaction = manager.beginTransaction();
                    if (f1 == null) {
                        Fragment fragment = new Tuesday();
                        transaction.replace(R.id.frame, fragment, "tuesday");
                        transaction.commit();
                    }
                    else {
                        transaction.replace(R.id.frame, f1, "tuesday");
                        transaction.commit();
                    }
                    break;}
                case 5:
                {
                    ActionBar actionBar=getSupportActionBar();
                    assert actionBar != null;
                    actionBar.setTitle("Wednesday");
                    Fragment f1 = manager.findFragmentByTag("wednesday");
                    transaction = manager.beginTransaction();
                    if (f1 == null) {
                        Fragment fragment = new Wednesday();
                        transaction.replace(R.id.frame, fragment, "wednesday");
                        transaction.commit();
                    }
                    else {
                        transaction.replace(R.id.frame, f1, "wednesday");
                        transaction.commit();
                    }
                    break;}
                case 6:
                {
                    ActionBar actionBar=getSupportActionBar();
                    assert actionBar != null;
                    actionBar.setTitle("Thursday");
                    Fragment f1 = manager.findFragmentByTag("thursday");
                    transaction = manager.beginTransaction();
                    if (f1 == null) {
                        Fragment fragment = new Thursday();
                        transaction.replace(R.id.frame, fragment, "thursday");
                        transaction.commit();
                    }
                    else {
                        transaction.replace(R.id.frame, f1, "thursday");
                        transaction.commit();
                    }
                    break;}
                case 7:
                {
                    ActionBar actionBar=getSupportActionBar();
                    assert actionBar != null;
                    actionBar.setTitle("Friday");
                    Fragment f1 = manager.findFragmentByTag("friday");
                    transaction = manager.beginTransaction();
                    if (f1 == null) {
                        Fragment fragment = new Friday();
                        transaction.replace(R.id.frame, fragment, "friday");
                        transaction.commit();
                    }
                    else {
                        transaction.replace(R.id.frame, f1, "friday");
                        transaction.commit();
                    }
                    break;}

            }
            drawerLayout.closeDrawer(navlist);
        }
        public void findDay()
        { String weekDay;
            SimpleDateFormat dayformat=new SimpleDateFormat("EEEE",Locale.US);
            Calendar calendar=Calendar.getInstance();
            weekDay=dayformat.format(calendar.getTime());
            switch (weekDay)
            {

                case "Monday":
                {
                    ActionBar actionBar=getSupportActionBar();
                    assert actionBar != null;
                    actionBar.setTitle("Monday");
                    Fragment f1 = manager.findFragmentByTag("monday");
                    transaction = manager.beginTransaction();
                    if (f1 == null) {
                        Fragment fragment = new Monday();
                        transaction.replace(R.id.frame, fragment, "monday");
                        transaction.commit();
                    }
                    else {
                        transaction.replace(R.id.frame, f1, "monday");
                        transaction.commit();
                    }
                    break;}
                case "Tuesday":
                {
                    ActionBar actionBar=getSupportActionBar();
                    assert actionBar != null;
                    actionBar.setTitle("Tuesday");
                    Fragment f1 = manager.findFragmentByTag("tuesday");
                    transaction = manager.beginTransaction();
                    if (f1 == null) {
                        Fragment fragment = new Tuesday();
                        transaction.replace(R.id.frame, fragment, "tuesday");
                        transaction.commit();
                    }
                    else {
                        transaction.replace(R.id.frame, f1, "tuesday");
                        transaction.commit();
                    }
                    break;}
                case "Wednesday":
                {
                    ActionBar actionBar=getSupportActionBar();
                    assert actionBar != null;
                    actionBar.setTitle("Wednesday");
                    Fragment f1 = manager.findFragmentByTag("wednesday");
                    transaction = manager.beginTransaction();
                    if (f1 == null) {
                        Fragment fragment = new Wednesday();
                        transaction.replace(R.id.frame, fragment, "wednesday");
                        transaction.commit();
                    }
                    else {
                        transaction.replace(R.id.frame, f1, "wednesday");
                        transaction.commit();
                    }
                    break;}
                case "Thursday":
                {
                    ActionBar actionBar=getSupportActionBar();
                    assert actionBar != null;
                    actionBar.setTitle("Thursday");
                    Fragment f1 = manager.findFragmentByTag("thursday");
                    transaction = manager.beginTransaction();
                    if (f1 == null) {
                        Fragment fragment = new Thursday();
                        transaction.replace(R.id.frame, fragment, "thursday");
                        transaction.commit();
                    }
                    else {
                        transaction.replace(R.id.frame, f1, "thursday");
                        transaction.commit();
                    }
                    break;}
                case "Friday":
                {  ActionBar actionBar=getSupportActionBar();
                    assert actionBar != null;
                    actionBar.setTitle("Friday");
                    Fragment f1 = manager.findFragmentByTag("friday");
                    transaction = manager.beginTransaction();
                    if (f1 == null) {
                        Fragment fragment = new Friday();
                        transaction.replace(R.id.frame, fragment, "friday");
                        transaction.commit();
                    }
                    else {
                        transaction.replace(R.id.frame, f1, "friday");
                        transaction.commit();
                    }
                    break;}
                case "Saturday":
                {  ActionBar actionBar=getSupportActionBar();
                    assert actionBar != null;
                    actionBar.setTitle("Saturday");
                    Fragment f1 = manager.findFragmentByTag("saturday");
                    transaction = manager.beginTransaction();
                    if (f1 == null) {
                        Fragment fragment = new Saturday();
                        transaction.replace(R.id.frame, fragment, "saturday");
                        transaction.commit();
                    }
                    else {
                        transaction.replace(R.id.frame, f1, "saturday");
                        transaction.commit();
                    }
                    break;}
                case "Sunday":
                {  ActionBar actionBar=getSupportActionBar();
                    assert actionBar != null;
                    actionBar.setTitle("Sunday");
                    Fragment f1 = manager.findFragmentByTag("sunday");
                    transaction = manager.beginTransaction();
                    if (f1 == null) {
                        Fragment fragment = new Sunday();
                        transaction.replace(R.id.frame, fragment, "sunday");
                        transaction.commit();
                    }
                    else {
                        transaction.replace(R.id.frame, f1, "sunday");
                        transaction.commit();
                    }
                    break;}
            }
        }

    }

 public class  ViewAdapter extends RecyclerView.Adapter<ViewHolder> {

     List<String> Choose_it;

     public ViewAdapter(List<String> list) {
         Choose_it=list;
     }

     @Override
     public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
         if (viewType == 0) {
             View v = layoutInflater.inflate(R.layout.row_navigation, parent, false);
             return new ViewHolder(v,viewType);
         }
         if(viewType==1)
         {
             View v = layoutInflater.inflate(R.layout.header, parent, false);
             return new ViewHolder(v,viewType);
         }
        return null;
     }
     @Override
     public void onBindViewHolder(ViewHolder holder, int position) {
       if(position!=0)
       {   if(position==view_no)
       {holder.myBackground.setSelected(true);view1=holder.myBackground;}
           holder.choose_it.setText(Choose_it.get(position-1));}
     }

     @Override
     public int getItemViewType(int position) {
         if(position==0)
             return 1;
         return super.getItemViewType(position);
     }

     @Override
     public int getItemCount() {
         return (Choose_it.size()+1);
     }

     @Override
     public long getItemId(int position) {
         return position;
     }

 }


}


