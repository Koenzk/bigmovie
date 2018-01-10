package com.Chatbot;

import com.rivescript.macro.Subroutine;
import com.rivescript.util.StringUtils;
import org.telegram.telegrambots.bots.DefaultAbsSender;

public class BotSubroutine implements Subroutine
{
    private DefaultAbsSender sender;

    BotSubroutine(DefaultAbsSender sender)
    {
        this.sender = sender;
    }

    @Override
    public String call(com.rivescript.RiveScript rs, String[] args)
    {
        String cmd = StringUtils.join(args, " ");

        switch(cmd)
        {
            case "reload":
                //
                break;
            default:
                //
                break;
        }
        return "";
    }
}
