package com.example.lab4.tasks;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TaskListContent {


    public static final List<Task> ITEMS = new ArrayList<Task>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Task> ITEM_MAP = new HashMap<String, Task>();



    public static void addItem(Task item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void DeleteRecord(String hash)
    {
        DatabaseReference kasowany_wpis = FirebaseDatabase.getInstance().getReference("Wpis serwisowy").child(hash);
        kasowany_wpis.removeValue();
    }

    public static void removeItem(int currentItemPosition) {

        String itemId = ITEMS.get(currentItemPosition).id;

        //poniższe linie służą razem z funkcją DeleteRecord do kasowania wpisu z bazy - pytanie o poprawność lokalizacji
       /* String kasowana_data = ITEMS.get(currentItemPosition).data;
        String kasowana_aktywnosc = ITEMS.get(currentItemPosition).aktywnosc;
        String kasowany_koszt = ITEMS.get(currentItemPosition).koszt;
        String kasowany_przebieg = ITEMS.get(currentItemPosition).przebieg;

        String hash = kasowana_data+kasowana_aktywnosc+kasowany_koszt+kasowany_przebieg;

        DeleteRecord(hash);*/



        ITEMS.remove(currentItemPosition);
        ITEM_MAP.remove(itemId);
    }


    public static class Task implements Parcelable {
        public final String id;
        public final String data;
        public final String aktywnosc;
        public final String koszt;
        public final String przebieg;


        public String hash;

        public final int picPath;



        public Task(String id, String data, String aktywnosc, String koszt, String przebieg) {
            this.id = id;
            this.data = data;
            this.aktywnosc = aktywnosc;
            this.koszt = koszt;
            this.przebieg = przebieg;
            this.picPath = 0;
        }
        public Task(String id, String data, String aktywnosc, String koszt, String przebieg, int picPath) {
            this.id = id;
            this.data = data;
            this.aktywnosc = aktywnosc;
            this.koszt = koszt;
            this.przebieg = przebieg;
            this.picPath = picPath;
            this.hash = this.id+this.data+this.aktywnosc+this.koszt+this.przebieg;
        }



        protected Task(Parcel in) {
            id = in.readString();
            data = in.readString();
            aktywnosc = in.readString();
            koszt = in.readString();
            przebieg = in.readString();
            picPath = in.readInt();
            hash=in.readString();
        }

        public static final Creator<Task> CREATOR = new Creator<Task>() {
            @Override
            public Task createFromParcel(Parcel in) {
                return new Task(in);
            }

            @Override
            public Task[] newArray(int size) {
                return new Task[size];
            }
        };

        @Override
        public String toString() {

            return koszt;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(data);
            dest.writeString(aktywnosc);
            dest.writeString(koszt);
            dest.writeString(przebieg);
            dest.writeInt(picPath);
            dest.writeString(hash);
        }
    }
}
