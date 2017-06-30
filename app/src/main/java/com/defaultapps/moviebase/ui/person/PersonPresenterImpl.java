package com.defaultapps.moviebase.ui.person;

import com.defaultapps.moviebase.data.usecase.PersonUseCaseImpl;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

@PerActivity
public class PersonPresenterImpl extends BasePresenter<PersonContract.PersonView> implements PersonContract.PersonPresenter {

    private PersonUseCaseImpl personUseCase;

    @Inject
    public PersonPresenterImpl(PersonUseCaseImpl personUseCase) {
        this.personUseCase = personUseCase;
    }

    @Override
    public void requestPersonInfo(int personId, boolean force) {
        if (getView() != null) {
            getView().hideData();
            getView().hideError();
            getView().showLoading();
        }
        getCompositeDisposable().add(
                personUseCase.requestPersonData(personId, force).subscribe(
                        personInfo -> {
                            if (getView() != null) {
                                getView().hideLoading();
                                getView().hideError();
                                getView().showData();
                                getView().displayStaffInfo(personInfo);
                            }
                        },
                        err -> {
                            if (getView() != null) {
                                getView().hideLoading();
                                getView().hideData();
                                getView().showError();
                            }
                        }

                )
        );
    }
}
