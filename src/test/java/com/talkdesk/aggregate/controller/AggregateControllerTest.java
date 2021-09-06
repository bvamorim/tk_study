package com.talkdesk.aggregate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.talkdesk.aggregate.databuilder.AggregateResponseDataBuilder;
import com.talkdesk.aggregate.exception.InvalidParameterException;
import com.talkdesk.aggregate.model.datatransferobject.AggregateResponse;
import com.talkdesk.aggregate.model.datatransferobject.PrefixDTO;
import com.talkdesk.aggregate.model.datatransferobject.SectorSumDTO;
import com.talkdesk.aggregate.model.domainvalue.Sector;
import com.talkdesk.aggregate.services.AggregateService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.talkdesk.aggregate.controller.AggregateController;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>TalkdeskApplicationTests</h1>
 * This class executes Tests to validade the Application.
 *
 * @author  Bruno Amorim
 * @version 1.0
 * @since   2019-10-03
 */
@RunWith(MockitoJUnitRunner.class)
public class AggregateControllerTest {

    @InjectMocks
    private AggregateController controller;

    @Mock
    private AggregateService aggregateService;

    private Gson gsonUtil;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp(){
        gsonUtil = new Gson();
    }

    @Test
    public void processPhones_ValidInputWithValidAndInvalidPhones_ShouldReturnWithExpectedJSONWithNoError() throws Exception {

        String validInput = "[\"+1983248\", \"001382355\", \"+147 8192\", \"+4439877\"]";

        assertThat(aggregateService).isNotNull();

        AggregateResponse validAggregateResponseMock = AggregateResponseDataBuilder.getValidAggregateResponse();

        when(aggregateService.processPhones(validInput))
                .thenReturn(validAggregateResponseMock);

        ResponseEntity<String> reponseEntity = controller.processPhones(validInput);

        String aggregateResultRecived = gsonUtil.toJson(reponseEntity);

        String aggregateResultExpected = "{\"status\":\"OK\",\"headers\":{},\"body\":\"{\\\"1\\\":{\\\"Banking\\\":1,\\\"Technology\\\":1},\\\"3519173\\\":{\\\"Clothing\\\":2}}\"}";

        assertThat(aggregateResultRecived).isEqualTo(aggregateResultExpected);

    }

    @Test
    public void processPhones_EmptyInput_ShouldReturnEmptyResponse() throws Exception {

        String emptyInput = "[]";

        assertThat(aggregateService).isNotNull();

        AggregateResponse validAggregateResponseMock = AggregateResponseDataBuilder.getEmptyAggregateResponse();

        when(aggregateService.processPhones(emptyInput))
                .thenReturn(validAggregateResponseMock);

        String aggregateResultRecived = gsonUtil.toJson(controller.processPhones(emptyInput));

        String aggregateResultExpected = "{\"status\":\"OK\",\"headers\":{}}";

        assertThat(aggregateResultRecived).isEqualTo(aggregateResultExpected);

    }

    @Test
    public void processPhones_InvalidInput_ShouldThrownInvalidParameterExceptionWithExpectedMessage() throws Exception {

        String invalidInput = "";

        InvalidParameterException exceptionMock = new InvalidParameterException("phoneString", null);
        when(aggregateService.processPhones(invalidInput))
                .thenThrow(exceptionMock);

        exceptionRule.expect(InvalidParameterException.class);
        exceptionRule.expectMessage("The parameter [phoneString] is not valid with actual value of [null]");

        controller.processPhones(invalidInput);
    }

}
