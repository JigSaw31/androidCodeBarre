package com.example.thoma.applicationcodebarre;

import com.example.thoma.applicationcodebarre.model.RoomBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class WebServiceUtils {

    public static final Gson GSON = new Gson();

    public static ArrayList<RoomBean> getRooms() throws Exception {

        String jsonArrayListRoom = OkHttpUtils.sendGetOkHttpRequest("http://192.168.40.26:8000/rooms");

        ArrayList<RoomBean> roomArrayList = GSON.fromJson(jsonArrayListRoom,
                new TypeToken<ArrayList<RoomBean>>() {
                }.getType());

        return roomArrayList;
    }
}
