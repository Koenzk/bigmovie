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

    // Constructor
    Chatbot()
    {
        super();
        bot.setSubroutine("system", new SystemSubroutine());
        bot.setSubroutine("bot", new BotSubroutine());
        bot.setSubroutine("jdbc", new DatabaseSubroutine());
        bot.setSubroutine("send", new SendSubroutine(this));
        bot.loadDirectory("/home/koenzk/chatbot/rivescript");
//        bot.loadDirectory("resources/rivescript");
        bot.sortReplies();
    }


    /**
     * send a location message to the user
     * @param usermsg message sent by the user depends on brain.rive
     * @param cid chat id
     */
    public void SendLocationMessage(String usermsg, Long cid) throws InterruptedException, ApiException, IOException {

        //TODO change to singleton
        GeoApiContext context = new GeoApiContext.Builder() //creates a geoapicontext needed for using google maps api
                .apiKey("AIzaSyAYSpNnEji7o1QmXORjyVzOc5aHYD1OxlU ") //sets the key to token
                .build(); //builds context

        GeocodingResult[] results = GeocodingApi.geocode( //create an array of geocoding results
                context, //param conext
                usermsg) //param message
                .await(); //wait till all results are collected

        Gson gson = new GsonBuilder().setPrettyPrinting().create(); //not needed but why not
        if(results.length == 0){ //null check if the message is empty
            Say("https://en.wikipedia.org/wiki/Nothing", cid);
            return;
        }

        Say("I have found " + results.length + "results, here they are", cid);

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

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void onUpdateReceived(Update update)
    {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText())
        {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            // Get reply
            String reply = bot.reply(String.valueOf(chat_id), message_text);

            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(reply);
            try
            {
                execute(message); // Sending our message object to user
            }
            catch (TelegramApiException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername()
    {
        // Return bot username
        return "The_Best_IMDB_bot";
    }

    @Override
    public String getBotToken()
    {
        // Return bot token from BotFather
        return "365483835:AAHCHztOCwj8RcU0aDVXQb5RcGdp_aPAlSU";
    }
}
