package com.OrangeHRM.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{

private int retryCount=0;//Number of retries
private static final int maxRetryCount=0; //maximum no of retries
	

    @Override
    public boolean retry(ITestResult result) {

        if (retryCount < maxRetryCount) {

            retryCount++;
            return true;
        }

        return false;
    }
}

