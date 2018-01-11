package com.Chatbot;

import com.rivescript.macro.Subroutine;
import com.rivescript.util.StringUtils;
import org.telegram.telegrambots.bots.DefaultAbsSender;

import java.io.IOException;

public class BotSubroutine implements Subroutine
{
    @Override
    public String call(com.rivescript.RiveScript rs, String[] args)
    {
        String cmd = StringUtils.join(args, " ");

        switch(cmd)
        {
            case "restart":
                // restart java bot or reload brain.rive
                restartApplication();
                break;
            default:
                //
                break;
        }
        return "";
    }

    private void restartApplication()
    {
        try
        {
            Runtime.getRuntime().exec("nohup java -jar /home/koenzk/chatbot/server.jar &");
            System.exit(0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
