package com.DNTShareCalculation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

	/*dateapi-getmonth-gives one lesser value*/
	public static Hashtable<Integer,Double> hDict= new Hashtable<Integer, Double>();
	public static Hashtable<Integer,Double>fennecDict=new Hashtable<Integer, Double>();
	static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	static int year;
	
	public static void computeShare(Hashtable<Integer,Double> hashtable)
	{
		Set<Integer> keySt = hashtable.keySet();
		int daysInmonth=0;
		//here key is decremented by one [3 here means 4]
		for(Integer key:keySt)
		{
			if (key == 3 || key == 5 || key == 8 || key == 10)
				daysInmonth = 30;
			
			else if(key==1)
			{
				/*which means Feb*/
				if(isLeapYear(year))
					daysInmonth=29;
				else
					daysInmonth=28;
				
			}
			
			else 
				daysInmonth = 31;

			Double percentValue= hashtable.get(key)/daysInmonth;
			hashtable.put(key, percentValue);
		}
	}
	
	public static List<DataObject> convertTabtoDAL(Hashtable<Integer, Double> hashtable)
	{
		Set<Integer> keySet=hashtable.keySet();
		List<DataObject>objList= new ArrayList<DataObject>();
		for(Integer key:keySet)
		{	
			DataObject dObj= new DataObject();
			dObj.date="year-"+"0"+(key+1)+"-01";
			dObj.percentage=hashtable.get(key);
			objList.add(dObj);
		}
		
		return objList;
		
	}
	
	public static boolean isLeapYear(int year) {
	    
	    return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
	}
	
	
	public static String getJson(List<DataObject> objList)
	{
		
		simpleObject intmdObj= new simpleObject();
		intmdObj.GLOBAL=objList;
		
		String jsonified =gson.toJson(intmdObj);
		
		return jsonified;
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		String sCurrentLine;
		SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd");
		year=Integer.parseInt(args[1]);
	
		while((sCurrentLine = br.readLine() )!=null)
		{
				
			String[] arrData= sCurrentLine.split("\\s+");
			Date dObj=sd.parse(arrData[0]);
			
		    int month= dObj.getMonth();
		  			
			if(hDict.containsKey(month))
			{
				Double perValue  =hDict.get(month);
				Double newPerValue=perValue+Double.parseDouble(arrData[1]);
				hDict.put(month, newPerValue);
				
			}
			else
			{
				hDict.put(month, Double.parseDouble(arrData[1]));
			}
			
			

			if(fennecDict.containsKey(month))
			{
				Double perValue  =fennecDict.get(month);
				Double newPerValue=perValue+Double.parseDouble(arrData[2]);
				fennecDict.put(month, newPerValue);
				
			}
			else
			{
				fennecDict.put(month, Double.parseDouble(arrData[2]));
			}
				
				
		}
		computeShare(hDict);
		computeShare(fennecDict);
		
		
		List<DataObject> desktopDAL= convertTabtoDAL(hDict);
		List<DataObject> fennecDAL=convertTabtoDAL(fennecDict);
		
		String dskJson=getJson(desktopDAL);
		String fennecJson=getJson(fennecDAL);
		
		System.out.println("DNTSHARECALCULATION");
		System.out.println("######DESKTOP JSON####### "+ dskJson);
		System.out.println("######FENNEC JSON#######"+ fennecJson);
		
	}

}
