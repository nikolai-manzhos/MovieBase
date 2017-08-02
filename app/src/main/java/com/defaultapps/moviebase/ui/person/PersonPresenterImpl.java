package com.defaultapps.moviebase.ui.person;

import com.defaultapps.moviebase.data.usecase.PersonUseCase;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

@PerFragment
public class PersonPresenterImpl extends BasePresenter<PersonContract.PersonView> implements PersonContract.PersonPresenter {

    private PersonUseCase personUseCase;

    @Inject
    public PersonPresenterImpl(PersonUseCase personUseCase) {
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
