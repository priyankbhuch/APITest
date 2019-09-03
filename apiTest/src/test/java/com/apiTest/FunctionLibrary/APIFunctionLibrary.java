package com.apiTest.FunctionLibrary;

import static io.restassured.RestAssured.given;

import java.util.List;

import com.apiTest.APIClass.CategoryURL;
import com.apiTest.APIClass.Promotions;
import com.apiTest.BaseClass.BaseClass;
import com.apiTest.UtilityFunctions.FrameworkException;
import com.apiTest.UtilityFunctions.Util;

import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;

public class APIFunctionLibrary extends BaseClass {

	/**
	 * This method will get the Response against a URL
	 * 
	 * @author Priyank
	 * @param categoriesValue
	 * @param catalogueValue
	 * @return Response
	 * @throws FrameworkException
	 */
	public Response getResponse(String categoriesValue, String catalogueValue) throws FrameworkException {
		RestAssured.baseURI = Util.getProperty("BaseURL");
		Response response = given().param("catalogue", catalogueValue).when()
				.get("/v1/Categories/" + categoriesValue + "/Details.json").then().assertThat().statusCode(200)
				.extract().response();
		Report.info("Response received : " + response.asString());
		return response;
	}

	/**
	 * This method will validate the Category Name, canRelist and Promotion with its
	 * Description from the Response received
	 * 
	 * @author Priyank
	 * @param response
	 * @param categoryID
	 * @param catalogue
	 * @param categoryName
	 * @param canRelist
	 * @param promotionName
	 * @param promotionDescription
	 */
	public void validateResponse(Response response, String categoryID, String catalogue, String categoryName,
			String canRelist, String promotionName, String promotionDescription) {
		try {
			CategoryURL categoryURLObj = response.as(CategoryURL.class, ObjectMapperType.GSON);
			validateCategoryName(response, categoryURLObj, categoryName);
			validateCanRelist(response, categoryURLObj, canRelist);
			validatePromotionDescription(response, categoryURLObj, promotionName, promotionDescription);

		} catch (Exception e) {
			Report.fail("API Response validation failed. Exception : " + e.getMessage());
		}
	}

	/**
	 * This method will validate specific Category Name in the Response
	 * 
	 * @author Priyank
	 * @param response
	 * @param categoryURLObj
	 * @param expectedCategoryName
	 */
	public void validateCategoryName(Response response, CategoryURL categoryURLObj, String expectedCategoryName) {
		try {
			String categoryNameFromResponse = categoryURLObj.getName();
			boolean valueMatchFlag = false;
			valueMatchFlag = categoryNameFromResponse.equals(expectedCategoryName);
			if (valueMatchFlag) {
				Report.pass("Category Name : " + categoryNameFromResponse + " is present in the response as expected.");
			} else {
				throw new FrameworkException(
						"Category Name : " + categoryNameFromResponse + " is not present in the response.");
			}
		} catch (Exception e) {
			Report.fail("API Response validation for Category Name failed. Exception : " + e.getMessage());
		}
	}

	/**
	 * This method will validate expected CanRelist in the Response
	 * 
	 * @author Priyank
	 * @param response
	 * @param categoryURLObj
	 * @param expectedCanRelist
	 */
	public void validateCanRelist(Response response, CategoryURL categoryURLObj, String expectedCanRelist) {
		try {
			String canRelistFromResponse = categoryURLObj.getCanRelist();
			boolean valueMatchFlag = false;
			valueMatchFlag = canRelistFromResponse.equals(expectedCanRelist);
			if (valueMatchFlag) {
				Report.pass("CanRelist value : " + canRelistFromResponse + " is present in the response as expected.");
			} else {
				throw new FrameworkException(
						"CanRelist value : " + canRelistFromResponse + " is not present in the response.");
			}
		} catch (Exception e) {
			Report.fail("API Response validation for CanRelist failed. Exception : " + e.getMessage());
		}
	}

	/**
	 * This method will validate the Description of a specific Promotion Name
	 * 
	 * @author Priyank
	 * @param response
	 * @param categoryURLObj
	 * @param expectedPromotionName
	 * @param expectedPromotionDescription
	 */
	public void validatePromotionDescription(Response response, CategoryURL categoryURLObj,
			String expectedPromotionName, String expectedPromotionDescription) {
		try {
			List<Promotions> promotions = categoryURLObj.getPromotions();
			for (Promotions pro : promotions) {
				if (pro.getName().equals(expectedPromotionName)) {
					boolean valueContainsFlag = false;
					valueContainsFlag = pro.getDescription().contains(expectedPromotionDescription);
					if (valueContainsFlag) {
						Report.pass("The Promotion '" + expectedPromotionName + "' contains the description : "
								+ expectedPromotionDescription);
					} else {
						Report.fail("The Promotion '" + expectedPromotionName + "' do not contains the description : "
								+ expectedPromotionDescription);
					}
				}
			}
		} catch (Exception e) {
			Report.fail(
					"API Response validation for Promotion and its Description failed. Exception : " + e.getMessage());
		}
	}

}
