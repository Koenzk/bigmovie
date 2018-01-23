package com.Chatbot;

import com.rivescript.RiveScript;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Chatbot extends TelegramLongPollingBot
{
    private RiveScript bot = new RiveScript();

    // Constructor
    Chatbot()
    {
        super();
        bot.setSubroutine("system", new com.Chatbot.SystemSubroutine());
        bot.setSubroutine("bot", new com.Chatbot.BotSubroutine());
        bot.setSubroutine("jdbc", new com.Chatbot.DatabaseSubroutine());
        bot.setSubroutine("send", new com.Chatbot.SendSubroutine(this));
        bot.loadDirectory("/home/koenzk/chatbot/rivescript");
        bot.sortReplies();
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
