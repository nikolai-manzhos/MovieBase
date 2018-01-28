package com.defaultapps.moviebase.domain.usecase;

import com.defaultapps.moviebase.data.models.responses.person.PersonResponse;
import com.defaultapps.moviebase.domain.repository.ApiRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class PersonUseCaseTest {

    @Mock
    private ApiRepository apiRepository;

    private PersonUseCase useCase;
    private TestScheduler testScheduler;

    private PersonResponse actualResult;
    private Throwable actualError;

    private static final int PERSON_ID = 28281;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        useCase = new PersonUseCaseImpl(apiRepository);
    }

    @Test
    public void requestPersonDataSuccess() throws Exception {
        PersonResponse expectedResult = new PersonResponse();
        Single<PersonResponse> single = Single.just(expectedResult).subscribeOn(testScheduler);

        when(apiRepository.requestPersonDetails(PERSON_ID))
                .thenReturn(single);

        useCase.requestPersonData(PERSON_ID, false).subscribe();

        //If previous call still ongoing it should be disposed and new one fired.
        useCase.requestPersonData(PERSON_ID, true).subscribe(
                personInfo -> actualResult = personInfo,
                err ->{}
        );
        testScheduler.triggerActions();

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void requestPersonDataFailure() throws Exception {
        Exception expectedError = new Exception("Network error.");
        Single<PersonResponse> single = Single.error(expectedError);

        when(apiRepository.requestPersonDetails(PERSON_ID))
                .thenReturn(single);

        useCase.requestPersonData(PERSON_ID, false).subscribe(
                personInfo -> {},
                err -> actualError = err);

        testScheduler.triggerActions();

        assertNotNull(actualError);
        assertEquals(expectedError, actualError);
    }
}
