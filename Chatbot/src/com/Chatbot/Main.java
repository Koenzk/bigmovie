package com.Chatbot;

import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main
{
    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        // Download latest RiveScript
        try
        {
            FileUtils.copyURLToFile(new URL("https://pastebin.com/raw/dUeRdymq"), new File("/home/koenzk/chatbot/rivescript/brain.rive"), 1000, 1000);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        // Initialize Api Context
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try
        {
            botsApi.registerBot(new Chatbot());
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
        System.out.println("Chatbot successfully started!");
    }
}
