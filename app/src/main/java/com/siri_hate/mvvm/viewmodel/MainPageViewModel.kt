package com.siri_hate.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.siri_hate.mvvm.model.Parser


class MainPageViewModel(
    private val currencyAbbreviation: Array<String>,
    private val currencyFlags: IntArray
    ) : ViewModel() {

    // *Переменные* --------------------------------------------------------------------------------

    // Обычные переменные

        /// (Массив double цены на 1 единицу валюты)
    private var baseCostOfCurrencyMutable = DoubleArray(13)

    // MutableLiveData

        /// (Значения валют)
    private var firstCurrencyValueMutable = MutableLiveData("")
    private var secondCurrencyValueMutable = MutableLiveData("")

        /// (Названия валют)
    private var firstCurrencyNameMutable = MutableLiveData("")
    private var secondCurrencyNameMutable = MutableLiveData("")

        /// (Иконки флагов для валют)
    private val firstCurrencyFlagMutable = MutableLiveData<Int>()
    private val secondCurrencyFlagMutable = MutableLiveData<Int>()

        /// (Текущий курс выбранных валют)
    private var liveCurrencyValueMutable = MutableLiveData("")

        /// (Выбранные элементы выпадающих списков)
    private val firstCurrencySelectedMutable = MutableLiveData(0)
    private val secondCurrencySelectedMutable = MutableLiveData(0)


    // LiveData

        /// (Значения валют)
    val firstCurrencyValueLiveData: LiveData<String> = firstCurrencyValueMutable
    val secondCurrencyValueLiveData: LiveData<String> = secondCurrencyValueMutable

        /// (Названия валют)
    val firstCurrencyNameLiveData: LiveData<String> = firstCurrencyNameMutable
    val secondCurrencyNameLiveData: LiveData<String> = secondCurrencyNameMutable

        /// (Иконки флагов для валют)
    val firstCurrencyFlagLiveData: LiveData<Int> = firstCurrencyFlagMutable
    val secondCurrencyFlagLiveData: LiveData<Int> = secondCurrencyFlagMutable

        /// (Текущий курс выбранных валют)
    val liveCurrencyValueLiveData: LiveData<String> = liveCurrencyValueMutable

        /// (Выбранные элементы выпадающих списков)
    val firstCurrencySelectedLiveData: LiveData<Int> = firstCurrencySelectedMutable
    val secondCurrencySelectedLiveData: LiveData<Int> = secondCurrencySelectedMutable


    // *Переменные* (END)---------------------------------------------------------------------------


    // *Функции* -----------------------------------------------------------------------------------

    // Инициализация ViewModel
    init {
        // Получаем цену за 1 единицу каждой из валют на данный момент
        baseCostOfCurrencyMutable = Parser.getPage().toDoubleArray()

        // Получаем текущий курс валют
        liveCurrency()

        // Устанавливаем выпадающие списки в начальное положение
        firstCurrencySelectedMutable.value = 1
        secondCurrencySelectedMutable.value = 0
    }


    // Функция обработки нажатий на клавиатуру
    fun keyboard(keyCode: String) {

        // Буфер для обработки данных
        var buffer = secondCurrencyValueMutable.value.orEmpty()

        when(keyCode) {
            "Clear" -> {
                if (buffer.isNotEmpty()) {
                    buffer = buffer.substring(0, buffer.length - 1)
                }
            }
            "Dot" -> {
                if (!buffer.contains(".") && buffer.isNotEmpty()) {
                    buffer += "."
                }
            }
            else -> buffer += keyCode
        }

        // Возвращаем LiveData значение буфера
        secondCurrencyValueMutable.value = buffer

        // Считаем новую конвертацию
        calculate(secondCurrencyValueMutable.value.toString())
    }

    // Функция конвертации валюты
    private fun calculate(secondCurrencyValue: String) {
        // Переменные для подсчета результата
        val amountOfSecondCurrency: Double
        val baseCostOfFirstCurrency: Double
        val baseCostOfSecondCurrency: Double
        val result: Double

        // Если вторая валюта введена то конвертируем
        if (secondCurrencyValue.isNotEmpty()) {
            amountOfSecondCurrency = secondCurrencyValue.toDouble()
            baseCostOfFirstCurrency =
                baseCostOfCurrencyMutable[firstCurrencySelectedMutable.value!!]
            baseCostOfSecondCurrency =
                baseCostOfCurrencyMutable[secondCurrencySelectedMutable.value!!]
            result = (baseCostOfSecondCurrency / baseCostOfFirstCurrency) * amountOfSecondCurrency
            firstCurrencyValueMutable.value = String.format("%.2f", result)
        }

        // Если вторая валюта пустая то очищаем значение первой валюте
        if (secondCurrencyValue.isEmpty()) {
            firstCurrencyValueMutable.value = ""
        }

    }

    // Функция подсчета текущего курса валют
    private fun liveCurrency() {

        // Переменные для подсчета результата
        val firstCurrencyName: String = firstCurrencyNameMutable.value.toString()
        val secondCurrencyName: String = secondCurrencyNameMutable.value.toString()

        // Считаем текущий курс валют
        val baseCostOfFirstCurrency: Double =
            baseCostOfCurrencyMutable[firstCurrencySelectedMutable.value!!]
        val baseCostOfSecondCurrency: Double =
            baseCostOfCurrencyMutable[secondCurrencySelectedMutable.value!!]
        val result = String.format("%.2f", baseCostOfFirstCurrency / baseCostOfSecondCurrency)
        liveCurrencyValueMutable.value = "1 $firstCurrencyName = $result $secondCurrencyName"

    }

    // Функция перестановки валют местами
    fun changePosition() {
        val buffer: Int = firstCurrencySelectedMutable.value!!
        firstCurrencySelectedMutable.value = secondCurrencySelectedMutable.value
        secondCurrencySelectedMutable.value = buffer
    }


    fun setFirstSpinnerPosition(position: Int) {
        firstCurrencySelectedMutable.value = position
        changeFirstCurrency(position)
    }

    fun setSecondSpinnerPosition(position: Int) {
        secondCurrencySelectedMutable.value = position
        changeSecondCurrency(position)
    }

    private fun changeFirstCurrency(position: Int) {
        firstCurrencyNameMutable.value = currencyAbbreviation[position]
        firstCurrencyFlagMutable.value = currencyFlags[position]
        calculate(secondCurrencyValueMutable.value.toString())
        liveCurrency()
    }

    private fun changeSecondCurrency(position: Int) {
        secondCurrencyNameMutable.value = currencyAbbreviation[position]
        secondCurrencyFlagMutable.value = currencyFlags[position]
        calculate(secondCurrencyValueMutable.value.toString())
        liveCurrency()
    }




    // *Функции* (END) -----------------------------------------------------------------------------


}


