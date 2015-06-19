package com.ns.spring;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ns.spring.dao.RmaHdrRepository;
import com.ns.spring.model.RMA_HDR;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Controller
public class MVCController {

	private String url = "http://localhost:8080/NS2015V07/ns-home/json";

	@Autowired
	private RmaHdrRepository rmaRep;

	@RequestMapping(value = "/welcome_01")
	public ModelAndView init01(ModelMap model) {

		ModelAndView modelAndView = new ModelAndView("welcome_01");

		String strToBePased = "<br><div align='center'>"
				+ "<h3>init01/welcome_01</h3><br><br>";
		modelAndView.addObject("message", strToBePased);

		List<RMA_HDR> rmaList = rmaRep.findAll();
		modelAndView.addObject("rmaList", rmaList);

		// mast be match with jsp name to be displayed
		return modelAndView;
	}

	@RequestMapping(value = "/welcome_02")
	public ModelAndView init02() {

		String strToBePased = "<br><div align='center'>"
				+ "<h3>init02/welcome_02</h3><br><br>";

		// mast be match with jsp name to be displayed
		return new ModelAndView("welcome_02", "message", strToBePased);
	}

	@RequestMapping(value = "/welcome_01", params = "webService", method = RequestMethod.POST)
	public ModelAndView refreshByWebService() {

		ModelAndView modelAndView = new ModelAndView("welcome_01");

		String strToBePased = "<br><div align='center'>"
				+ "<h3>refreshByWebService/welcome_01</h3><br><br>";
		modelAndView.addObject("message", strToBePased);

		List<JSONObject> jsonList = getRmaHdrList();
		saveRma(jsonList);

		List<RMA_HDR> rmaList = rmaRep.findByRma_exclude("auth");
		modelAndView.addObject("rmaList", rmaList);

		return modelAndView;
	}

	@RequestMapping("/authorize/{rmaNum}")
	public ModelAndView authorizeRma(@PathVariable("rmaNum") String rmaNum) {

		ModelAndView modelAndView = new ModelAndView("welcome_01");

		String strToBePased = "<br><div align='center'>"
				+ "<h3>authorizeRma/welcome_01</h3><br><br>";
		modelAndView.addObject("message", strToBePased);

		saveByRmaNum(rmaNum, "auth");

		List<RMA_HDR> rmaList = rmaRep.findAll();
		modelAndView.addObject("rmaList", rmaList);

		return modelAndView;
	}

	private void saveRma(List<JSONObject> jsonList) {

		for (int i = 0; i < jsonList.size(); i++) {
			JSONObject rmaJson = jsonList.get(i);
//			Long id = (Long) rmaJson.get("id");
			String rmaNum = (String) rmaJson.get("rmaNum");
			String stsCd = (String) rmaJson.get("rmaHdrStsCd");

			deleteByRmaNum(rmaNum);

			RMA_HDR rma = new RMA_HDR();
			rma.setRma_num(rmaNum);
			rma.setRma_hdr_sts_cd(stsCd);

			rmaRep.save(rma);
		}
	}

	private void deleteByRmaNum(String rma_num) {
		List<RMA_HDR> list = rmaRep.findByRma_num(rma_num);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				RMA_HDR temp = list.get(i);
				delete(temp);
			}
		}
	}

	private void saveByRmaNum(String rma_num, String stsNm) {
		List<RMA_HDR> list = rmaRep.findByRma_num(rma_num);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				RMA_HDR temp = list.get(i);
				temp.setRma_hdr_sts_cd(stsNm);
				rmaRep.save(temp);
			}
		}
	}

	private void delete(RMA_HDR obj) {
		rmaRep.delete(obj);
	}

	private List<JSONObject> getRmaHdrList() {
		String output = getJsonStrByURL(this.url);
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(output);
			JSONArray array = (JSONArray) obj;

			for (int i = 0; i < array.size(); i++) {
				JSONObject json = (JSONObject) array.get(i);
				list.add(json);

				Long id = (Long) json.get("id");
				String rmaNum = (String) json.get("rmaNum");
				String rmaHdrStsCd = (String) json.get("rmaHdrStsCd");

				System.out.println("id: " + id);
				System.out.println("rmaNum: " + rmaNum);
				System.out.println("rmaHdrStsCd: " + rmaHdrStsCd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private String getJsonStrByURL(String url) {
		Client client = Client.create();
		WebResource response = client.resource("http://localhost:8080/NS2015V07/ns-home/json");
		ClientResponse clientRes = response.accept("application/json").get(ClientResponse.class);
		if (clientRes.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + clientRes.getStatus());
		}
		return clientRes.getEntity(String.class);
	}

	@RequestMapping(value = "/welcome_01", params = "cancel", method = RequestMethod.POST)
	public ModelAndView helloWorld_1_2() { // TODO Called by Cancel

		String strToBePased = "<br><div align='center'>"
				+ "<h3>helloWorld_1_2/welcome_01</h3><br><br>";

		// mast be match with jsp name to be displayed
		return new ModelAndView("welcome_02", "message", strToBePased);
	}

	@RequestMapping(value = "/welcome_02", params = "next", method = RequestMethod.POST)
	public ModelAndView helloWorld_2_1() {

		String strToBePased = "<br><div align='center'>"
				+ "<h3>helloWorld_2_1/welcome_02</h3><br><br>";

		// mast be match with jsp name to be displayed
		return new ModelAndView("welcome_02", "message", strToBePased);
	}

	@RequestMapping(value = "/welcome_02", params = "cancel", method = RequestMethod.POST)
	public ModelAndView helloWorld_2_2() {

		String strToBePased = "<br><div align='center'>"
				+ "<h3>helloWorld_2_2/welcome_02</h3><br><br>";

		// mast be match with jsp name to be displayed
		return new ModelAndView("welcome_01", "message", strToBePased);
	}
}
