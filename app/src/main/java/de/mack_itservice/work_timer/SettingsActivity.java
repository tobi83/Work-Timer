package de.mack_itservice.work_timer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Mack on 08.04.2015.
 */
public class SettingsActivity extends Activity {

    private Button saveButton;
    private EditText tbRate;
    private EditText tbMailAddress;

    private RadioGroup radioButtonGroup;
    private RadioButton selectedRadioButton;

    public static final String PREFS_NAME = "MyApp_Settings";

    private static final String TAG = MainActivity.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // Den Spinner befüllen
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        tbRate = (EditText) findViewById(R.id.hourlyRate);
        tbMailAddress = (EditText) findViewById(R.id.mailAddress);
        radioButtonGroup = (RadioGroup) findViewById(R.id.radioButtonGroup);

        int selectedId = radioButtonGroup.getCheckedRadioButtonId();
        selectedRadioButton = (RadioButton) findViewById(selectedId);

        // Den gespeicherten Wert für den Stundenlohn laden
        String rate = settings.getString("stundenlohn", "");
        tbRate.setText(rate);

        // Den gespeicherten Wert für die Mail-Adresse laden
        String mailaddress = settings.getString("mailadresse", "");
        tbMailAddress.setText(mailaddress);

        // Den gespeicherten Wert für den Abrechnungszeitraum laden
        final String billingrate = settings.getString("abrechnungszeitraum", "");

        if (billingrate.contains("1/4")) {
            radioButtonGroup.check(R.id.radioButtonBeforehand);
            spinner.setSelection(0);
        }
        else if (billingrate.contains("1/2")) {
            radioButtonGroup.check(R.id.radioButtonBeforehand);
            spinner.setSelection(1);
        }
        else if (billingrate.contains("1 h")) {
            radioButtonGroup.check(R.id.radioButtonBeforehand);
            spinner.setSelection(2);
        }
        else if (billingrate.contains("genau")) {
            radioButtonGroup.check(R.id.radioButtonExact);
        }

        final Spinner selection;
        selection = (Spinner)findViewById(R.id.spinner);

        // Speichern der Einstellungen
        saveButton = (Button) findViewById(R.id.startButton_old);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Es wird geprüft, ob alle Felder ausgefüllt/ausgewählt sind
                RadioButton rb;
                rb = (RadioButton) findViewById(R.id.radioButtonBeforehand);
                RadioButton rb2;
                rb2 = (RadioButton) findViewById(R.id.radioButtonExact);
                if (tbRate.getText().length() != 0 && tbMailAddress.getText().length() != 0 && (rb.isChecked() || rb2.isChecked())) {
                    if (tbMailAddress.getText().toString().contains("@") && tbMailAddress.getText().toString().contains(".")) {
                        // Den eingegebenen Stundenlohn speichern
                        String rate = tbRate.getText().toString();
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("stundenlohn", rate);
                        //Die eingegebene Mail-Adresse speichern
                        String mailaddress = tbMailAddress.getText().toString();
                        editor.putString("mailadresse", mailaddress);
                        editor.commit();

                        int selectedId = radioButtonGroup.getCheckedRadioButtonId();
                        selectedRadioButton = (RadioButton) findViewById(selectedId);

                        // Den Abrechnungszeitraum auslesen
                        if (selectedRadioButton.getText().toString().contains("Voraus")) {
                            String billingcycle = selection.getSelectedItem().toString();
                            editor.putString("abrechnungszeitraum", billingcycle);
                        }
                        else if (selectedRadioButton.getText().toString().contains("genau")) {
                            editor.putString("abrechnungszeitraum", "minutengenau");
                        }

                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Gespeichert.", Toast.LENGTH_SHORT).show();

                        // Jetzt werden der Abrechnungszeitraum und der Stundenlohn auf der Startseite angezeigt
                        // und es wird dorthin gewechselt
                        Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
                        nextScreen.putExtra("abrechnungszeitraum2", tbRate.getText());
                        nextScreen.putExtra("stundenlohn2", tbRate.getText());
                        startActivity(nextScreen);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Bitte eine gültige E-Mail Adresse angeben!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Bitte alles ausfüllen!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}