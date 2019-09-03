package com.apiTest.API;

import org.testng.annotations.Test;

import com.apiTest.FunctionLibrary.APIFunctionLibrary;
import com.apiTest.UtilityFunctions.FrameworkException;

import io.restassured.response.Response;

public class ValidateAPI extends APIFunctionLibrary {

	@Test(dataProvider = "APITestData", dataProviderClass = com.apiTest.TestData.TestDataProvider.class)
	public void verifyAPI(String runMode, String categoryID, String catalogue, String categoryName, String canRelist,
			String promotionName, String promotionDescription) throws FrameworkException {

		try {
			if (runMode.equalsIgnoreCase("Yes")) {
				Response response = getResponse(categoryID, catalogue);
				validateResponse(response, categoryID, catalogue, categoryName, canRelist, promotionName,
						promotionDescription);
			}

		} catch (FrameworkException e) {
			Report.fail(e.getMessage());
		} catch (Exception e) {
			Report.fail("API validation failed. Exception : " + e.getMessage());
			Report.fail("API validation failed. Exception :" + e.getClass());
		}

	}
}
