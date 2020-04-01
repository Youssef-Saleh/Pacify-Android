package com.example.pacify;

public class Constants {

    public interface ACTION {

        public static String MAIN_ACTION = "com.example.pacify.action.main";
        public static String PREV_ACTION = "com.example.pacify.action.prev";
        public static String PLAY_ACTION = "com.example.pacify.action.play";
        public static String NEXT_ACTION = "com.example.pacify.action.next";
        public static String STARTFOREGROUND_ACTION = "com.example.pacify.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.example.pacify.action.stopforeground";


    }

    public interface NOTIFICATION_ID{
        public static int FOREGROUND_SERVICE = 101;
    }
}
