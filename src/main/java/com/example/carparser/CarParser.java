package com.example.carparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

public class CarParser {
    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect("https://auto.drom.ru/mercedes-benz/g-class/?minprobeg=5000").get();

            System.out.println(GetPrice(document));
            System.out.println(GetCreatedDate(document));
            System.out.println(GetDescription(document));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Integer> GetPrice(Document document) {
        var stringPrice = document.getElementsByAttributeValue("data-ftid", "bull_price");
        var priceArray = new ArrayList<Integer>();

        for (Element elem : stringPrice) {
            var element = elem.toString().substring(29);
            priceArray.add(Integer.parseInt(element.substring(0, element.length() - 21)
                    .replace("&nbsp;", "")));
        }

        return priceArray;
    }

    public static ArrayList<String> GetCreatedDate(Document document) {
        var stringDate = document.getElementsByAttributeValue("data-ftid", "bull_date").text();
        var createdDateArray = new ArrayList<String>();

        for (String word : stringDate.split("назад")) {
            createdDateArray.add(word + "назад");
        }

        return createdDateArray;
    }

    public static ArrayList<String> GetDescription(Document document) {
        var stringDescription = document.getElementsByAttributeValue("data-ftid", "component_inline-bull-description").text();
        var arrayDescription = new ArrayList<String>();

        var count = 0;
        var beginString = 0;
        for (var i = 0; i < stringDescription.length(); i++) {
            if (String.valueOf(stringDescription.charAt(i)).equals(" ")) {
                count++;
                if (count == 10) {
                    arrayDescription.add(stringDescription.substring(beginString, i).replace(",", ""));
                    count = 0;
                    beginString = i;
                }
            }
        }

        return arrayDescription;
    }
}

