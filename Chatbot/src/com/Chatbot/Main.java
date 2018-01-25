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
    public static void main(String[] args)
    {
        try
        {
            // Download latest (updated) RiveScript from GitHub
            FileUtils.copyURLToFile(new URL("https://raw.githubusercontent.com/Koenzk/bigmovie/master/Chatbot/resources/rivescript/brain.rive"), new File("C:\\Users\\5wesl\\Desktop\\bigmovie\\Chatbot\\resources\\rivescript\\brain.rive"), 2500, 2500);
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
