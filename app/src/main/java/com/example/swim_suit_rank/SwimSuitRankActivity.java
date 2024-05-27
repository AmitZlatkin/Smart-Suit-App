
package com.example.swim_suit_rank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.jar.JarException;

public class SwimSuitRankActivity extends AppCompatActivity {

    ListView suitNamesListView;
    ArrayList<SwimSuitInfo> swimSuitInfoList;
    ArrayList<String> suitNamesList;
    String jsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swim_suit_rank);

        swimSuitInfoList = new ArrayList<>();
        suitNamesList = new ArrayList<>();
        suitNamesListView = findViewById(R.id.suitNamesListView);
        jsonString = "";

        createSuitListAndFillListView();

        suitNamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SwimSuitInfo selectedSwimSuitInfo = swimSuitInfoList.get(position);
                showSwimSuitInfoDialog(selectedSwimSuitInfo);
            }
        });
    }



    public void createSuitListAndFillListView() {

        read_json();
        createSuitInfoListFromJsonString();
        createSuitNameListFromSuitInfoList();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(SwimSuitRankActivity.this, android.R.layout.simple_list_item_1, suitNamesList);
        suitNamesListView.setAdapter(adapter);
    }
    private void read_json() {
        try {
            InputStream is = getResources().openRawResource(R.raw.swim_suits_info);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            // load from .json file into string
            StringBuilder stringBuilder = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = br.readLine();
            }
            jsonString = stringBuilder.toString();

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createSuitInfoListFromJsonString() {
        if (jsonString == null || jsonString.length() == 0) return;

        // algorithm
        jsonString = jsonString.replace("    ", "").replace("\"", "");
        if (jsonString.length() == 0) return;

        int i = 0;
        int opening_bracket_index;
        int closing_bracket_index;

        while (i < jsonString.length()) {
            if(jsonString.charAt(i) == '{')
            {
                opening_bracket_index = i;
                int count = 1;
                while (count > 0) {
                    i++;
                    if(jsonString.charAt(i) == '{') count++;
                    else if(jsonString.charAt(i) == '}') count--;
                }
                closing_bracket_index = i;

                // everything between opening_..._index and closing_..._index is the data we are looking for
                swimSuitInfoList.add(parseSwimSuitInfoFromJsonString(jsonString.substring(opening_bracket_index+1, closing_bracket_index)));
            }
            i++;
        }
    }
    private SwimSuitInfo parseSwimSuitInfoFromJsonString(String jsonStr) {
        SwimSuitInfo swimSuitInfo = new SwimSuitInfo();

        StringBuilder stringBuilder = new StringBuilder();

        int index = "suitName: ".length();

        while (jsonStr.charAt(index) != ','){
            stringBuilder.append(jsonStr.charAt(index));
            index++;
        }
        swimSuitInfo.set(SwimSuitInfo.DATA.NAME, stringBuilder.toString());
        stringBuilder.setLength(0);

        index += ",suitSpecs: {Picture Source: ".length();

        while (jsonStr.charAt(index) != ','){
            stringBuilder.append(jsonStr.charAt(index));
            index++;
        }
        swimSuitInfo.set(SwimSuitInfo.DATA.PICTURE_SRC, stringBuilder.toString());
        stringBuilder.setLength(0);

        index += ",Compression: ".length();

        while (jsonStr.charAt(index) != ','){
            stringBuilder.append(jsonStr.charAt(index));
            index++;
        }
        swimSuitInfo.set(SwimSuitInfo.DATA.COMPRESSION, stringBuilder.toString());
        stringBuilder.setLength(0);

        index += ",Price Range: ".length();

        while (jsonStr.charAt(index) != ','){
            stringBuilder.append(jsonStr.charAt(index));
            index++;
        }
        swimSuitInfo.set(SwimSuitInfo.DATA.PRICE_RANGE, stringBuilder.toString());
        stringBuilder.setLength(0);

        index += ",Strokes: ".length();

        while (jsonStr.charAt(index) != ','){
            stringBuilder.append(jsonStr.charAt(index));
            index++;
        }
        swimSuitInfo.set(SwimSuitInfo.DATA.STROKES, stringBuilder.toString());
        stringBuilder.setLength(0);

        index += ",Distances: ".length();

        while (jsonStr.charAt(index) != '}'){
            stringBuilder.append(jsonStr.charAt(index));
            index++;
        }
        swimSuitInfo.set(SwimSuitInfo.DATA.DISTANCES, stringBuilder.toString());

        return swimSuitInfo;
    }
    private void createSuitNameListFromSuitInfoList() {
        for(SwimSuitInfo info : swimSuitInfoList){
            suitNamesList.add(info.get(SwimSuitInfo.DATA.NAME));
        }
    }

    @SuppressLint("DiscouragedApi")
    public void showSwimSuitInfoDialog(SwimSuitInfo suitInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_swim_suit_info_dialog, null);

        TextView suitNameTextView = dialogView.findViewById(R.id.suitNameTextView);
        TextView suitCompressionTextView = dialogView.findViewById(R.id.suitCompressionTextView);
        TextView suitPriceRangeTextView = dialogView.findViewById(R.id.suitPriceRangeTextView);
        TextView suitStrokesTextView = dialogView.findViewById(R.id.suitStrokesTextView);
        TextView suitDistancesTextView = dialogView.findViewById(R.id.suitDistancesTextView);

        displayData(suitNameTextView, "", suitInfo.get(SwimSuitInfo.DATA.NAME));
        displayData(suitCompressionTextView, "Compression: ", suitInfo.get(SwimSuitInfo.DATA.COMPRESSION));
        displayData(suitPriceRangeTextView, "Price: ", suitInfo.get(SwimSuitInfo.DATA.PRICE_RANGE));
        displayData(suitStrokesTextView, "Strokes: ", suitInfo.get(SwimSuitInfo.DATA.STROKES));
        displayData(suitDistancesTextView, "Distances: ", suitInfo.get(SwimSuitInfo.DATA.DISTANCES));

        ImageView suitPictureImageView = dialogView.findViewById(R.id.suitPictureImageView);
        String picture_src = suitInfo.get(SwimSuitInfo.DATA.PICTURE_SRC);
        suitPictureImageView.setImageResource(this.getResources().getIdentifier(picture_src, "drawable", this.getPackageName()));

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void displayData (TextView textView, String message, String data) {
        if (textView != null) {
            textView.setText(new StringBuilder().append(message).append(data).toString());
        }
    }
}