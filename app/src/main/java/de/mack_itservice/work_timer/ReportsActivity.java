package de.mack_itservice.work_timer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Mack on 09.04.2015.
 */
public class ReportsActivity extends Activity {

    private Button saveButton;
    private Button deleteButton;

    private TextView tbDate1;
    private TextView tbWorktime1;
    private TextView tbStart1;
    private TextView tbStop1;
    private TextView tbPause1;
    private TextView tbBill1;
    private EditText tbCustomer1;

    private TextView tbDate2;
    private TextView tbWorktime2;
    private TextView tbStart2;
    private TextView tbStop2;
    private TextView tbPause2;
    private TextView tbBill2;
    private EditText tbCustomer2;

    private TextView tbDate3;
    private TextView tbWorktime3;
    private TextView tbStart3;
    private TextView tbStop3;
    private TextView tbPause3;
    private TextView tbBill3;
    private EditText tbCustomer3;

    private TextView tbDate4;
    private TextView tbWorktime4;
    private TextView tbStart4;
    private TextView tbStop4;
    private TextView tbPause4;
    private TextView tbBill4;
    private EditText tbCustomer4;

    private TextView tbDate5;
    private TextView tbWorktime5;
    private TextView tbStart5;
    private TextView tbStop5;
    private TextView tbPause5;
    private TextView tbBill5;
    private EditText tbCustomer5;

    private TextView tbDate6;
    private TextView tbWorktime6;
    private TextView tbStart6;
    private TextView tbStop6;
    private TextView tbPause6;
    private TextView tbBill6;
    private EditText tbCustomer6;

    public static final String PREFS_NAME = "MyApp_Settings";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports);

        saveButton = (Button) findViewById(R.id.saveButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        tbDate1 = (TextView) findViewById(R.id.date1);
        tbWorktime1 = (TextView) findViewById(R.id.worktime1);
        tbStart1 = (TextView) findViewById(R.id.start1);
        tbStop1 = (TextView) findViewById(R.id.stop1);
        tbPause1 = (TextView) findViewById(R.id.pause1);
        tbBill1 = (TextView) findViewById(R.id.bill1);
        tbCustomer1 = (EditText) findViewById(R.id.customer1);

        tbDate2 = (TextView) findViewById(R.id.date2);
        tbWorktime2 = (TextView) findViewById(R.id.worktime2);
        tbStart2 = (TextView) findViewById(R.id.start2);
        tbStop2 = (TextView) findViewById(R.id.stop2);
        tbPause2 = (TextView) findViewById(R.id.pause2);
        tbBill2 = (TextView) findViewById(R.id.bill2);
        tbCustomer2 = (EditText) findViewById(R.id.customer2);

        tbDate3 = (TextView) findViewById(R.id.date3);
        tbWorktime3 = (TextView) findViewById(R.id.worktime3);
        tbStart3 = (TextView) findViewById(R.id.start3);
        tbStop3 = (TextView) findViewById(R.id.stop3);
        tbPause3 = (TextView) findViewById(R.id.pause3);
        tbBill3 = (TextView) findViewById(R.id.bill3);
        tbCustomer3 = (EditText) findViewById(R.id.customer3);

        tbDate4 = (TextView) findViewById(R.id.date4);
        tbWorktime4 = (TextView) findViewById(R.id.worktime4);
        tbStart4 = (TextView) findViewById(R.id.start4);
        tbStop4 = (TextView) findViewById(R.id.stop4);
        tbPause4 = (TextView) findViewById(R.id.pause4);
        tbBill4 = (TextView) findViewById(R.id.bill4);
        tbCustomer4 = (EditText) findViewById(R.id.customer4);

        tbDate5 = (TextView) findViewById(R.id.date5);
        tbWorktime5 = (TextView) findViewById(R.id.worktime5);
        tbStart5 = (TextView) findViewById(R.id.start5);
        tbStop5 = (TextView) findViewById(R.id.stop5);
        tbPause5 = (TextView) findViewById(R.id.pause5);
        tbBill5 = (TextView) findViewById(R.id.bill5);
        tbCustomer5 = (EditText) findViewById(R.id.customer5);

        tbDate6 = (TextView) findViewById(R.id.date6);
        tbWorktime6 = (TextView) findViewById(R.id.worktime6);
        tbStart6 = (TextView) findViewById(R.id.start6);
        tbStop6 = (TextView) findViewById(R.id.stop6);
        tbPause6 = (TextView) findViewById(R.id.pause6);
        tbBill6 = (TextView) findViewById(R.id.bill6);
        tbCustomer6 = (EditText) findViewById(R.id.customer6);

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Der erste Zeile ist in der Prefs-Datei vorhanden und wird geladen
        if (settings.getString("datum1", "").toString() != "") {
            // Die gespeicherten Werte für die erste Zeile laden
            String date1 = settings.getString("datum1", "");
            tbDate1.setText(date1);
            String worktime1 = settings.getString("arbeitszeit1", "");
            tbWorktime1.setText(worktime1);
            String start1 = settings.getString("start1", "");
            tbStart1.setText(start1);
            String stop1 = settings.getString("stop1", "");
            tbStop1.setText(stop1);
            String pause1 = settings.getString("pause1", "");
            tbPause1.setText(pause1);
            String bill1 = settings.getString("rechnung1", "");
            tbBill1.setText(bill1);
            String customer1 = settings.getString("kunde1", "");
            tbCustomer1.setText(customer1);
        }

        // Die zweite Zeile ist in der Prefs-Datei vorhanden und wird geladen
        if (settings.getString("datum2", "").toString() != "") {
            // Die gespeicherten Werte für die zweite Zeile laden
            String date2 = settings.getString("datum2", "");
            tbDate2.setText(date2);
            String worktime2 = settings.getString("arbeitszeit2", "");
            tbWorktime2.setText(worktime2);
            String start2 = settings.getString("start2", "");
            tbStart2.setText(start2);
            String stop2 = settings.getString("stop2", "");
            tbStop2.setText(stop2);
            String pause2 = settings.getString("pause2", "");
            tbPause2.setText(pause2);
            String bill2 = settings.getString("rechnung2", "");
            tbBill2.setText(bill2);
            String customer2 = settings.getString("kunde2", "");
            tbCustomer2.setText(customer2);
        }

        // Die dritte Zeile ist in der Prefs-Datei vorhanden und wird geladen
        if (settings.getString("datum3", "").toString() != "") {
            // Die gespeicherten Werte für die zweite Zeile laden
            String date3 = settings.getString("datum3", "");
            tbDate3.setText(date3);
            String worktime3 = settings.getString("arbeitszeit3", "");
            tbWorktime3.setText(worktime3);
            String start3 = settings.getString("start3", "");
            tbStart3.setText(start3);
            String stop3 = settings.getString("stop3", "");
            tbStop3.setText(stop3);
            String pause3 = settings.getString("pause3", "");
            tbPause3.setText(pause3);
            String bill3 = settings.getString("rechnung3", "");
            tbBill3.setText(bill3);
            String customer3 = settings.getString("kunde3", "");
            tbCustomer3.setText(customer3);
        }

        // Die vierte Zeile ist in der Prefs-Datei vorhanden und wird geladen
        if (settings.getString("datum4", "").toString() != "") {
            // Die gespeicherten Werte für die zweite Zeile laden
            String date4 = settings.getString("datum4", "");
            tbDate4.setText(date4);
            String worktime4 = settings.getString("arbeitszeit4", "");
            tbWorktime4.setText(worktime4);
            String start4 = settings.getString("start4", "");
            tbStart4.setText(start4);
            String stop4 = settings.getString("stop4", "");
            tbStop4.setText(stop4);
            String pause4 = settings.getString("pause4", "");
            tbPause4.setText(pause4);
            String bill4 = settings.getString("rechnung4", "");
            tbBill4.setText(bill4);
            String customer4 = settings.getString("kunde4", "");
            tbCustomer4.setText(customer4);
        }

        // Die fünfte Zeile ist in der Prefs-Datei vorhanden und wird geladen
        if (settings.getString("datum5", "").toString() != "") {
            // Die gespeicherten Werte für die zweite Zeile laden
            String date5 = settings.getString("datum5", "");
            tbDate5.setText(date5);
            String worktime5 = settings.getString("arbeitszeit5", "");
            tbWorktime5.setText(worktime5);
            String start5 = settings.getString("start5", "");
            tbStart5.setText(start5);
            String stop5 = settings.getString("stop5", "");
            tbStop5.setText(stop5);
            String pause5 = settings.getString("pause5", "");
            tbPause5.setText(pause5);
            String bill5 = settings.getString("rechnung5", "");
            tbBill5.setText(bill5);
            String customer5 = settings.getString("kunde5", "");
            tbCustomer5.setText(customer5);
        }

        // Die sechste Zeile ist in der Prefs-Datei vorhanden und wird geladen
        if (settings.getString("datum6", "").toString() != "") {
            // Die gespeicherten Werte für die zweite Zeile laden
            String date6 = settings.getString("datum6", "");
            tbDate6.setText(date6);
            String worktime6 = settings.getString("arbeitszeit6", "");
            tbWorktime6.setText(worktime6);
            String start6 = settings.getString("start6", "");
            tbStart6.setText(start6);
            String stop6 = settings.getString("stop6", "");
            tbStop6.setText(stop6);
            String pause6 = settings.getString("pause6", "");
            tbPause6.setText(pause6);
            String bill6 = settings.getString("rechnung6", "");
            tbBill6.setText(bill6);
            String customer6 = settings.getString("kunde6", "");
            tbCustomer6.setText(customer6);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                SharedPreferences.Editor editor = settings.edit();

                // Werte werden geschrieben
                editor.putString("datum1", tbDate1.getText().toString());
                editor.putString("arbeitszeit1", tbWorktime1.getText().toString());
                editor.putString("start1", tbStart1.getText().toString());
                editor.putString("stop1", tbStop1.getText().toString());
                editor.putString("pause1", tbPause1.getText().toString());
                editor.putString("rechnung1", tbBill1.getText().toString());
                editor.putString("kunde1", tbCustomer1.getText().toString());

                editor.putString("datum2", tbDate2.getText().toString());
                editor.putString("arbeitszeit2", tbWorktime2.getText().toString());
                editor.putString("start2", tbStart2.getText().toString());
                editor.putString("stop2", tbStop2.getText().toString());
                editor.putString("pause2", tbPause2.getText().toString());
                editor.putString("rechnung2", tbBill2.getText().toString());
                editor.putString("kunde2", tbCustomer2.getText().toString());

                editor.putString("datum3", tbDate3.getText().toString());
                editor.putString("arbeitszeit3", tbWorktime3.getText().toString());
                editor.putString("start3", tbStart3.getText().toString());
                editor.putString("stop3", tbStop3.getText().toString());
                editor.putString("pause3", tbPause3.getText().toString());
                editor.putString("rechnung3", tbBill3.getText().toString());
                editor.putString("kunde3", tbCustomer3.getText().toString());

                editor.putString("datum4", tbDate4.getText().toString());
                editor.putString("arbeitszeit4", tbWorktime4.getText().toString());
                editor.putString("start4", tbStart4.getText().toString());
                editor.putString("stop4", tbStop4.getText().toString());
                editor.putString("pause4", tbPause4.getText().toString());
                editor.putString("rechnung4", tbBill4.getText().toString());
                editor.putString("kunde4", tbCustomer4.getText().toString());

                editor.putString("datum5", tbDate5.getText().toString());
                editor.putString("arbeitszeit5", tbWorktime5.getText().toString());
                editor.putString("start5", tbStart5.getText().toString());
                editor.putString("stop5", tbStop5.getText().toString());
                editor.putString("pause5", tbPause5.getText().toString());
                editor.putString("rechnung5", tbBill5.getText().toString());
                editor.putString("kunde5", tbCustomer5.getText().toString());

                editor.putString("datum6", tbDate6.getText().toString());
                editor.putString("arbeitszeit6", tbWorktime6.getText().toString());
                editor.putString("start6", tbStart6.getText().toString());
                editor.putString("stop6", tbStop6.getText().toString());
                editor.putString("pause6", tbPause6.getText().toString());
                editor.putString("rechnung6", tbBill6.getText().toString());
                editor.putString("kunde6", tbCustomer6.getText().toString());

                editor.commit();
                Toast.makeText(getApplicationContext(), "Gespeichert", Toast.LENGTH_SHORT).show();
            }
        });

        // Button zum Löschen aller Einträge
        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                DeleteReportEntries();
            }
        });
    }

    // Funktion zum Leeren der Reports
    protected void DeleteReportEntries() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Alle Einträge löschen")
                .setMessage("Sicher?")
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        tbDate1.setText("");
                        tbWorktime1.setText("");
                        tbStart1.setText("");
                        tbStop1.setText("");
                        tbPause1.setText("");
                        tbBill1.setText("");
                        tbCustomer1.setText("");

                        tbDate2.setText("");
                        tbWorktime2.setText("");
                        tbStart2.setText("");
                        tbStop2.setText("");
                        tbPause2.setText("");
                        tbBill2.setText("");
                        tbCustomer2.setText("");

                        tbDate3.setText("");
                        tbWorktime3.setText("");
                        tbStart3.setText("");
                        tbStop3.setText("");
                        tbPause3.setText("");
                        tbBill3.setText("");
                        tbCustomer3.setText("");

                        tbDate4.setText("");
                        tbWorktime4.setText("");
                        tbStart4.setText("");
                        tbStop4.setText("");
                        tbPause4.setText("");
                        tbBill4.setText("");
                        tbCustomer4.setText("");

                        tbDate5.setText("");
                        tbWorktime5.setText("");
                        tbStart5.setText("");
                        tbStop5.setText("");
                        tbPause5.setText("");
                        tbBill5.setText("");
                        tbCustomer5.setText("");

                        tbDate6.setText("");
                        tbWorktime6.setText("");
                        tbStart6.setText("");
                        tbStop6.setText("");
                        tbPause6.setText("");
                        tbBill6.setText("");
                        tbCustomer6.setText("");

                        Toast.makeText(getApplicationContext(), "Alle Einträge gelöscht", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Nein", null) // Nichts machen
                .show();
    }

    float dX, dY;

    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:

                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:

                view.animate()
                        .x(event.getRawX() + dX)
                        .y(event.getRawY() + dY)
                        .setDuration(0)
                        .start();
                break;
            default:
                return false;
        }
        return true;
    }
}


