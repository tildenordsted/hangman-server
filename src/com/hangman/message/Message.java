package com.hangman.message;

public class Message implements java.io.Serializable {
    private String message;
    private String typeOfMessage;


    //constructor for message
    public Message(String message, String typeOfMessage){
        this.message = message;
        this.typeOfMessage = typeOfMessage;
    }

    //get message
    public String getMessage() {
        return message;
    }

    //get the type of message
    public String getTypeOfMessage() {
        return typeOfMessage;
    }
}
