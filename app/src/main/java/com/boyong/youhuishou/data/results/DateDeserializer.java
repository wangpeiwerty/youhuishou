package com.boyong.youhuishou.data.results;

import android.annotation.SuppressLint;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateDeserializer implements JsonDeserializer<Date> {
    @SuppressLint("UseValueOf")
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Pattern pattern = Pattern.compile("\\/(Date\\((.*?)(\\+.*)?\\))\\/");
        Matcher matcher = pattern.matcher(json.getAsJsonPrimitive().getAsString());
        String million = matcher.replaceAll("$2");

        return new Date(new Long(million));
    }
}