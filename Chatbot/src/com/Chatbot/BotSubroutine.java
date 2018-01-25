package com.Chatbot;

import com.rivescript.macro.Subroutine;
import com.rivescript.util.StringUtils;

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
        }
        return "";
    }

    private void restartApplication()
    {
        try
        {
            // Run new instance of java executable
            Runtime.getRuntime().exec("nohup java -jar /home/koenzk/chatbot/server.jar &");
            // Terminate current executable
            System.exit(0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
