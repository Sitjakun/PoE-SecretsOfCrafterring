package ass.cheat;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main  {

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
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
        List<Date> startDates = transformToListOfDates(startTimes);
        startDates = getSubListStartingFromToday(startDates);

        Timer t = new Timer();
        for (Date startDate : startDates) {
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Time is on !");
                    try {
                        Robot bot = new Robot();
                        bot.keyPress(KeyEvent.VK_CONTROL);
                        bot.keyPress(KeyEvent.VK_W);
                        bot.keyRelease(KeyEvent.VK_W);
                        bot.keyRelease(KeyEvent.VK_CONTROL);
                        Runtime.getRuntime().exec(new String[] {"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe" , "https://lolesports.com/live/worlds/otplol_" });
                        Thread.sleep(10000);
                        bot.mouseMove(900, 400);
                        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        Thread.sleep(10000);
                    } catch (IOException | InterruptedException | AWTException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            t.schedule(tt, startDate);
        }

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

    private static List<Date> getSubListStartingFromToday(List<Date> startDates) {
        int index;
        for (index = 0; index < startDates.size(); index++) {
            if (startDates.get(index).toInstant().isAfter(new Date().toInstant())) {
                break;
            }
        }
        return startDates.subList(index, startDates.size() - 1);
    }

    private static String transformToLocalTime(String startTime) throws ParseException {
        DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = utcFormat.parse(startTime);
        DateFormat cetFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        cetFormat.setTimeZone(TimeZone.getTimeZone("CET"));
        return cetFormat.format(date);
    }

    public static String readSchedule() throws IOException {
        return Files.readString(Paths.get("C:\\Users\\Sitja\\Documents\\Projects\\Cheater\\LeagueWorldsRewards\\src\\main\\resources\\worlds-schedule.json"), StandardCharsets.UTF_8);
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
