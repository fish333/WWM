package com.example.wheresmymoney;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.content.ClipData.Item;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddExpense extends Activity {

	double cost=0;
	int chosenType=0;
	int chosenSubType=-1;
	DBAdapter myDB;
	ImageView iv1,iv2,iv3,iv4,iv5,ivv1,ivv2,ivv3,ivv4,ivv5;
	TextView tvChosenType, tvCost;
	Calendar c = Calendar.getInstance(); 

	
	String[] imageNAMEs={ 
		"Food",
		"Transportation",
		"Bills",
		"Materialistic",
		"Fun",
		
		"Groceries","Meal","Snack","Drink","Other",  //FOOD
		"Gas","Taxi","Bus","Train","Other", //TRANSPORT
		"Electric bill","Housing bill","Phone bill","Debts","Other bills", //BILLS
		"Multimedia","Clothes","Sport equipment","Practical stuff","Other stuff",//MATERIALISTIC
		"Activity","Travel","Event","Services","Other fun stuff"//FUN
		
	};
	int[] imageIDs={
		R.drawable.type_a,
		R.drawable.type_b,
		R.drawable.type_c,
		R.drawable.type_d,
		R.drawable.type_e,
		
		R.drawable.type_aa,
		R.drawable.type_ab,
		R.drawable.type_ac,
		R.drawable.type_ad,
		R.drawable.type_ae,
		
		R.drawable.type_ba,
		R.drawable.type_bb,
		R.drawable.type_bc,
		R.drawable.type_bd,
		R.drawable.type_be,
		
		R.drawable.type_ca,
		R.drawable.type_cb,
		R.drawable.type_cc,
		R.drawable.type_cd,
		R.drawable.type_ce,
		
		R.drawable.type_da,
		R.drawable.type_db,
		R.drawable.type_dc,
		R.drawable.type_dd,
		R.drawable.type_de,
		
		R.drawable.type_ea,
		R.drawable.type_eb,
		R.drawable.type_ec,
		R.drawable.type_ed,
		R.drawable.type_ee,
		
	};
	int nextImageIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addexpense);
		iv1 = (ImageView) findViewById(R.id.image1); 
		iv2 = (ImageView) findViewById(R.id.image2);
		iv3 = (ImageView) findViewById(R.id.image3);
		iv4 = (ImageView) findViewById(R.id.image4);
		iv5 = (ImageView) findViewById(R.id.image5);
		
		ivv1 = (ImageView) findViewById(R.id.imagex1);
		ivv2 = (ImageView) findViewById(R.id.imagex2);
		ivv3 = (ImageView) findViewById(R.id.imagex3);
		ivv4 = (ImageView) findViewById(R.id.imagex4);
		ivv5 = (ImageView) findViewById(R.id.imagex5);
		
		tvChosenType = (TextView) findViewById(R.id.tv_chosenType);
		tvCost = (TextView) findViewById(R.id.tv_cost);
		iv1.performClick();
		
		
		openDB();
		//populateListViewFromDB();
		//registerListClick();
	}
	private void openDB(){
		myDB = new DBAdapter(this);
		myDB.open();
	}
	private void closeDB(){
		myDB.close();
	}
	protected void onDestroy(){
		super.onDestroy();
		closeDB();
	}
	public void btn_clear_Click(View v){
		myDB.deleteAll();	
		Toast.makeText(AddExpense.this, "Expenses deleted", Toast.LENGTH_SHORT).show();
		//populateListViewFromDB();
	}
	public void btn_addExpense_Click(View v) throws IOException{

		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("dd-MMM yyyy");
		String date = df.format(c.getTime());

		if(chosenSubType!=-1){
			int imageID = imageIDs[chosenSubType];
			nextImageIndex = (nextImageIndex)%imageIDs.length; //vsakiè da nasledno sliko

			try{			
				//Add expense
				myDB.insertRow(date, imageID, tvCost.getText().toString(), imageNAMEs[chosenSubType]);
				
				//Get new value			
				String curretnBalance = myDB.getMoney();
				Double moneyToSpend = Double.parseDouble(tvCost.getText().toString());
				Double newBalance = Double.parseDouble(curretnBalance)-moneyToSpend;
				//myDB.insertRow();   prvo vstavimo za dnar
				
				//Save new value to DB
				myDB.updateMoneyRow(newBalance+"");
				Toast.makeText(AddExpense.this,"You spent "+moneyToSpend+"€", Toast.LENGTH_SHORT).show();
			}
			catch(Exception ex){
				Toast.makeText(AddExpense.this,"Error: "+ex.getMessage(), Toast.LENGTH_LONG).show();	
			}

		}
		else{
			Toast.makeText(AddExpense.this, "Pick a type!", Toast.LENGTH_SHORT).show();
		}
	}
	

	public void image1Click(View v){
		iv1.setBackgroundResource(R.drawable.greenspikeball);
		iv2.setBackgroundResource(R.color.BelaOdzadje);
		iv3.setBackgroundResource(R.color.BelaOdzadje);
		iv4.setBackgroundResource(R.color.BelaOdzadje);
		iv5.setBackgroundResource(R.color.BelaOdzadje);
		ivv1.setImageResource(imageIDs[5]);
		ivv2.setImageResource(imageIDs[6]);
		ivv3.setImageResource(imageIDs[7]);
		ivv4.setImageResource(imageIDs[8]);
		ivv5.setImageResource(imageIDs[9]);
		tvChosenType.setText(imageNAMEs[0]);
		chosenType = 4;
		chosenSubType=-1;
		iv1.setPadding(1, 1, 1, 1);

		
	}
	public void image2Click(View v){
		iv1.setBackgroundResource(R.color.BelaOdzadje);
		iv2.setBackgroundResource(R.drawable.greenspikeball);
		iv3.setBackgroundResource(R.color.BelaOdzadje);
		iv4.setBackgroundResource(R.color.BelaOdzadje);
		iv5.setBackgroundResource(R.color.BelaOdzadje);
		ivv1.setImageResource(imageIDs[10]);
		ivv2.setImageResource(imageIDs[11]);
		ivv3.setImageResource(imageIDs[12]);
		ivv4.setImageResource(imageIDs[13]);
		ivv5.setImageResource(imageIDs[14]);
		tvChosenType.setText(imageNAMEs[1]);
		chosenType=9;
		chosenSubType=-1;
	}
	public void image3Click(View v){
		iv1.setBackgroundResource(R.color.BelaOdzadje);
		iv2.setBackgroundResource(R.color.BelaOdzadje);
		iv3.setBackgroundResource(R.drawable.greenspikeball);
		iv4.setBackgroundResource(R.color.BelaOdzadje);
		iv5.setBackgroundResource(R.color.BelaOdzadje);
		ivv1.setImageResource(imageIDs[15]);
		ivv2.setImageResource(imageIDs[16]);
		ivv3.setImageResource(imageIDs[17]);
		ivv4.setImageResource(imageIDs[18]);
		ivv5.setImageResource(imageIDs[19]);
		tvChosenType.setText(imageNAMEs[2]);
		chosenType=14;
		chosenSubType=-1;
	}
	public void image4Click(View v){
		iv1.setBackgroundResource(R.color.BelaOdzadje);
		iv2.setBackgroundResource(R.color.BelaOdzadje);
		iv3.setBackgroundResource(R.color.BelaOdzadje);
		iv4.setBackgroundResource(R.drawable.greenspikeball);
		iv5.setBackgroundResource(R.color.BelaOdzadje);
		ivv1.setImageResource(imageIDs[20]);
		ivv2.setImageResource(imageIDs[21]);
		ivv3.setImageResource(imageIDs[22]);
		ivv4.setImageResource(imageIDs[23]);
		ivv5.setImageResource(imageIDs[24]);
		tvChosenType.setText(imageNAMEs[3]);
		chosenType=19;
		chosenSubType=-1;
	}
	public void image5Click(View v){
		iv1.setBackgroundResource(R.color.BelaOdzadje);
		iv2.setBackgroundResource(R.color.BelaOdzadje);
		iv3.setBackgroundResource(R.color.BelaOdzadje);
		iv4.setBackgroundResource(R.color.BelaOdzadje);
		iv5.setBackgroundResource(R.drawable.greenspikeball);
		ivv1.setImageResource(imageIDs[25]);
		ivv2.setImageResource(imageIDs[26]);
		ivv3.setImageResource(imageIDs[27]);
		ivv4.setImageResource(imageIDs[28]);
		ivv5.setImageResource(imageIDs[29]);
		tvChosenType.setText(imageNAMEs[4]);
		chosenType=24;
		chosenSubType=-1;
	}
	public void imagesub1Click(View v){
		chosenSubType=chosenType+1;
		tvChosenType.setText(imageNAMEs[chosenSubType]);
		ivv1.setBackgroundResource(R.drawable.greenspikeball);
		ivv2.setBackgroundResource(R.color.BelaOdzadje);
		ivv3.setBackgroundResource(R.color.BelaOdzadje);
		ivv4.setBackgroundResource(R.color.BelaOdzadje);
		ivv5.setBackgroundResource(R.color.BelaOdzadje);
	}
	public void imagesub2Click(View v){
		
		chosenSubType=chosenType+2;
		tvChosenType.setText(imageNAMEs[chosenSubType]);
		ivv1.setBackgroundResource(R.color.BelaOdzadje);
		ivv2.setBackgroundResource(R.drawable.greenspikeball);
		ivv3.setBackgroundResource(R.color.BelaOdzadje);
		ivv4.setBackgroundResource(R.color.BelaOdzadje);
		ivv5.setBackgroundResource(R.color.BelaOdzadje);
	}
	public void imagesub3Click(View v){
		chosenSubType=chosenType+3;
		tvChosenType.setText(imageNAMEs[chosenSubType]);
		ivv1.setBackgroundResource(R.color.BelaOdzadje);
		ivv2.setBackgroundResource(R.color.BelaOdzadje);
		ivv3.setBackgroundResource(R.drawable.greenspikeball);
		ivv4.setBackgroundResource(R.color.BelaOdzadje);
		ivv5.setBackgroundResource(R.color.BelaOdzadje);
	}
	public void imagesub4Click(View v){
		chosenSubType=chosenType+4;
		tvChosenType.setText(imageNAMEs[chosenSubType]);
		ivv1.setBackgroundResource(R.color.BelaOdzadje);
		ivv2.setBackgroundResource(R.color.BelaOdzadje);
		ivv3.setBackgroundResource(R.color.BelaOdzadje);
		ivv4.setBackgroundResource(R.drawable.greenspikeball);
		ivv5.setBackgroundResource(R.color.BelaOdzadje);
	}
	public void imagesub5Click(View v){
		chosenSubType=chosenType+5;
		tvChosenType.setText(imageNAMEs[chosenSubType]);
		ivv1.setBackgroundResource(R.color.BelaOdzadje);
		ivv2.setBackgroundResource(R.color.BelaOdzadje);
		ivv3.setBackgroundResource(R.color.BelaOdzadje);
		ivv4.setBackgroundResource(R.color.BelaOdzadje);
		ivv5.setBackgroundResource(R.drawable.greenspikeball);
	}
	public void btn_plus1_Click(View v){
		cost = Double.parseDouble(tvCost.getText().toString());
		tvCost.setText(""+(cost+1));
		
	}
	public void btn_plus2_Click(View v){
		cost = Double.parseDouble(tvCost.getText().toString());
		tvCost.setText(""+(cost+2));
	}
	public void btn_plus10_Click(View v){
		cost = Double.parseDouble(tvCost.getText().toString());
		tvCost.setText(""+(cost+10));
	}
	public void btn_plus100_Click(View v){
		cost = Double.parseDouble(tvCost.getText().toString());
		tvCost.setText(""+(cost+100));
	}
	
	public void btn_Clear_Click(View v){
		tvCost.setText("0");
	}

}