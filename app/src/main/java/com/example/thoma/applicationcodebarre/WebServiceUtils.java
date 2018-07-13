package com.example.thoma.applicationcodebarre;

import com.example.thoma.applicationcodebarre.model.RoomBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;

public class WebServiceUtils {

    private static final String URL_BASE = "http://192.168.40.26:8000/";

    private static final Gson GSON = new Gson();

    public static ArrayList<RoomBean> getRooms() throws Exception {

        String jsonArrayListRoom = OkHttpUtils.sendGetOkHttpRequest(URL_BASE + "rooms");

        ArrayList<RoomBean> roomArrayList = GSON.fromJson(jsonArrayListRoom,
                new TypeToken<ArrayList<RoomBean>>() {
                }.getType());

        return roomArrayList;
    }

    public static void addEquipment(JSONObject jsonObject) throws Exception {

        OkHttpUtils.sendPostOkHttpRequest(URL_BASE + "send-equipment", jsonObject.toString());

    }
}
