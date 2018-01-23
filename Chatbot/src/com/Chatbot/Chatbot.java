package com.Chatbot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.rivescript.RiveScript;
import org.telegram.telegrambots.api.methods.send.SendLocation;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;

public class Chatbot extends TelegramLongPollingBot
{
    private RiveScript bot = new RiveScript();
    private String apikey = "AIzaSyA4edm9FhbojYzh1lUHcDdJy-ZTnJzQ9tg";

    // Constructor
    Chatbot()
    {
        super();
        bot.setSubroutine("system", new com.Chatbot.SystemSubroutine());
        bot.setSubroutine("bot", new com.Chatbot.BotSubroutine());
        bot.setSubroutine("jdbc", new com.Chatbot.DatabaseSubroutine());
        bot.setSubroutine("send", new com.Chatbot.SendSubroutine(this));
        bot.loadDirectory("C:\\Users\\5wesl\\Desktop\\bigmovie\\Chatbot\\resources\\rivescript");
//        bot.loadDirectory("resources/rivescript");
        bot.sortReplies();
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            if(message_text.startsWith("give location")){
                try {
                    SendLocationMessage(message_text, chat_id);
                } catch (InterruptedException | IOException | ApiException e) {
                    e.printStackTrace();
                }
            } else {
                SendRegularMessage(message_text, chat_id);
            }
        }
    }

    public void Say(String msg, Long cid){

        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(cid)
                .setText(msg);
        if(message != null)
            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
    }

    public void SendRegularMessage(String usermsg, Long cid){
        Say(usermsg, cid);
    }

    /**
     * send a location message to the user
     * @param usermsg message sent by the user depends on brain.rive
     * @param cid chat id
     */
    public void SendLocationMessage(String usermsg, Long cid) throws InterruptedException, ApiException, IOException {

        GeoApiContext context = new GeoApiContext.Builder() //creates a geoapicontext needed for using google maps api
                .apiKey(apikey) //sets the key to token
                .build(); //builds context


        GeocodingResult[] results = GeocodingApi.geocode( //create an array of geocoding results
                context, //param conext
                usermsg) //param message
                .await(); //wait till all results are collected

        if(results.length == 0){ //null check if the message is empty
            SendRegularMessage("https://en.wikipedia.org/wiki/Nothing", cid);
            return;
        }

        SendRegularMessage("I have found " + results.length + "results, here they are", cid);

        for(int i = 0; i < results.length; i++) {
            SendLocation message = new SendLocation() // Create a locations message object
                    .setChatId(cid) //set chat id
                    .setLatitude((float) results[0].geometry.location.lat) //set latitude
                    .setLongitude((float) results[0].geometry.location.lng); //set longitude

            try {
                if (message != null)
                    execute(message); // Sending our message object to user

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername()
    {
        // Return bot username
        return "Amsterdammer_bot";
    }

    @Override
    public String getBotToken()
    {
        // Return bot token from BotFather
        return "544145072:AAEyqVbe-x0itYz2tdHdhmDuQTnlhG8N8bI";
    }
}
