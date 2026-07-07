package com.OrangeHRM.utilities;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.DataProvider;

public class DataProviders {



private static final String FILE_PATH = System.getProperty("user.dir")
+ "\\src\\test\\resources\\testdata\\TestData.xlsx";

@DataProvider(name="validLoginData")
public static Object[][] validLoginData()//to be called from test methods 
{
	return getSheetData("validLoginData");//sheetName
}

@DataProvider(name="invalidLoginData")
public static Object[][] invalidLoginData()throws IOException
{
	return getSheetData("invalidLoginData");
}
@DataProvider(name="addEmployeeData")
public static Object[][] addEmployeeData()throws IOException
{
	return getSheetData("addEmployeeData");
}

@DataProvider(name = "claimData")
public static Object[][] claimData() {
    return getSheetData("claimData");
}

/*private static Object[][] getSheetData(String sheetName) {
    List<String[]> sheetData = ExcelReaderUtility.getSheetData(FILE_PATH, sheetName);
   
    // Skip header row (index 0) and filter out empty rows
    List<String[]> dataRows = sheetData.stream()
        .skip(1)                          // skip header
        .filter(row -> row.length > 0 && !row[0].isEmpty())  // skip empty rows
        .collect(java.util.stream.Collectors.toList());
    
    Object[][] data = new Object[dataRows.size()][dataRows.get(0).length];
    for (int i = 0; i < dataRows.size(); i++) {
        data[i] = dataRows.get(i);
    }
    return data;
}*/


private static Object[][]getSheetData(String sheetName) 
{
	List<String[]> sheetData=ExcelReaderUtility.getSheetData(FILE_PATH, sheetName);

Object[][] data=new Object[sheetData.size()][sheetData.get(0).length];

for(int i=0; i<sheetData.size(); i++) 
{
	data[i]=sheetData.get(i);
}

return data;


}
}
