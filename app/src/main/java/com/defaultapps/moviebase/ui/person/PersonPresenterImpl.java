package com.defaultapps.moviebase.ui.person;

import com.defaultapps.moviebase.data.usecase.PersonUseCase;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

@PerFragment
public class PersonPresenterImpl extends BasePresenter<PersonContract.PersonView> implements PersonContract.PersonPresenter {

    private PersonUseCase personUseCase;

    @Inject
    PersonPresenterImpl(PersonUseCase personUseCase) {
        this.personUseCase = personUseCase;
    }

    @Override
    public void requestPersonInfo(int personId, boolean force) {
        getView().hideData();
        getView().hideError();
        getView().showLoading();
        getCompositeDisposable().add(
                personUseCase.requestPersonData(personId, force).subscribe(
                        personInfo -> {
                            getView().hideLoading();
                            getView().hideError();
                            getView().showData();
                            getView().displayStaffInfo(personInfo);
                        },
                        err -> {
                            getView().hideLoading();
                            getView().hideData();
                            getView().showError();
                        }

                )
        );
    }
}
