package de.mack_itservice.work_timer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // Wird für das Popup-benötigt zur Eingabe eines Kunden
    final Context context = this;

    // Es wird nachher abgefragt ob das Configfile existiert, hier wird es deklariert
    File configfile = new File("/data/data/de.mack_itservice.work_timer/shared_prefs/MyApp_Settings.xml");

    private ImageButton startStopButton;
    private ImageButton calcButton;
    private ImageButton resetButton;
    private ImageButton saveButton;

    private TextView tbWorktime;
    private TextView tbCosts;
    private TextView tbStartTime1;
    private TextView tbStopTime1;
    private TextView tbStartTime2;
    private TextView tbStopTime2;
    private TextView tbStartSummary;

    // Die Eingabe des Kunden beim Speichern des Berichts
    public String kunde;

    double compltime = 0;
    double calchelp = 0;

    double bill = 0;
    double rate = 0;
    int billingcycle = 0;
    String billingcycletxt;

    String time1;
    String time2;

    private static final String TAG = MainActivity.class.getName();
    public static final String PREFS_NAME = "MyApp_Settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Die Buttons werden deklariert
        tbStartTime1 = (TextView) findViewById(R.id.startTime1);
        tbStopTime1 = (TextView) findViewById(R.id.stopTime1);
        tbStartTime2 = (TextView) findViewById(R.id.startTime2);
        tbStopTime2 = (TextView) findViewById(R.id.stopTime2);
        tbCosts = (TextView) findViewById(R.id.costs);
        tbWorktime = (TextView) findViewById(R.id.workTime);
        tbStartSummary = (TextView) findViewById(R.id.startSummary);
        startStopButton = (ImageButton) findViewById(R.id.startStopButton);
        calcButton = (ImageButton) findViewById(R.id.calcButton);
        resetButton = (ImageButton) findViewById(R.id.resetButton);
        saveButton = (ImageButton) findViewById(R.id.saveButton);

        // Der Zwischenbericht/Reset- und der Save-Button werden bei Programmstart deaktiviert
        calcButton.setEnabled(false);
        calcButton.setBackgroundColor(Color.parseColor("#efefef"));
        resetButton.setEnabled(false);
        resetButton.setBackgroundColor(Color.parseColor("#efefef"));
        saveButton.setEnabled(false);
        saveButton.setBackgroundColor(Color.parseColor("#efefef"));

        //final SharedPreferences settings2 = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        if (configfile.exists()) {
            // Das Configfile existiert

            // Der Abrechnungszeitraum und der Stundenlohn werden ausgelesen aus der SettingsActivity
            Intent i = getIntent();
            String abrechnungszeitraum = i.getStringExtra("abrechnungszeitraum2");
            String stundenlohn = i.getStringExtra("stundenlohn2");

            final SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

            // Das Programm wurde frisch gestartet, also kein Rückgabewert aus der SettingsActivity vorhanden
            if (abrechnungszeitraum == null) {
                abrechnungszeitraum = (settings.getString("abrechnungszeitraum", ""));
            }
            if (stundenlohn == null) {
                stundenlohn = (settings.getString("stundenlohn", ""));
            }

            tbStartSummary.setText("Abrechnungszeitraum: " + abrechnungszeitraum + "\nStundenlohn: " + stundenlohn + " €");
        } else {
            Toast.makeText(getApplicationContext(), "Programm wurde das erste mal gestartet. Bitte in die Einstellungen gehen!", Toast.LENGTH_SHORT).show();
            final SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("stundenlohn", "");
            //editor.putString("arbeitszeitraum", "");

            editor.putString("datum1", "");
            editor.putString("arbeitszeit1", "");
            editor.putString("pause1", "");
            editor.putString("start1", "");
            editor.putString("stop1", "");
            editor.putString("rechnung1", "");
            editor.putString("kunde1", "");

            editor.putString("datum2", "");
            editor.putString("arbeitszeit2", "");
            editor.putString("pause2", "");
            editor.putString("start2", "");
            editor.putString("stop2", "");
            editor.putString("rechnung2", "");
            editor.putString("kunde2", "");

            editor.putString("datum3", "");
            editor.putString("arbeitszeit3", "");
            editor.putString("pause3", "");
            editor.putString("start3", "");
            editor.putString("stop3", "");
            editor.putString("rechnung3", "");
            editor.putString("kunde3", "");

            editor.putString("datum4", "");
            editor.putString("arbeitszeit4", "");
            editor.putString("pause4", "");
            editor.putString("start4", "");
            editor.putString("stop4", "");
            editor.putString("rechnung4", "");
            editor.putString("kunde4", "");

            editor.putString("datum5", "");
            editor.putString("arbeitszeit5", "");
            editor.putString("pause5", "");
            editor.putString("start5", "");
            editor.putString("stop5", "");
            editor.putString("rechnung5", "");
            editor.putString("kunde5", "");

            editor.putString("datum6", "");
            editor.putString("arbeitszeit6", "");
            editor.putString("pause6", "");
            editor.putString("start6", "");
            editor.putString("stop6", "");
            editor.putString("rechnung6", "");
            editor.putString("kunde6", "");
            editor.commit();
        }

        startStopButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                // Der eingestellte Abrechnungszeitraum/Stundenlohn werden ausgeblendet
                tbStartSummary.setText("");
                tbStartSummary.setVisibility(view.INVISIBLE);

                // Der eingegebene Stundensatz wird ausgelesen
                final SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                rate = Double.parseDouble(settings.getString("stundenlohn", ""));

                // Der Abrechnungszeitraum wird ausgelesen
                rate = Double.parseDouble(settings.getString("stundenlohn", ""));
                billingcycletxt = (settings.getString("abrechnungszeitraum", ""));

                if (billingcycletxt.contains("1/4")) {
                    billingcycle = 900;
                    rate = rate / 4;
                } else if (billingcycletxt.contains("1/2")) {
                    billingcycle = 1800;
                    rate = rate / 2;
                } else if (billingcycletxt.contains("1 h")) {
                    billingcycle = 3600;
                } else if (billingcycletxt.contains("minutengenau")) {
                    billingcycle = 60;
                    rate = rate / 60;
                }

                // Der Timer wird zum ersten mal gestartet (tbStartTime1 ist leer)
                if (!tbStartTime1.getText().toString().contains(":")) {
                    DateFormat df = new SimpleDateFormat("HH:mm");
                    time1 = df.format(Calendar.getInstance().getTime());
                    tbStartTime1.setText(time1);

                    startStopButton.setImageResource(R.drawable.stop_50x50);
                    calcButton.setEnabled(true);
                    calcButton.setBackgroundColor(Color.parseColor("#d9d9d9"));
                    resetButton.setEnabled(false);
                    resetButton.setBackgroundColor(Color.parseColor("#f1f1f1"));
                }
                // nur tbStartTime1 ist belegt
                else if ((tbStartTime1.getText().toString().contains(":")) && (!tbStopTime1.getText().toString().contains(":") && (!tbStartTime2.getText().toString().contains(":")) && (!tbStopTime2.getText().toString().contains(":")))) {
                    TimerStoppen1();
                }

                // Die ersten beiden Textboxen sind belegt
                else if ((tbStartTime1.getText().toString().contains(":")) && (tbStopTime1.getText().toString().contains(":") && (!tbStartTime2.getText().toString().contains(":")) && (!tbStopTime2.getText().toString().contains(":")))) {
                    DateFormat df = new SimpleDateFormat("HH:mm");
                    time1 = df.format(Calendar.getInstance().getTime());
                    tbStartTime2.setText(time1);
                    tbWorktime.setText("");
                    tbCosts.setText("");

                    startStopButton.setImageResource(R.drawable.stop_50x50);
                    calcButton.setEnabled(true);
                    calcButton.setBackgroundColor(Color.parseColor("#d9d9d9"));
                    saveButton.setEnabled(false);
                    saveButton.setBackgroundColor(Color.parseColor("#f1f1f1"));
                    resetButton.setEnabled(false);
                    resetButton.setBackgroundColor(Color.parseColor("#f1f1f1"));
                }
                // Die ersten 3 Textboxen sind belegt
                else if ((tbStartTime1.getText().toString().contains(":")) && (tbStopTime1.getText().toString().contains(":") && (tbStartTime2.getText().toString().contains(":")) && (!tbStopTime2.getText().toString().contains(":")))) {
                    TimerStoppen2();
                }
            }
        });

        // Zwischenanzeige-Button
        calcButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                // nur tbStartTime1 ist belegt
                if ((tbStartTime1.getText().toString().contains(":")) && (!tbStopTime1.getText().toString().contains(":") && (!tbStartTime2.getText().toString().contains(":")) && (!tbStopTime2.getText().toString().contains(":")))) {
                    DateFormat df = new SimpleDateFormat("HH:mm");
                    time1 = df.format(Calendar.getInstance().getTime());
                    time2 = df.format(Calendar.getInstance().getTime());

                    String time1 = tbStartTime1.getText().toString();
                    String time2 = df.format(Calendar.getInstance().getTime());

                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = df.parse(time1);
                        date2 = df.parse(time2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Die Zeit-Differenz ausrechnen
                    double dif = date2.getTime() - date1.getTime();
                    //Toast.makeText(getApplicationContext(), "differenz: " + dif, Toast.LENGTH_SHORT).show();

                    compltime = dif / 1000;

                    int compltimehelp = (int) compltime;

                    // Die komplette Arbeitszeit (compltime) wird in INT umgerechnet, sonst stimmt das Ergebnis nachher nicht
                    calchelp = (compltimehelp - 1) / billingcycle;
                    bill = ((calchelp + 1) * (rate));

                    // Die Stunden und die Minuten werden aus der Komplett-Zeit berechnet
                    int stunden = (int) compltime / 60 / 60;
                    int minuten = (int) (compltime - stunden * 3600) / 60;

                    String rechnung_gekuerzt = Double.toString(bill);

                    // Wenn die Zeit 00:00 ist, dann wird auch noch nichts berechnet
                    if ((stunden == 0) && (minuten == 0)) {
                        rechnung_gekuerzt = "0";
                    } else if (rechnung_gekuerzt.length() > 5) {
                        rechnung_gekuerzt = rechnung_gekuerzt.substring(0, 6);
                    }

                    // Die Stunden+Minuten werden in Strings umgewandelt, damit bei Bedarf eine 0 vorangestellt werden kann
                    String stundenstring = String.valueOf(stunden);
                    String minutenstring = String.valueOf(minuten);
                    if (stunden < 10) {
                        stundenstring = "0" + stundenstring;
                    }
                    if (minuten < 10) {
                        minutenstring = "0" + minutenstring;
                    }

                    // Die Arbeitszeit und die Kosten werden ausgegeben
                    Toast.makeText(getApplicationContext(), "Zeit: " + stundenstring + ":" + minutenstring + " | Rechnung: " + rechnung_gekuerzt + " €", Toast.LENGTH_SHORT).show();
                }
                // Die ersten 3 Textboxen sind belegt
                else if ((tbStartTime1.getText().toString().contains(":")) && (tbStopTime1.getText().toString().contains(":") && (tbStartTime2.getText().toString().contains(":")) && (!tbStopTime2.getText().toString().contains(":")))) {
                    DateFormat df = new SimpleDateFormat("HH:mm");
                    time1 = df.format(Calendar.getInstance().getTime());
                    time2 = df.format(Calendar.getInstance().getTime());

                    String time1 = tbStartTime1.getText().toString();
                    String time2 = tbStopTime1.getText().toString();
                    String time3 = tbStartTime2.getText().toString();
                    String time4 = df.format(Calendar.getInstance().getTime());

                    Date date1 = null;
                    Date date2 = null;
                    Date date3 = null;
                    Date date4 = null;
                    try {
                        date1 = df.parse(time1);
                        date2 = df.parse(time2);
                        date3 = df.parse(time3);
                        date4 = df.parse(time4);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Die Zeit-Differenz ausrechnen
                    double dif = date2.getTime() - date1.getTime();
                    double dif2 = date4.getTime() - date3.getTime();
                    double difgesamt = dif + dif2;

                    compltime = difgesamt / 1000;

                    int compltimehelp = (int) compltime;

                    // Die komplette Arbeitszeit (compltime) wird in INT umgerechnet, sonst stimmt das Ergebnis nachher nicht
                    calchelp = (compltimehelp - 1) / billingcycle;
                    bill = ((calchelp + 1) * (rate));

                    String rechnung_gekuerzt = Double.toString(bill);

                    // Die Stunden und die Minuten werden aus der Komplett-Zeit berechnet
                    int stunden = (int) compltime / 60 / 60;
                    int minuten = (int) (compltime - stunden * 3600) / 60;

                    // Wenn die Zeit 00:00 ist, dann wird auch noch nichts berechnet
                    if ((stunden == 0) && (minuten == 0)) {
                        rechnung_gekuerzt = "0";
                    } else if (rechnung_gekuerzt.length() > 5) {
                        rechnung_gekuerzt = rechnung_gekuerzt.substring(0, 6);
                    }

                    // Die Stunden+Minuten werden in Strings umgewandelt, damit bei Bedarf eine 0 vorangestellt werden kann
                    String stundenstring = String.valueOf(stunden);
                    String minutenstring = String.valueOf(minuten);
                    if (stunden < 10) {
                        stundenstring = "0" + stundenstring;
                    }
                    if (minuten < 10) {
                        minutenstring = "0" + minutenstring;
                    }

                    // Die Arbeitszeit und die Kosten werden ausgegeben
                    Toast.makeText(getApplicationContext(), "Zeit: " + stundenstring + ":" + minutenstring + " | Rechnung: " + rechnung_gekuerzt + " €", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Reset-Button: Es soll alles zurückgesetzt werden
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ArbeitszeitUndKostenZuruecksetzen();
            }
        });

        // Speichern-Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BerichtSpeichern_EingabeKunde();
            }
        });
    }

    // Funktion zum Zurücksetzen der aufgezeichneten Arbeitszeit/-kosten
    protected void ArbeitszeitUndKostenZuruecksetzen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Alles zurücksetzen?")
                //.setMessage("Bericht senden?")
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Der Abrechnungszeitraum und der Stundenlohn werden ausgelesen
                        tbStartSummary.setVisibility(View.VISIBLE);
                        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        String abrechnungszeitraum = (settings.getString("abrechnungszeitraum", ""));
                        String stundenlohn = (settings.getString("stundenlohn", ""));
                        tbStartSummary.setText("Abrechnungszeitraum: " + abrechnungszeitraum + "\nStundenlohn: " + stundenlohn + " €");

                        startStopButton.setEnabled(true);
                        startStopButton.setImageResource(R.drawable.start_50x50);
                        startStopButton.setBackgroundColor(Color.parseColor("#d9d9d9"));
                        tbStartTime1.setText("");
                        tbStopTime1.setText("");
                        tbStartTime2.setText("");
                        tbStopTime2.setText("");
                        tbWorktime.setText("");
                        tbCosts.setText("");
                        compltime = 0;
                        bill = 0;
                        resetButton.setEnabled(false);
                        resetButton.setBackgroundColor(Color.parseColor("#f1f1f1"));
                        calcButton.setEnabled(false);
                        calcButton.setBackgroundColor(Color.parseColor("#f1f1f1"));
                        saveButton.setEnabled(false);
                        saveButton.setBackgroundColor(Color.parseColor("#f1f1f1"));
                    }
                })
                .setNegativeButton("Nein", null) // Nichts machen
                .show();
    }

    // Funktion die beim Klick auf den Speichern-Button nachfragt, ob auch eine Mail geschickt werden soll
    protected void NachfrageObMailVersendetWerdenSoll() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("E-Mail mit Tätigkeits-Bericht versenden?")
                .setMessage("Bericht senden?")
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "Es wurde ja gedrückt", Toast.LENGTH_SHORT).show();
                        BerichtPerMailSenden();
                    }
                })
                .setNegativeButton("Nein", null) // Nichts machen
                .show();
    }

    // Funktion, die den Bericht per E-Mail versendet
    protected void BerichtPerMailSenden() {
        // Das Datum muss ausgelesen werden für den Bericht
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        // Die Mail-Adresse wird ausgelesen aus der Configdatei
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String[] mailaddress = {(settings.getString("mailadresse", ""))};

        //String[] TO = {"info@mack-itservice.de"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, mailaddress);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bericht Service-Leistung ("+date+")");

        String body = "";

        // Es gab eine zweite Arbeitszeit
        if (tbStartTime2.getText().toString().contains(":")) {
            body = "Bericht zur Service-Leistung vom " + date +
                    "\n-----------------------------------------------------------------" +
                    "\n\nKunde: " + kunde +
                    "\nArbeitszeit: " + tbWorktime.getText().toString().substring(6) +
                    "\nBeginn 1: " + tbStartTime1.getText() +
                    "\nEnde 1: " + tbStopTime1.getText() +
                    "\nBeginn 2: " + tbStartTime2.getText() +
                    "\nEnde 2: " + tbStopTime2.getText() +
                    "\nRechnung: " + Double.toString(bill) + " €" +
                    "\n\n" + "Automatisch generiert über Work-Timer für Android";
        }
        // Es gab keine zweite Arbeitszeit
        else {
            body = "Bericht zur Service-Leistung vom " + date +
                    "\n-----------------------------------------------------------------" +
                    "\n\nKunde: " + kunde +
                    "\nArbeitszeit: " + tbWorktime.getText().toString().substring(6) +
                    "\nBeginn: " + tbStartTime1.getText().toString() +
                    "\nEnde: " + tbStopTime1.getText().toString() +
                    "\nRechnung: " + Double.toString(bill) + " €" +
                    "\n\n" + "Automatisch generiert über Work-Timer für Android";
        }
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
                startActivity(Intent.createChooser(emailIntent, "Bitte Mail-App auswählen..."));
                finish();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this,
                        "Kein Mail-Client installiert!", Toast.LENGTH_SHORT).show();
            }
        }

    // Es wird der Stop-Button gedrückt (zum ersten mal)
    protected void TimerStoppen1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Den Timer stoppen?")
                .setCancelable(false)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DateFormat df = new SimpleDateFormat("HH:mm");
                                time1 = df.format(Calendar.getInstance().getTime());
                                time2 = df.format(Calendar.getInstance().getTime());
                                tbStopTime1.setText(time2);

                                startStopButton.setImageResource(R.drawable.start_50x50);
                                calcButton.setEnabled(false);
                                calcButton.setBackgroundColor(Color.parseColor("#f1f1f1"));
                                saveButton.setEnabled(true);
                                saveButton.setBackgroundColor(Color.parseColor("#d9d9d9"));
                                resetButton.setEnabled(true);
                                resetButton.setBackgroundColor(Color.parseColor("#d9d9d9"));

                                String time1 = tbStartTime1.getText().toString();
                                String time2 = tbStopTime1.getText().toString();

                                Date date1 = null;
                                Date date2 = null;
                                try {
                                    date1 = df.parse(time1);
                                    date2 = df.parse(time2);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                // Die Zeit-Differenz ausrechnen
                                double dif = date2.getTime() - date1.getTime();

                                compltime = dif / 1000;

                                int compltimehelp = (int) compltime;

                                // Die komplette Arbeitszeit (compltime) wird in INT umgerechnet, sonst stimmt das Ergebnis nachher nicht
                                calchelp = (compltimehelp - 1) / billingcycle;
                                bill = ((calchelp + 1) * (rate));

                                // Die Stunden und die Minuten werden aus der Komplett-Zeit berechnet
                                int stunden = (int) compltime / 60 / 60;
                                int minuten = (int) (compltime - stunden * 3600) / 60;

                                String rechnung_gekuerzt = Double.toString(bill);

                                // Wenn die Zeit 00:00 ist, dann wird auch noch nichts berechnet
                                if ((stunden == 0) && (minuten == 0)) {
                                    rechnung_gekuerzt = "0";
                                } else if (rechnung_gekuerzt.length() > 5) {
                                    rechnung_gekuerzt = rechnung_gekuerzt.substring(0, 6);
                                }

                                // Die Stunden+Minuten werden in Strings umgewandelt, damit bei Bedarf eine 0 vorangestellt werden kann
                                String stundenstring = String.valueOf(stunden);
                                String minutenstring = String.valueOf(minuten);
                                if (stunden < 10) {
                                    stundenstring = "0" + stundenstring;
                                }
                                if (minuten < 10) {
                                    minutenstring = "0" + minutenstring;
                                }

                                // Die Arbeitszeit und die Kosten werden ausgegeben
                                tbWorktime.setText("Zeit: " + stundenstring + ":" + minutenstring);
                                tbCosts.setText("Rechnung: " + rechnung_gekuerzt + " €");
                            }
                        }

                )
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // some code if you want
                                dialog.cancel();
                            }
                        }

                );
        AlertDialog alert = builder.create();
        alert.show();
    }

    // Es wurde der Stop-Button gedrückt (zum zweiten mal)
    protected void TimerStoppen2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Den Timer stoppen?")
                .setCancelable(false)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DateFormat df = new SimpleDateFormat("HH:mm");
                                time1 = df.format(Calendar.getInstance().getTime());
                                time2 = df.format(Calendar.getInstance().getTime());
                                tbStopTime2.setText(time2);

                                startStopButton.setImageResource(R.drawable.start_50x50);
                                startStopButton.setEnabled(false);
                                startStopButton.setBackgroundColor(Color.parseColor("#f1f1f1"));
                                calcButton.setEnabled(false);
                                calcButton.setBackgroundColor(Color.parseColor("#f1f1f1"));
                                saveButton.setEnabled(true);
                                saveButton.setBackgroundColor(Color.parseColor("#d9d9d9"));
                                resetButton.setEnabled(true);
                                resetButton.setBackgroundColor(Color.parseColor("#d9d9d9"));

                                String time1 = tbStartTime1.getText().toString();
                                String time2 = tbStopTime1.getText().toString();
                                String time3 = tbStartTime2.getText().toString();
                                String time4 = tbStopTime2.getText().toString();

                                Date date1 = null;
                                Date date2 = null;
                                Date date3 = null;
                                Date date4 = null;
                                try {
                                    date1 = df.parse(time1);
                                    date2 = df.parse(time2);
                                    date3 = df.parse(time3);
                                    date4 = df.parse(time4);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                // Die Zeit-Differenz ausrechnen
                                double dif = date2.getTime() - date1.getTime();
                                double dif2 = date4.getTime() - date3.getTime();
                                double difgesamt = dif + dif2;

                                compltime = difgesamt / 1000;

                                int compltimehelp = (int) compltime;

                                // Die komplette Arbeitszeit (compltime) wird in INT umgerechnet, sonst stimmt das Ergebnis nachher nicht
                                calchelp = (compltimehelp - 1) / billingcycle;
                                bill = ((calchelp + 1) * (rate));

                                String rechnung_gekuerzt = Double.toString(bill);

                                // Die Stunden und die Minuten werden aus der Komplett-Zeit berechnet
                                int stunden = (int) compltime / 60 / 60;
                                int minuten = (int) (compltime - stunden * 3600) / 60;

                                // Wenn die Zeit 00:00 ist, dann wird auch noch nichts berechnet
                                if ((stunden == 0) && (minuten == 0)) {
                                    rechnung_gekuerzt = "0";
                                } else if (rechnung_gekuerzt.length() > 5) {
                                    rechnung_gekuerzt = rechnung_gekuerzt.substring(0, 6);
                                }

                                // Die Stunden+Minuten werden in Strings umgewandelt, damit bei Bedarf eine 0 vorangestellt werden kann
                                String stundenstring = String.valueOf(stunden);
                                String minutenstring = String.valueOf(minuten);
                                if (stunden < 10) {
                                    stundenstring = "0" + stundenstring;
                                }
                                if (minuten < 10) {
                                    minutenstring = "0" + minutenstring;
                                }

                                // Die Arbeitszeit und die Kosten werden ausgegeben
                                tbWorktime.setText("Zeit: " + stundenstring + ":" + minutenstring);
                                tbCosts.setText("Rechnung: " + rechnung_gekuerzt + " €");
                            }
                        }

                )
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // some code if you want
                                dialog.cancel();
                            }
                        }

                );
        AlertDialog alert = builder.create();
        alert.show();
    }

    // Beim Klick auf den Speichern-Button wird der Kunde abgefragt
    protected void BerichtSpeichern_EingabeKunde() {
        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                kunde =  userInput.getText().toString();
                                BerichtSpeichern();
                                NachfrageObMailVersendetWerdenSoll();
                            }
                        })
                .setNegativeButton("Abbruch",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    // Funktion zum Speichern des Berichts
    protected void BerichtSpeichern() {

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        if ((tbStartTime1.getText().toString().contains(":")) && (tbStopTime1.getText().toString().contains(""))) {
            // Es wird ermittelt, ob eine Pause gemacht wurde
            String pause;
            if (tbStartTime2.getText().toString().contains(":")) {
                DateFormat df = new SimpleDateFormat("HH:mm");

                String time1 = tbStopTime1.getText().toString();
                String time2 = tbStartTime2.getText().toString();

                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = df.parse(time1);
                    date2 = df.parse(time2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Die Zeit-Differenz ausrechnen
                double dif = date2.getTime()- date1.getTime();
                compltime = dif / 1000;

                // Die Stunden und die Minuten werden aus der Komplett-Zeit berechnet
                int stunden = (int) compltime / 60 / 60;
                int minuten = (int) (compltime-stunden*3600)/60;

                // Die Stunden+Minuten werden in Strings umgewandelt, damit bei Bedarf eine 0 vorangestellt werden kann
                String stundenstring = String.valueOf(stunden);
                String minutenstring = String.valueOf(minuten);
                if (stunden < 10) {
                    stundenstring = "0" + stundenstring;
                }
                if (minuten < 10) {
                    minutenstring = "0" + minutenstring;
                }
                pause = stundenstring + ":" + minutenstring;
            }
            else {
                pause = "nein";
            }

            // Alle Speicherplätze sind noch frei
            if (settings.getString("datum1", "").toString() == "" && settings.getString("datum2", "").toString() == "") {
                // Werte werden geschrieben
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                String date = df.format(Calendar.getInstance().getTime());
                editor.putString("datum1", date);
                editor.putString("arbeitszeit1", tbWorktime.getText().toString().substring(6));
                editor.putString("start1", tbStartTime1.getText().toString());
                editor.putString("stop1", tbStopTime1.getText().toString());
                editor.putString("pause1", pause);
                editor.putString("rechnung1", Double.toString(bill) + " €");
                editor.putString("kunde1", kunde);
                editor.commit();
                Toast.makeText(getApplicationContext(), "Gespeichert", Toast.LENGTH_SHORT).show();
            }
            // Zeile 1 ist belegt, alles wird eins nach unten geschoben
            else if (settings.getString("datum1", "").toString() != "") {

                String temp;

                // Zeile 1 wird in Zeile 2 verschoben
                temp = settings.getString("datum1", "").toString();
                editor.putString("datum2", temp);
                temp = settings.getString("arbeitszeit1", "").toString();
                editor.putString("arbeitszeit2", temp);
                temp = settings.getString("start1", "").toString();
                editor.putString("start2", temp);
                temp = settings.getString("stop1", "").toString();
                editor.putString("stop2", temp);
                temp = settings.getString("pause1", "").toString();
                editor.putString("pause2", temp);
                temp = settings.getString("rechnung1", "").toString();
                editor.putString("rechnung2", temp);
                temp = settings.getString("kunde1", "").toString();
                editor.putString("kunde2", temp);

                // Zeile 2 wird in Zeile 3 verschoben
                temp = settings.getString("datum2", "").toString();
                editor.putString("datum3", temp);
                temp = settings.getString("arbeitszeit2", "").toString();
                editor.putString("arbeitszeit3", temp);
                temp = settings.getString("start2", "").toString();
                editor.putString("start3", temp);
                temp = settings.getString("stop2", "").toString();
                editor.putString("stop3", temp);
                temp = settings.getString("pause2", "").toString();
                editor.putString("pause3", temp);
                temp = settings.getString("rechnung2", "").toString();
                editor.putString("rechnung3", temp);
                temp = settings.getString("kunde2", "").toString();
                editor.putString("kunde3", temp);

                // Zeile 3 wird in Zeile 4 verschoben
                temp = settings.getString("datum3", "").toString();
                editor.putString("datum4", temp);
                temp = settings.getString("arbeitszeit3", "").toString();
                editor.putString("arbeitszeit4", temp);
                temp = settings.getString("start3", "").toString();
                editor.putString("start4", temp);
                temp = settings.getString("stop3", "").toString();
                editor.putString("stop4", temp);
                temp = settings.getString("pause3", "").toString();
                editor.putString("pause4", temp);
                temp = settings.getString("rechnung3", "").toString();
                editor.putString("rechnung4", temp);
                temp = settings.getString("kunde3", "").toString();
                editor.putString("kunde4", temp);

                // Zeile 4 wird in Zeile 5 verschoben
                temp = settings.getString("datum4", "").toString();
                editor.putString("datum5", temp);
                temp = settings.getString("arbeitszeit4", "").toString();
                editor.putString("arbeitszeit5", temp);
                temp = settings.getString("start4", "").toString();
                editor.putString("start5", temp);
                temp = settings.getString("stop4", "").toString();
                editor.putString("stop5", temp);
                temp = settings.getString("pause4", "").toString();
                editor.putString("pause5", temp);
                temp = settings.getString("rechnung4", "").toString();
                editor.putString("rechnung5", temp);
                temp = settings.getString("kunde4", "").toString();
                editor.putString("kunde5", temp);

                // Zeile 54 wird in Zeile 6 verschoben
                temp = settings.getString("datum5", "").toString();
                editor.putString("datum6", temp);
                temp = settings.getString("arbeitszeit5", "").toString();
                editor.putString("arbeitszeit6", temp);
                temp = settings.getString("start5", "").toString();
                editor.putString("start6", temp);
                temp = settings.getString("stop5", "").toString();
                editor.putString("stop6", temp);
                temp = settings.getString("pause5", "").toString();
                editor.putString("pause6", temp);
                temp = settings.getString("rechnung5", "").toString();
                editor.putString("rechnung6", temp);
                temp = settings.getString("kunde5", "").toString();
                editor.putString("kunde6", temp);

                // Werte werden in Zeile 1 geschrieben
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                String date = df.format(Calendar.getInstance().getTime());
                editor.putString("datum1", date);
                editor.putString("arbeitszeit1", tbWorktime.getText().toString().substring(6));
                editor.putString("start1", tbStartTime1.getText().toString());
                editor.putString("stop1", tbStopTime1.getText().toString());
                editor.putString("pause1", pause);
                editor.putString("rechnung1", Double.toString(bill) + " €");
                editor.putString("kunde1", kunde);

                editor.commit();
                Toast.makeText(getApplicationContext(), "Gespeichert", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Bitte erst den Timer starten!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                Log.i(TAG, "Einstellungen wurden angeklickt");
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.report_settings:
                Log.i(TAG, "Berichte wurden angeklickt");
                startActivity(new Intent(this, ReportsActivity.class));
                return true;
            /*
            case R.id.about_settings:
                Log.i(TAG, "Über... wurde angeklickt");
                startActivity(new Intent(this, AboutActivity.class));
                return true;
                */
            case R.id.quit:
                Log.i(TAG, "Beenden wurde angeklickt");
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
