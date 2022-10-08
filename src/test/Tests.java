import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;


public class Tests {

    @Test
    public void test() throws IOException, ParseException {
        JSONObject json = mapJson(readSchedule());
        JSONObject data = extractValueAsJsonObject(json, "data");
        JSONObject schedule = extractValueAsJsonObject(data, "schedule");
        JSONArray events = extractValueAsJsonArray(schedule, "events");
        java.util.List<Object> eventsList = events.toList();

        List<String> startTimes = eventsList.stream().map(event -> (HashMap<String, String>) event).map(event -> event.get("startTime")).collect(Collectors.toList());
        startTimes = startTimes.stream().map(startTime -> {
            try {
                return transformToLocalTime(startTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        startTimes = getSubListStartingFromToday(startTimes);
        List<Date> startDates = transformToListOfDates(startTimes);

        System.out.println("");

    }

    private static List<Date> transformToListOfDates(List<String> startTimes) throws ParseException {

        return startTimes.stream().map(startTime -> {
            try {
                return stringToDate(startTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

    }



    public static Date stringToDate(String dateString) throws ParseException {
        DateFormat cetFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        cetFormat.setTimeZone(TimeZone.getTimeZone("CET"));
        return cetFormat.parse(dateString);
    }

    private static String transformToLocalTime(String startTime) throws ParseException {
        DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = utcFormat.parse(startTime);
        DateFormat cetFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        cetFormat.setTimeZone(TimeZone.getTimeZone("CET"));
        return cetFormat.format(date);
    }

    private static List<String> getSubListStartingFromToday(List<String> startTimes) {
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateObj.format(formatter);
        int index;
        for (index = 0; index < startTimes.size(); index++) {
            if (startTimes.get(index).contains(date)) {
                break;
            }
        }
        return startTimes.subList(index, startTimes.size() - 1);
    }


    public static String readSchedule() throws IOException {
        return Files.readString(Paths.get("src/resources/worlds-schedule.json"), StandardCharsets.UTF_8);
    }

    public static JSONObject mapJson(String json) {
        return new JSONObject(json);
    }

    public static JSONObject extractValueAsJsonObject(JSONObject json, String key) {
        return (JSONObject) json.get(key);
    }

    public static JSONArray extractValueAsJsonArray(JSONObject json, String key) {
        return (JSONArray) json.get(key);
    }
}
