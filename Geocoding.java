/* 
 * The code is written in Java programming language to 
 * grab coordinates for a given address using Google's Geocoding API.
 * 
 * By - Poorva Jambhale
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Geocoding {
	
	//API Key
	public static final String KEY = "AIzaSyDJl75vbgxJd1VsQy_MRCdAVFaTZSxIFv8";
	
	public void getCoOrdinates(String str) throws IOException, JSONException {
		//Build the url to invoke the API.
		String s = str.replaceAll(" ", "+");
		String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + s +  "&key=" + KEY;
		String response = httpGet(url); 
		
		//Parse the JSON object.
		JSONObject jsonObj = new JSONObject(response);
		String status = jsonObj.getString("status");
				
		if(status.equals("OK")){
			System.out.print(status);
			System.out.println (" - The address was successfully parsed.");
			JSONArray resultsArray = jsonObj.getJSONArray("results");
			JSONObject formattedAddressObj = resultsArray.getJSONObject(0);
			JSONObject geometryObj = formattedAddressObj.getJSONObject("geometry");
			JSONObject locationObj = geometryObj.getJSONObject("location");
			
			Object latitudeObj = locationObj.get("lat");
			System.out.println("Latitude: " + latitudeObj.toString());
			
			Object longitudeObj = locationObj.get("lng");
			System.out.println("Longitude: " + longitudeObj.toString());
			
		} else {
			System.out.println(status);
		}
	}
	
	 public String httpGet(String urlStr) throws IOException {
		 
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//Check for HTTP OK
			if (conn.getResponseCode() != 200) {
			    throw new IOException(conn.getResponseMessage());
			}

			// Buffer the result into a string
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			return sb.toString();
		}
	
	
	public static void main(String[] args) throws IOException, JSONException {
		
		String str = "1600 Amphitheatre Pkwy, Mountain View, CA 94043";
		System.out.println();
		System.out.println("Retrieving co-ordinates for the address: " + str);
		System.out.println();

		Geocoding gc = new Geocoding();
		gc.getCoOrdinates(str);

	}
}
