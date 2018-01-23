package com.Chatbot;

import java.io.File;
import java.io.IOException;

import com.rivescript.macro.Subroutine;

import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.send.SendLocation;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class SendSubroutine implements Subroutine
{
    private DefaultAbsSender sender;

    SendSubroutine(DefaultAbsSender sender)
    {
        this.sender = sender;
    }

    @Override
    public String call(com.rivescript.RiveScript rs, String[] args)
    {
        String type = args[0];
        if (type.equals("photo"))
        {
            String photo = args[1];
            String caption = "";
            for (int i = 2; i < args.length; i++)
                caption += " " + args[i]; // TODO: replace with StringBuilder/StringBuffer (better)
            caption = caption.trim();
            SendPhoto msg = new SendPhoto()
                    .setChatId(rs.currentUser())
                    .setNewPhoto(new File(photo))
                    .setCaption(caption);
            try
            {
                sender.sendPhoto(msg); // Call method to send the photo with caption
            }
            catch (TelegramApiException e)
            {
                e.printStackTrace();
            }
        }
        else if (type.equals("location"))
        {
            String location = "";
            for (int i = 1; i < args.length; i++)
                location += " " + args[i];
            location = location.trim();

            try
            {
                sender.sendLocation(GetLocationMessage(location, rs.currentUser()));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return "";
    }

    private SendLocation GetLocationMessage(String usermsg, String cid) throws InterruptedException, ApiException, IOException
    {
        GeoApiContext context = new GeoApiContext.Builder() //creates a geoapicontext needed for using google maps api
                .apiKey("AIzaSyA4edm9FhbojYzh1lUHcDdJy-ZTnJzQ9tg") //sets the key to token
                .build(); //builds context

        GeocodingResult[] results = GeocodingApi.geocode( //create an array of geocoding results
                context, //param conext
                usermsg) //param message
                .await(); //wait till all results are collected

        return new SendLocation()// Create a locations message object
                .setChatId(cid)
                .setLatitude((float) results[0].geometry.location.lat)
                .setLongitude((float) results[0].geometry.location.lng);
    }
}
