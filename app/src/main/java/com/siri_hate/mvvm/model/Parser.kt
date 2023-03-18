package com.siri_hate.mvvm.model
import android.os.StrictMode
import org.jsoup.Jsoup

class Parser {

    companion object {
        fun getPage(): MutableList<Double> {

            // Управление политикой потоков для сетевых операций
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            // Список валют для парсинга
            val currencyIdsList = listOf(
                "Valute[id=R01235]", // USD
                "Valute[id=R01239]", // EUR
                "Valute[id=R01775]", // CHF
                "Valute[id=R01035]", // GBP
                "Valute[id=R01820]", // JPY
                "Valute[id=R01720]", // UAH
                "Valute[id=R01335]", // KZT
                "Valute[id=R01090B]", // BYN
                "Valute[id=R01700J]", // TRY
                "Valute[id=R01375]", // CNY
                "Valute[id=R01010]", // AUD
                "Valute[id=R01350]", // CAD
                "Valute[id=R01565]" // PLN
            )

            // Массив для хранения цены за 1 единицу валюты
            val result = mutableListOf<Double>()

            result.add(1.0) // Базовый элемент

            // Получение страницы ЦБ
            val doc = Jsoup.connect("https://www.cbr.ru/scripts/XML_daily.asp").get()

            // Рассчет цены за 1 единицу валюты
            for (currency in currencyIdsList) {
                val element = doc.select(currency).first()
                if (element != null) {
                    val value = element.select("Value").text()
                        .replace(',', '.').toDouble()
                    val nominal = element.select("Nominal").text().toInt()
                    result.add(value / nominal)
                }
            }

            return result

        }
    }

}
