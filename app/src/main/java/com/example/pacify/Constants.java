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

    public interface PLAYLIST_ID {
        public static String POP = "http://www.mocky.io/v2/5e8fb410330000551827d2d8";
        public static String ELECTRONIC= "http://192.168.43.130:5000/Playlist/5e8e0396a1a3730480cc0a68"; //not in use
    }

    public static String SIGNUP_URL = "http://23.96.41.241/api/signup"; //http://23.96.41.241/api/signup this is the working link
    public static String EDIT_PROFILE_URL = "http://23.96.41.241/api/account/profile";
}
