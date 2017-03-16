package com.ele.controller;

import com.ele.model.hackathon.HisEcoEnv;
import com.ele.service.HackathonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author oukailiang
 * @create 2016-10-12 下午3:50
 */
@Controller
@RequestMapping("/")
public class HackathonController {
    private static Logger Log = Logger.getLogger(HackathonController.class);
    @Autowired
    private HackathonService hackathonService;

    @RequestMapping(value = "/insert.do", method = RequestMethod.GET)
    public void getVoucherById(HttpServletRequest request) {
        List<HisEcoEnv> hisEcoEnvList = new ArrayList<HisEcoEnv>();
        hackathonService.batchInsertHisEcoEnv(hisEcoEnvList);

    }
}
