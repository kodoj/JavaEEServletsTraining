package com.codecool.myApp.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WebServlet extends HttpServlet {

    protected void doGet( HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().write("<html><body><form name=\"lotteryForm\" method=\"post\">\n" +
                "    HELLO THERE, PREMIUM USER, GIVE US YOUR CREDIT CARD NUMBER AND WE WILL GENERATE LOTTO NUMBERS FOR YOU\n" +
                "    Enter your Credit Card number (number without spaces): <input type=\"text\" name=\"number\"/> <br/>\n" +
                "    Lottery Type:\n" +
                "    <input type=\"radio\" name=\"lottery\" value=\"mini\" />Mini Lotto (5 numbers)\n" +
                "    <input type=\"radio\" name=\"lottery\" value=\"lotto\" />Lotto (6 numbers)\n" +
                "    <input type=\"submit\" value=\"Generate\" />\n" +
                "</form></body></html>");
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String cardNumber = request.getParameter("number");
        String lotteryType = request.getParameter("lottery");
        PrintWriter writer = response.getWriter();

        if(cardNumber == null || lotteryType == null) {
            writer.write("<html><body>Sorry! you have to give us full information! :(");
            return;
        } else if (cardNumber.length() != 16) {
            writer = response.getWriter();
            writer.write("<html><body>That is not a valid credit card number, it's about your lottery win! " +
                    "Man, give us your card information, what can you lose? :(</body></html>");
            return;
        }

        lotteryEngine(response, cardNumber, lotteryType);
    }

    private void lotteryEngine(HttpServletResponse response, String cardNumber, String lotteryType) throws IOException {
        PrintWriter writer;
        long number = 1;
        int loweredNumber = 1;
        int howManyNumbers;
        int maxNumberValue;
        List<Integer> superNumbers;

        try {
            number = Long.valueOf(cardNumber);
            loweredNumber = (int)(number*(0.00000025/100.0f));
            System.out.println(loweredNumber);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        if (lotteryType.equals("mini")) {
            howManyNumbers = 5;
            superNumbers = Arrays.asList(new Integer[5]);
            maxNumberValue = 42;
        } else {
            howManyNumbers = 6;
            superNumbers = Arrays.asList(new Integer[6]);
            maxNumberValue = 49;
        }

        int tempResult;
        for (int i = 0; i < howManyNumbers; i++) {
            tempResult = maxNumberValue + 1;
            Random random = new Random();
            while (tempResult > maxNumberValue || superNumbers.contains(tempResult)){
                tempResult = loweredNumber / (random.nextInt(100000) + 1);
            }
            superNumbers.set(i, tempResult);
        }

        response.setHeader("Content-Type", "text/html");

        writer = response.getWriter();
        writer.write("<html><body>CONGRATULATIONS, Your super numbers to play " +
                lotteryType + " is: ");

        for (int i = 0; i < superNumbers.size(); i++) {
            System.out.println(superNumbers.get(i));
            writer.write(superNumbers.get(i) + "  ");
        }

        writer.write("</body></html>");
    }
}