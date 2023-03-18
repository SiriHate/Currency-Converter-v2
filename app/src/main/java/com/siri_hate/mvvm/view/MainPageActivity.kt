package com.siri_hate.mvvm.view

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.siri_hate.mvvm.R
import com.siri_hate.mvvm.viewmodel.MainPageViewModel
import com.siri_hate.mvvm.viewmodel.MainPageViewModelFactory


class MainPageActivity : AppCompatActivity() {

    // Переменная ViewModel
    private lateinit var viewModel : MainPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)


        // Переменная для передачи аббревиатур валют в фабрику ViewModel
        val currencyAbbreviation = resources.getStringArray(R.array.CurrencyAbbreviation)
        // Переменная для передачи флагов валют в фабрику ViewModel
        val currencyFlags = intArrayOf(
            R.drawable.rub_flag,
            R.drawable.usd_flag,
            R.drawable.eur_flag,
            R.drawable.chf_flag,
            R.drawable.gbp_flag,
            R.drawable.jpy_flag,
            R.drawable.uah_flag,
            R.drawable.kzt_flag,
            R.drawable.byn_flag,
            R.drawable.try_flag,
            R.drawable.cny_flag,
            R.drawable.aud_flag,
            R.drawable.cad_flag,
            R.drawable.pln_flag
        )

        // Создание ViewModel и ее фабрики для данной Activity
        viewModel = ViewModelProvider(this, MainPageViewModelFactory(
            currencyAbbreviation,
            currencyFlags
        ))[MainPageViewModel::class.java]


        // *UI Objects* ----------------------------------------------------------------------------


        // ImageView

            /// (Флаги выбранных валют)
        val firstCurrencyFlagIcon: ImageView = findViewById(R.id.FirstCurrencyFlag)
        val secondCurrencyFlagIcon: ImageView = findViewById(R.id.SecondCurrencyFlag)

        // TextView

            /// (Значения валют)
        val firstCurrencyValue: TextView = findViewById(R.id.FirstCurrencyValue)
        val secondCurrencyValue: TextView = findViewById(R.id.SecondCurrencyValue)

            /// (Названия валют)
        val firstCurrencyName: TextView = findViewById(R.id.FirstCurrencyName)
        val secondCurrencyName: TextView = findViewById(R.id.SecondCurrencyName)

            /// (Значение текущего курса выбранных валют)
        val liveCurrencyValue: TextView = findViewById(R.id.LiveCurrencyValue)

        // Spinner

            /// (Выпадающие списки)
        val firstCurrencySpinner: Spinner = findViewById(R.id.FirstCurrencySpinner)
        val secondCurrencySpinner: Spinner = findViewById(R.id.SecondCurrencySpinner)


        // Button

            /// (Кнопки клавиатуры)
        val buttonZero: Button = findViewById(R.id.ButtonZero)
        val buttonOne: Button = findViewById(R.id.ButtonOne)
        val buttonTwo: Button = findViewById(R.id.ButtonTwo)
        val buttonThree: Button = findViewById(R.id.ButtonThree)
        val buttonFour: Button = findViewById(R.id.ButtonFour)
        val buttonFive: Button = findViewById(R.id.ButtonFive)
        val buttonSix: Button = findViewById(R.id.ButtonSix)
        val buttonSeven: Button = findViewById(R.id.ButtonSeven)
        val buttonEight: Button = findViewById(R.id.ButtonEight)
        val buttonNine: Button = findViewById(R.id.ButtonNine)
        val buttonDot: Button = findViewById(R.id.ButtonDot)
        val buttonClear: Button = findViewById(R.id.ButtonClear)


        // ImageButton

            /// (Кнопка для смены валют местами)
        val changeCurrencyPosition: ImageButton = findViewById(R.id.ChangeCurrencyPosition)


        // *UI Objects* (END)-----------------------------------------------------------------------


        // *Observers* -----------------------------------------------------------------------------

            /// (Подписка на обновления значений 1-й валюты)
        viewModel.firstCurrencyValueLiveData.observe(this){ text ->
            firstCurrencyValue.text = text
        }

            /// (Подписка на обновления значений 2-й валюты)
        viewModel.secondCurrencyValueLiveData.observe(this){ text ->
            secondCurrencyValue.text = text
        }

            /// (Подписка на обновления названия 1-й валюты)
        viewModel.firstCurrencyNameLiveData.observe(this){ text ->
            firstCurrencyName.text = text
        }

            /// (Флаг 1-й валюты)
        viewModel.firstCurrencyFlagLiveData.observe(this){ flag ->
            firstCurrencyFlagIcon.setImageResource(flag)
        }

            /// (Флаг 2-й валюты)
        viewModel.secondCurrencyFlagLiveData.observe(this){ flag ->
            secondCurrencyFlagIcon.setImageResource(flag)
        }

            /// (Подписка на обновления названия 2-й валюты)
        viewModel.secondCurrencyNameLiveData.observe(this){ text ->
            secondCurrencyName.text = text
        }

            /// (Подписка на обновления значений текущего курса валют)
        viewModel.liveCurrencyValueLiveData.observe(this){ text ->
            liveCurrencyValue.text = text
        }

            /// (Подписка на обновления значений выбранного элемента 1-го списка)
        viewModel.firstCurrencySelectedLiveData.observe(this) { selected ->
            firstCurrencySpinner.setSelection(selected)
        }

            /// (Подписка на обновления значений выбранного элемента 2-го списка)
        viewModel.secondCurrencySelectedLiveData.observe(this) { selected ->
            secondCurrencySpinner.setSelection(selected)
        }

            /// (Подписка на обновления значений текущего курса валют)
        viewModel.liveCurrencyValueLiveData.observe(this) { text ->
            liveCurrencyValue.text = text
        }


        // *Observers* (END) -----------------------------------------------------------------------


        // *Listeners* -----------------------------------------------------------------------------


            /// Обработка нажатий кнопок клавиатуры
        buttonZero.setOnClickListener {
            viewModel.keyboard("0")
        }

        buttonOne.setOnClickListener {
            viewModel.keyboard("1")
        }

        buttonTwo.setOnClickListener {
            viewModel.keyboard("2")
        }

        buttonThree.setOnClickListener {
            viewModel.keyboard("3")
        }

        buttonFour.setOnClickListener {
            viewModel.keyboard("4")
        }

        buttonFive.setOnClickListener {
            viewModel.keyboard("5")
        }

        buttonSix.setOnClickListener {
            viewModel.keyboard("6")
        }

        buttonSeven.setOnClickListener {
            viewModel.keyboard("7")
        }

        buttonEight.setOnClickListener {
            viewModel.keyboard("8")
        }

        buttonNine.setOnClickListener {
            viewModel.keyboard("9")
        }

        buttonClear.setOnClickListener {
            viewModel.keyboard("Clear")
        }

        buttonDot.setOnClickListener {
            viewModel.keyboard("Dot")
        }


            /// Кнопка перестановки валют местами
        changeCurrencyPosition.setOnClickListener {
            viewModel.changePosition()
        }


            /// Передача выбранного элемента 1-го списка в LiveData переменную выбранного пункта
        firstCurrencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                viewModel.setFirstSpinnerPosition(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

            /// Передача выбранного элемента 2-го списка в LiveData переменную выбранного пункта
        secondCurrencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                viewModel.setSecondSpinnerPosition(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }


        // *Listeners* (END) -----------------------------------------------------------------------

        // *Adapters* ------------------------------------------------------------------------------


        // Создание адаптера для обоих выпадающих списков
        ArrayAdapter.createFromResource(this, R.array.CurrencyNames,
            R.layout.spinner_layout).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
            firstCurrencySpinner.adapter = adapter
            secondCurrencySpinner.adapter = adapter
            firstCurrencySpinner.setSelection(1)
            secondCurrencySpinner.setSelection(0)
        }


        // *Adapters* (END) ------------------------------------------------------------------------


    }
}

