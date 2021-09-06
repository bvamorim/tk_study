package com.talkdesk.aggregate.services;

import com.talkdesk.aggregate.databuilder.AggregateResponseDataBuilder;
import com.talkdesk.aggregate.exception.ExternalServiceNotFoundException;
import com.talkdesk.aggregate.exception.InvalidParameterException;
import com.talkdesk.aggregate.exception.NoPrefixesFilesFoundException;
import com.talkdesk.aggregate.model.datatransferobject.AggregateResponse;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AggregateServiceImpl.class})
public class AggregateServiceImplTest {

    @Autowired
    private AggregateService aggregateService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    //REVIEW
    @Test
    public void processPhones_ValidInput_ShouldThrownInvalidParameterExceptionWithExpectedMessage() throws NoPrefixesFilesFoundException, ExternalServiceNotFoundException {

        String validInput = "[\"+1983236248\", \"+1 7490276403\", \"001382355A\", \"+351917382672\", \"+35191734022\"]";

        AggregateResponse aggregateResponseRecived = aggregateService.processPhones(validInput);
        AggregateResponse aggregateResponseExpected = AggregateResponseDataBuilder.getValidAggregateResponse();

        assertThat(aggregateResponseRecived).isEqualToComparingFieldByField(aggregateResponseExpected);
    }

    @Test
    public void processPhones_InvalidInput_ShouldThrownInvalidParameterExceptionWithExpectedMessage() throws Exception {

        String phonesDTO = "";

        exceptionRule.expect(InvalidParameterException.class);
        exceptionRule.expectMessage("The parameter [phoneString] is not valid with actual value of [null]");

        aggregateService.processPhones(phonesDTO);
    }
}
