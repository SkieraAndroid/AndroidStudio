package com.example.lab4.tasks;

import android.os.Parcel;
import android.os.Parcelable;

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


    public static void removeItem(int currentItemPosition) {

        String itemId = ITEMS.get(currentItemPosition).id;

        ITEMS.remove(currentItemPosition);
        ITEM_MAP.remove(itemId);
    }


    public static class Task implements Parcelable {
        public final String id;
        public final String data;
        public final String aktywnosc;
        public final String koszt;
        public final String przebieg;

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
        }



        protected Task(Parcel in) {
            id = in.readString();
            data = in.readString();
            aktywnosc = in.readString();
            koszt = in.readString();
            przebieg = in.readString();
            picPath = in.readInt();
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
        }
    }
}
