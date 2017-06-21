package com.wm.brta.controller.web;

import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.wm.brta.domain.PendingReport;
import com.wm.brta.dto.PendingStoreReportDTO;
import com.wm.brta.service.BalePickupService;


@RestController
public class ReportMgmtController {

	@Autowired
	BalePickupService balePickupService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportMgmtController.class);

	
	@RequestMapping(value = "/reportMgmt/getAll/pendingReports", method = RequestMethod.POST)
	@ResponseBody
	public Set<PendingReport> getAllPendingReports(@RequestBody PendingStoreReportDTO pendingStoreReportDTO){
		Set<PendingReport> pendingreports = new HashSet<PendingReport>();
		try{
		pendingreports =balePickupService.getAllPendingPickups(pendingStoreReportDTO);
		}catch(Exception e){
			LOGGER.error("Error:ReportMgmtController:getAllPendingPickups(pendingStoreReportDTO)=====>" +e);
		}
		return pendingreports;
	}
	


}
