package com.Chatbot;

import com.rivescript.macro.Subroutine;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSubroutine implements Subroutine
{
    @Override
    public String call(com.rivescript.RiveScript rs, String[] args)
    {
        String host = args[0];
        String port = args[1];
        String db = args[2];
        String username = args[3];
        String password = args[4];
        String sql = "";
        String result = "";

        for (int i=5; i < args.length; i++)
        {
            sql += " " + args[i];
        }
        sql = sql.trim();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try
        {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + host + ":" + port + "/" + db,
                    username, password);
            statement = connection.createStatement();
            resultSet=statement.executeQuery(sql);
            while(resultSet.next())
            {
                int i = resultSet.getMetaData().getColumnCount();
                for (int j = 1; j <= i; j++)
                {
                    if (result.equals(""))
                    {
                        result = resultSet.getString(j);
                    }
                    else
                    {
                        result += resultSet.getString(j) + " ";
                    }
                }
                if (!result.equals(""))
                {
                    result += "\n";
                }
            }
        }
        catch (SQLException ex)
        {
            //
        }
        finally
        {
            try
            {
                resultSet.close();
                statement.close();
                connection.close();
            }
            catch (SQLException ex)
            {
                //
            }
        }

        return result;
    }
}
