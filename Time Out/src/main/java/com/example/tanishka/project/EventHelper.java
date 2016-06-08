package com.example.tanishka.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tanishka on 16-05-2016.
 */
public class EventHelper extends SQLiteOpenHelper {

   public static final class TimeTable{

        public  static final String Name="TIME_TABLE";
        public static final class Columns{
            public static final String Day="DAY";
            public static final String Event="EVENT";
            public static final String From_Hour="FROM_HOUR";
            public static final String From_Minute="FROM_MINUTE";
            public static final String To_Hour="TO_HOUR";
            public static final String To_Minute="TO_MINUTE";
        }
   }

    public EventHelper(Context context) {
        super(context, TimeTable.Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TimeTable.Name+" ( " +TimeTable.Columns.Day+","+TimeTable.Columns.Event+","+TimeTable.Columns.From_Hour
              +","+TimeTable.Columns.From_Minute+","+TimeTable.Columns.To_Hour+","+TimeTable.Columns.To_Minute+")" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TimeTable.Name);
        onCreate(db);

    }
    public boolean insertData(String day,String event,String from_hour,String from_minute,String to_hour,String to_minute){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(TimeTable.Columns.Day,day);
        values.put(TimeTable.Columns.Event,event);
        values.put(TimeTable.Columns.From_Hour,from_hour);
        values.put(TimeTable.Columns.From_Minute,from_minute);
        values.put(TimeTable.Columns.To_Hour,to_hour);
        values.put(TimeTable.Columns.To_Minute,to_minute);
        long check= db.insert(TimeTable.Name,null,values);
        db.close();
        if (check==-1)
            return false;
        else
            return  true;

    }
    public Cursor findEvents(String day){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(TimeTable.Name, new String[]{TimeTable.Columns.Event, TimeTable.Columns.From_Hour, TimeTable.Columns.From_Minute, TimeTable.Columns.To_Hour, TimeTable.Columns.To_Minute}, TimeTable.Columns.Day + "=?", new String[]{"Monday"}, null, null, TimeTable.Columns.From_Hour, null);
        switch (day) {
            case "Monday":
            cursor = db.query(TimeTable.Name, new String[]{TimeTable.Columns.Event, TimeTable.Columns.From_Hour, TimeTable.Columns.From_Minute, TimeTable.Columns.To_Hour, TimeTable.Columns.To_Minute}, TimeTable.Columns.Day + "=?", new String[]{"Monday"}, null, null, TimeTable.Columns.From_Hour+","+TimeTable.Columns.From_Minute, null);
            break;
            case "Tuesday":
                cursor = db.query(TimeTable.Name, new String[]{TimeTable.Columns.Event, TimeTable.Columns.From_Hour, TimeTable.Columns.From_Minute, TimeTable.Columns.To_Hour, TimeTable.Columns.To_Minute}, TimeTable.Columns.Day + "=?", new String[]{"Tuesday"}, null, null, TimeTable.Columns.From_Hour+","+TimeTable.Columns.From_Minute, null);
                break;
            case "Wednesday":
                 cursor = db.query(TimeTable.Name, new String[]{TimeTable.Columns.Event, TimeTable.Columns.From_Hour, TimeTable.Columns.From_Minute, TimeTable.Columns.To_Hour, TimeTable.Columns.To_Minute}, TimeTable.Columns.Day + "=?", new String[]{"Wednesday"}, null, null, TimeTable.Columns.From_Hour+","+TimeTable.Columns.From_Minute, null);
                 break;
            case "Thursday":
                cursor = db.query(TimeTable.Name, new String[]{TimeTable.Columns.Event, TimeTable.Columns.From_Hour, TimeTable.Columns.From_Minute, TimeTable.Columns.To_Hour, TimeTable.Columns.To_Minute}, TimeTable.Columns.Day + "=?", new String[]{"Thursday"}, null, null, TimeTable.Columns.From_Hour+","+TimeTable.Columns.From_Minute, null);
                break;
            case "Friday":
                cursor = db.query(TimeTable.Name, new String[]{TimeTable.Columns.Event, TimeTable.Columns.From_Hour, TimeTable.Columns.From_Minute, TimeTable.Columns.To_Hour, TimeTable.Columns.To_Minute}, TimeTable.Columns.Day + "=?", new String[]{"Friday"}, null, null, TimeTable.Columns.From_Hour+","+TimeTable.Columns.From_Minute, null);
                 break;

        }

        return cursor;

    }

  public void delete_it(String day,String event_name,String from_hour,String from_minute,String to_hour,String to_minute){
      SQLiteDatabase db=this.getWritableDatabase();
      db.delete(TimeTable.Name, TimeTable.Columns.Day + "=" + "? " + "AND " + TimeTable.Columns.Event + "=" + "? " + "AND " + TimeTable.Columns.From_Hour + "=" + "? " + "AND " + TimeTable.Columns.From_Minute + "=" + "? " + "AND " + TimeTable.Columns.To_Hour + "=" + "? " +"AND "+ TimeTable.Columns.To_Minute + "=" + "? ", new String[]{day,event_name,from_hour,from_minute,to_hour,to_minute});
      db.close();
  }
}
