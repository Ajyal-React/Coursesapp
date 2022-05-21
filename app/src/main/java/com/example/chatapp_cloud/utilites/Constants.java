package com.example.chatapp_cloud.utilites;

import java.util.HashMap;

public class Constants {
    public static final String KEY_COLLECTION_USERS ="users";
    public static String KEY_FIRST_NAME = "name";
    public static String KEY_MIDDLE_NAME = "middlename";
    public static String KEY_FAMILY_NAME = "familyname";
    public static String KEY_ADDRESS = "address";
    public static final String MOBILE_NUMBER = "mobilenumber";
    public static String KEY_EMAIL = "email";
    public static String KEY_password = "password";
    public static final String KEY_BIRTHDATE = "birthdate";
    public static final String KEY_PREFERENCE_NAME = "chatAppPreference";
    public static final String KEY_IS_SIGNED_IN = "isSignedIn";
    public static final String KEY_USER_ID = "userid";
    public static String KEY_IMAGE = "image";
    public static final String KEY_FCM_TOKEN= "fcmtoken";
    public static final String KEY_STUDENT ="student";
    public static final String KEY_COLLECTION_CHAT = "chat";
    public static final String KEY_SENDER_ID = "senderId";
    public static final String KEY_RECEIVER_ID = "receiverId";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_COLLECTION_CONVERSATIONS = "conversations";
    public static final String KEY_SENDER_NAME = "senderName";
    public static final String KEY_RECEIVER_NAME = "receiverName";
    public static final String KEY_SENDER_IMAGE = "senderImage";
    public static final String KEY_RECEIVER_IMAGE = "receiverImage";
    public static final String KEY_LAST_MESSAGE = "lastMessage";
    public static final String KEY_AVATIABTLITY = "availability";
    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content_type";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRTION_IDS = "registration_ids";

    public static HashMap<String,String> remoteMsgHeaders = null;
    public static HashMap<String,String> getRemoteMsgHeaders(){
        if (remoteMsgHeaders == null){
            remoteMsgHeaders = new HashMap<>();
            remoteMsgHeaders.put(
                    REMOTE_MSG_AUTHORIZATION,
                    "key=AAAAJN2UbKE:APA91bHHybzzY4D0eb8UHd5tEh3SynYyY4UiPggVWlu1HYM9QAMgrii-EqGnknrQVC7bI3_IdiB88EuB8w3GBdT_NK85idwKNXe1G78-zBrOvtNMdfkJa5o4j3TzZinyjDFPGUS9hFUI"
            );

            remoteMsgHeaders.put(
                    REMOTE_MSG_CONTENT_TYPE,
                    "application/json"
            );
        }
        return remoteMsgHeaders;
    }


}
