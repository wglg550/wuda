package com.qmth.wuda.teaching.api;

import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.config.DictionaryConfig;
import com.qmth.wuda.teaching.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Api(tags = "cas api Controller")
@RestController
@RequestMapping("/${prefix.url.wuda}/cas")
public class CasApiController {
    private final static Logger log = LoggerFactory.getLogger(SysController.class);

    @Resource
    DictionaryConfig dictionaryConfig;

    @ApiOperation(value = "cas鉴权接口")
    @RequestMapping(value = "/authentication/{studentCode}", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public void authentication(HttpServletRequest request, HttpServletResponse response, @PathVariable String studentCode) throws IOException, ServletException {
        log.info("authentication is come in");
//        String uid = request.getRemoteUser();
//        if (Objects.isNull(uid)) {
//            throw new BusinessException("请先登录");
//        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.sendRedirect(dictionaryConfig.sysDomain().getReportUrl() + studentCode);
    }
}
