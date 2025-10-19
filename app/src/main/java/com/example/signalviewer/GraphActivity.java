package com.example.signalviewer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {
    public final String LOG_TAG = "GRAPH_ACTIVITY";
    public final String SIGNAL_LABLE = "Синусоида";
    public final String SPECTRUM_LABLE = "БПФ";
    private Intent intent;


    //Заданные параметры сигнала
    private float Amplitude;
    private float Frequency;
    private float Phase;
    private float Duration;

    //Постоянные параметры сигналов
    private float Sample_Rate = 1000f;
    private int numSamples;

    //Массивы данных сигнала
    Complex [] c_signal;
    Complex [] c_spectrum;

    //Переменные вывода сигнала
    ArrayList<Entry> signal;
    ArrayList<Entry> spectrum;

    private LineChart lineChart_signal;
    private LineChart lineChart_spectrum;
    private Complex[] generateSinus(){
        Complex[] sinus = new Complex[numSamples];
        for (int i = 0; i < numSamples; i ++) {
            float t = (float) i/Sample_Rate;
            float value = Amplitude * (float) (Math.sin(2*Math.PI*Frequency*t+Phase));
            sinus[i] = new Complex(value, 0);
        }
        return sinus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        intent = getIntent();

        lineChart_signal = findViewById(R.id.lineChart_signal);
        lineChart_spectrum = findViewById(R.id.lineChart_spectrum);

        //Получение данных из main acntivity
        Amplitude =(float) intent.getDoubleExtra(MainActivity.STR_KEY_AMP,1);
        Frequency =(float) intent.getDoubleExtra(MainActivity.STR_KEY_FREQ,100);
        Phase =(float) intent.getDoubleExtra(MainActivity.STR_KEY_PHASE,0);
        Duration =(float) intent.getDoubleExtra(MainActivity.STR_KEY_PHASE,2);

        numSamples = (int) (Sample_Rate * Duration);
        if(!FFT.isPowerOfTwo(numSamples)){
            numSamples = FFT.nextPowerOf2(numSamples);
            Sample_Rate = (float) (numSamples / Duration);
        }

        signal = new ArrayList<>();
        spectrum = new ArrayList<>();
        c_signal = generateSinus();
        Log.d(LOG_TAG, "Sinus generated");
        c_spectrum = FFT.fft(c_signal);
        Log.d(LOG_TAG, "Spectrum generated "+ c_spectrum.length);

    }
    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart");
        super.onStart();
        /*Использование numSamples в качестве условия цикла обусловлено
        одинаковой размерностью дискретного сигнала и его БПФ (исходя из реализации БПФ)*/
        for(int i =0; i<numSamples; i++){
            float n = (float) i/Sample_Rate;
            signal.add(new Entry(n, c_signal[i].re));
            spectrum.add(new Entry(n, c_spectrum[i].abs()/numSamples)); //Деление на numSamples обусловлено нормировкой амплитуды БПФ
        }

        //Вывод графиков

        LineDataSet dataSet_signal = new LineDataSet(signal, SIGNAL_LABLE);
        dataSet_signal.setColor(getResources().getColor(android.R.color.black));
        dataSet_signal.setValueTextColor(getResources().getColor(android.R.color.holo_blue_light));

        LineDataSet dataSet_spectrum = new LineDataSet(spectrum, SPECTRUM_LABLE);
        dataSet_spectrum.setColor(getResources().getColor(android.R.color.black));
        dataSet_spectrum.setValueTextColor(getResources().getColor(android.R.color.holo_blue_light));

        dataSet_signal.setDrawCircles(false);
        dataSet_spectrum.setDrawCircles(false);


        LineData lineData_signal = new LineData(dataSet_signal);
        LineData lineData_spectrum = new LineData(dataSet_spectrum);
        lineChart_signal.setData(lineData_signal);
        lineChart_spectrum.setData(lineData_spectrum);
    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        super.onDestroy();
    }

}

