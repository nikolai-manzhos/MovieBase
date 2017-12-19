package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.data.TestSchedulerProvider;
import com.defaultapps.moviebase.data.models.responses.person.PersonInfo;
import com.defaultapps.moviebase.data.network.Api;
import com.defaultapps.moviebase.data.network.NetworkService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class PersonUseCaseTest {

    @Mock
    private NetworkService networkService;

    @Mock
    private Api api;


    private PersonUseCase useCase;
    private TestScheduler testScheduler;

    private PersonInfo actualResult;
    private Throwable actualError;

    private final int PERSON_ID = 28281;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        useCase = new PersonUseCaseImpl(networkService, new TestSchedulerProvider(testScheduler));
    }

    @Test
    public void requestPersonDataSuccess() throws Exception {
        PersonInfo expectedResult = new PersonInfo();
        Single<PersonInfo> single = Single.just(expectedResult).subscribeOn(testScheduler);
        when(networkService.getNetworkCall()).thenReturn(api);
        when(api.getPersonInfo(anyInt(), anyString(), anyString(), anyString())).thenReturn(single);

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
        Single<PersonInfo> single = Single.error(expectedError);
        when(networkService.getNetworkCall()).thenReturn(api);
        when(api.getPersonInfo(anyInt(), anyString(), anyString(), anyString())).thenReturn(single);

        useCase.requestPersonData(PERSON_ID, false).subscribe(
                personInfo -> {},
                err -> actualError = err);

        testScheduler.triggerActions();

        assertNotNull(actualError);
        assertEquals(expectedError, actualError);
    }
}
