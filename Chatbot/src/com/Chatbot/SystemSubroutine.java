package com.Chatbot;

import com.rivescript.macro.Subroutine;
import com.rivescript.util.StringUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemSubroutine implements Subroutine
{
    @Override
    public String call(com.rivescript.RiveScript rs, String[] args)
    {
        // Retrieve terminal command from input
        String cmd = StringUtils.join(args, " ");

        // Create a scanner
        java.util.Scanner s;

        try
        {
            // Execute terminal command
            s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
        catch (IOException ex)
        {
            Logger.getLogger(SystemSubroutine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
