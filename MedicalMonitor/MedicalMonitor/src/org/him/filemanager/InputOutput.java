package org.him.filemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.him.medicalmonitor.MainActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

@SuppressLint({ "WorldReadableFiles", "NewApi" })
public class InputOutput {
	static File cfg;
	static FileWriter cfgWrite;
	public static FileReader cfgRead;
	public static Scanner cfgScan;
	Logger log = Logger.getLogger(MainActivity.class.getName());
    FileHandler f;
    static FileHandler fs;
	static Logger logs;
	int pickedHour;
	int pickedMinute;
	static Context context;
	public InputOutput() {
		
	}
		
		public static void WriteLight(String s, String file)
		{
			try 
			{
				cfg = new File(context.getFilesDir() + "/" + file);
				cfg.createNewFile();
				cfgWrite = new FileWriter(file);
				cfgWrite.write(s);
				cfgWrite.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
				
				/**Play a sound in the following line.*/
				//playWindowsError();
				
				
				
			    try 
			    {

			      //Log all OTHER errors 
			      fs = new FileHandler("err/oerdump.vpr", true);
			      logs.setLevel(Level.ALL);		      
			      logs.addHandler(fs);
			      
			      
			      SimpleFormatter sf = new SimpleFormatter();
			      fs.setFormatter(sf);

			      //Log a string when other error occurs 
				    logs.log(Level.SEVERE, "UNABLE TO READ PREFERENCE FILES");

			    }
			    catch (SecurityException e1) 
			    {
			      e1.printStackTrace();
			    }
			    catch (IOException e2) 
			    {
			      e2.printStackTrace();
			    }
			}
		
		}
		
		public void ReadToTextView(TextView counterText)
		{
			try 
			{
				//READ a1 Checkbox
				//cfgRead = new FileReader(context.getFilesDir() + "/" + "med.vpr");
				//cfgScan = new Scanner(cfgRead);
				//String pref = cfgScan.next();
				
				 FileInputStream fis = context.openFileInput("med.vpr");
				   InputStreamReader isr = new InputStreamReader(fis);
				   BufferedReader bufferedReader = new BufferedReader(isr);
				   StringBuilder sb = new StringBuilder();
				   String line;
				   while ((line = bufferedReader.readLine()) != null) {
				       sb.append(line);
				       counterText.setText(sb);
				   }
				
				//READ a2 Checkbox
				/*cfgRead = new FileReader("wh/rdt2.vpr");
				cfgScan = new Scanner(cfgRead);
				String pref2 = cfgScan.next();*/
				
				
				//READ e1 Checkbox
				/*PokerFrame.cfgRead = new FileReader("wh/rdt1.vpr");
				PokerFrame.cfgScan = new Scanner(PokerFrame.cfgRead);
				String pref1 = PokerFrame.cfgScan.next();
				*/
				//ALL GENERAL READS HERE
				//counterText.setText(pref);
				
				//ALL SETTINGS READS HERE
				
				
				
				//ALL CONNECTION READS HERE
				
				
				
				//ALL OTHER READS HERE
				/*if(pref1.contains("e1=1"))
				{
					e1.setSelected(true);
				}
				else
				{
					e1.setSelected(false);
				}*/
				
				//cfgScan.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				
				
				
			    try 
			    {

			      //Log all OTHER errors 
			      f = new FileHandler("err/oerdump.vpr", true);
			      log.setLevel(Level.ALL);		      
			      log.addHandler(f);
			      
			      
			      SimpleFormatter sf = new SimpleFormatter();
			      f.setFormatter(sf);

			      //Log a string when connection error occurs 
				    log.log(Level.SEVERE, "UNABLE TO READ PREFERENCE FILES");


			    }
			    catch (SecurityException e1) 
			    {
			      e1.printStackTrace();
			    }
			    catch (IOException e2) 
			    {
			      e2.printStackTrace();
			    }
			}
			
			
		}
		
		/**
		 * Writes valid string into a specified file in the same directory
		 * the application resources are contained in.
		 * 
		 * @param file : String - File name and path succeeding resource directory.
		 * @param data : String - String to write into file.
		 * 
		 */
		public static void Write(String file, String data) {
			File f1 = new File(Environment.getExternalStorageDirectory().toString() + "/mdm/state/");
			File f2 = new File(Environment.getExternalStorageDirectory().toString() + "/mdm/state/" + file);
		    try {
		    	f1.mkdirs();
		    	f2.createNewFile();
		    	//context.openFileInput(file);
		    	FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/mdm/state/" + file);
		    	byte[] bytedData = data.getBytes();
		    	fos.write(bytedData);
		    	fos.close();
				//FileOutputStream fOut = context.openFileOutput(file, Context.MODE_WORLD_READABLE);
		        //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fOut);
		        		//(context.openFileOutput(context.getFilesDir().getAbsolutePath() + "/" + file, Context.MODE_PRIVATE));
		        //outputStreamWriter.write(data);
		        //outputStreamWriter.close();
		    }
		    catch (IOException e) {
		        Log.e("Exception", "File write failed: " + e.toString());
		    }
		}
		
		/**
		 * Reads from a specified file in the same directory
		 * the application resources are contained in.
		 * 
		 * @param file : String - File name and path succeeding resource directory.
		 * 
		 */
		public static String Read(String file) {

		    String result = "";

		    /*try {
		        InputStream inputStream = context.openFileInput(Environment.getExternalStorageDirectory().toString() + "/mdm/state/" + file);

		        if ( inputStream != null ) {
		            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		            String getString = "";
		            StringBuilder stringBuilder = new StringBuilder();

		            while ( (getString = bufferedReader.readLine()) != null ) {
		                stringBuilder.append(getString);
		            }

		            inputStream.close();
		            result = stringBuilder.toString();
		        }
		    }
		    catch (FileNotFoundException e) {
		        Log.e("read_error", "File not found: " + e.toString());
		    } catch (IOException e) {
		        Log.e("io_error", "Can not read file: " + e.toString());
		    }
*/
		    File f = new File(Environment.getExternalStorageDirectory().toString() + "/mdm/state/" + file);
		    StringBuilder sb = new StringBuilder();
		    try {
		        BufferedReader br = new BufferedReader(new FileReader(f));
		        String line;

		        while ((line = br.readLine()) != null) {
		            sb.append(line);
		            result = sb.toString();
		        }
		        br.close();
		    }
		    catch (IOException e) {
		        e.printStackTrace();
		    }
		    return result;
		}
		
		
}
