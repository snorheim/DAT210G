package logic;

import communication.JsonClient;

public class testClass {

	public static void main(String[] args) {

		JsonClient json = new JsonClient(new RequestClient("getImagesWithTag", 1, "sommer"));
		if (json.sendJsonToServer()){
			ResponseClient response1 = json.receiveJsonFromServer();
			System.out.println(response1.toString());
			json.closeHttpConnection();
		}

	}

}
