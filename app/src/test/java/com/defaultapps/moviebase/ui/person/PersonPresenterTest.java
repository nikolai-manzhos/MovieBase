package com.defaultapps.moviebase.ui.person;

import com.defaultapps.moviebase.data.models.responses.person.PersonInfo;
import com.defaultapps.moviebase.domain.usecase.PersonUseCaseImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class PersonPresenterTest {

    @Mock
    private PersonContract.PersonView view;

    @Mock
    private PersonUseCaseImpl useCase;

    private PersonContract.PersonPresenter presenter;
    private TestScheduler testScheduler;

    private final int PERSON_ID = 2838;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new PersonPresenterImpl(useCase);
        testScheduler = new TestScheduler();
        presenter.onAttach(view);
    }

    @Test
    public void requestPersonInfoSuccess() throws Exception {
        PersonInfo result = new PersonInfo();
        Observable<PersonInfo> observable = Observable.just(result).subscribeOn(testScheduler);
        when(useCase.requestPersonData(anyInt(), anyBoolean())).thenReturn(observable);

        presenter.requestPersonInfo(PERSON_ID, false);

        verify(view).hideData();
        verify(view).showLoading();

        testScheduler.triggerActions();

        verify(view).hideLoading();
        verify(view, times(2)).hideError();
        verify(view).showData();
        verify(view).displayStaffInfo(result);
    }

    @Test
    public void requestPersonInfoFailure() throws Exception {
        Observable<PersonInfo> observable = Observable.error(new Exception("Network error."));
        when(useCase.requestPersonData(anyInt(), anyBoolean())).thenReturn(observable);

        presenter.requestPersonInfo(PERSON_ID, true);

        verify(view).hideError();
        verify(view).showLoading();

        testScheduler.triggerActions();

        verify(view).hideLoading();
        verify(view, times(2)).hideData();
        verify(view).showError();
        verify(view, never()).showData();
        verify(view, never()).displayStaffInfo(any());
    }
}
