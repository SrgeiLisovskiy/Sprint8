//package com.company.adapters;
//
//import com.company.module.Epic;
//import com.company.module.Status;
//import com.company.serves.Managers;
//import com.google.gson.*;
//
//import java.lang.reflect.Type;
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class CustomConverter implements JsonSerializer<Epic>, JsonDeserializer<Epic> {
//
//    @Override
//    public JsonElement serialize(Epic epic, Type type, JsonSerializationContext jsonSerializationContext) {
//        JsonObject object = new JsonObject();
//        object.addProperty("name", epic.getName());
//        object.addProperty("description", epic.getDescription());
//        object.addProperty("id",epic.getId());
//        object.addProperty("status", epic.getStatus().toString());
//        object.addProperty("Type", epic.getType().toString());
//        object.addProperty("Duration",epic.getDuration().toMinutes());
//        object.addProperty("EndTime", epic.getEndTime().toString());
//        object.addProperty("StartTime", epic.getStartTime().toString());
//        object.addProperty("subtaskID",epic.getSubtasksID().toString());
//        return object;
//    }
//
//    @Override
//    public Epic deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//        JsonObject object = json.getAsJsonObject();
//        int id  = object.get("id").getAsInt();
//        String name = object.get("name").getAsString();
//        String description =object.get("description").getAsString();
//        Status status;
//            if (object.get("status").equals("NEW"))
//                status = Status.NEW;
//            else if (object.get("status").equals("DONE"))
//                status = Status.DONE;
//            else
//                status = Status.IN_PROGRESS;
//        Duration duration = Duration.ofMinutes(object.get("Duration").getAsInt());
//        LocalDateTime timeStart = LocalDateTime.parse(object.get("StartTime").getAsString());
//        LocalDateTime timeEnd = LocalDateTime.parse(object.get("SEndTime").getAsString());
//
//
//
//        return new Epic(id,name,description,status,duration,timeStart);
//    }
//}
