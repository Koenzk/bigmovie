package com.Chatbot;

import com.rivescript.macro.Subroutine;
import java.io.File;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

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
        if ("photo".equals(type))
        {
            String f_id = args[1];
            String caption = "";
            for (int i=2; i<args.length; i++)
                caption += " " + args[i]; // TODO: replace with StringBuilder/StringBuffer (better)
            caption = caption.trim();
            SendPhoto msg = new SendPhoto()
                    .setChatId(rs.currentUser())
                    .setNewPhoto(new File(f_id))
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
        return "";
    }
}
