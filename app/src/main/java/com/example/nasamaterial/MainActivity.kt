package com.example.nasamaterial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasa_photo.FragmentMain

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FragmentMain.newInstance())
            .commitNow()
    }
}

//- Добавьте описание (приходит с сервера) под фотографией
//- Изучите API NASA, который мы будем использовать в течение курса, и продумайте,
//    как вы выстроите своё приложение, какие там будут экраны.
//    - Создайте первый экран приложения с фотографией дня.
//    - Добавьте описание (приходит с сервера) под фотографией в виде текстовой подписи или в виде BottomSheet.
//    - Добавьте текстовое поле для поиска неизвестных слов в «Википедии».
//    - Добавьте адаптивную иконку для вашего приложения.