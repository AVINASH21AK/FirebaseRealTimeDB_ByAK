package com.firebasedatabase.utils;



public class Constants {

    public static String STATUS401 = "401";

    public static class DATABASE{
        public static String databaseName = "users";
        public static String users = "users";
        public static String login = "login";
        public static String title = "title";
    }

    public static class INTENT{
        public static String From = "from";
    }

    public static class SHAREDPREFERENCE{
        public static String Login = "isLogin";   //0-notLogin & 1-Login
        public static String UserName = "UserName";
        public static String UserID = "UserID";
        public static String UserEmail = "UserEmail";

    }


    public static class MESSAGES{

        public static class VALIDATIONS{
            public static String Name = "Enter name";
            public static String Email = "Enter email-id";
            public static String EmailInvalid = "Enter valid email-id";

            public static String UserNotRegister = "Email-ID is not registered, please register";
            public static String EmailAlreadyUsed = "Email-ID is already used";
            public static String SuccessRegistered = "Successfully registered";
            public static String SuccessLogin = "Successfully login";
            public static String SuccessUpdated = "Successfully updated";
            public static String SuccessDeleted = "Successfully deleted";
        }

    }




}
