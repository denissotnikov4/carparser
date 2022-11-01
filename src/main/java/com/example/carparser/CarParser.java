package com.example.carparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

public class CarParser {
    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://auto.drom.ru/mercedes-benz/g-class/").get();

            System.out.println(GetPrice(doc));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Object> GetPrice(Document document) {
        var stringPrice = document.getElementsByAttributeValue("data-ftid", "bull_price");
        var priceArray = new ArrayList<>();

        for (Element elem : stringPrice) {
            var element = elem.toString().substring(29);
            priceArray.add(Integer.parseInt(element.substring(0, element.length()-21)
                    .replace("&nbsp;", "")));
        }

        return priceArray;
    }
}

