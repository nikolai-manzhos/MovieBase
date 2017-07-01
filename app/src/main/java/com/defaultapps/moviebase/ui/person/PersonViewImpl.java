package com.defaultapps.moviebase.ui.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.person.PersonInfo;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.person.adapter.CreditsCastAdapter;
import com.defaultapps.moviebase.ui.person.adapter.CreditsCrewAdapter;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.SimpleItemDecorator;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonViewImpl extends BaseFragment implements PersonContract.PersonView {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.errorTextView)
    TextView errorText;

    @BindView(R.id.personPortrait)
    CircleImageView circleImageView;

    @BindView(R.id.personBiography)
    TextView staffBiographyView;

    @BindView(R.id.toolbarTitle)
    TextView toolbarTitleView;

    @BindView(R.id.contentContainer)
    LinearLayout contentContainer;

    @BindView(R.id.castCreditsRecyclerView)
    RecyclerView castRecyclerView;

    @BindView(R.id.crewCreditsRecyclerView)
    RecyclerView crewRecyclerView;

    @BindView(R.id.errorButton)
    Button errorButton;

    @Inject
    PersonPresenterImpl presenter;

    @Inject
    CreditsCastAdapter castAdapter;

    @Inject
    CreditsCrewAdapter crewAdapter;

    private Unbinder unbinder;


    public static PersonViewImpl createInstance(int staffId) {
        PersonViewImpl staffView = new PersonViewImpl();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.PERSON_ID, staffId);
        staffView.setArguments(bundle);
        return staffView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        ((PersonActivity) getActivity()).getActivityComponent().inject(this);
        initRecyclerViews();
        presenter.onAttach(this);

        int personId = getArguments().getInt(AppConstants.PERSON_ID);
        if (savedInstanceState == null) {
            presenter.requestPersonInfo(personId, true);
        } else {
            presenter.requestPersonInfo(personId, false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
    }

    @OnClick(R.id.backButton)
    void onBackClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void displayStaffInfo(PersonInfo personInfo) {
        Picasso
                .with(getContext())
                .load("https://image.tmdb.org/t/p/w300" + personInfo.getProfilePath())
                .placeholder(R.drawable.placeholder_human)
                .into(circleImageView);
        String biography = personInfo.getBiography() != null ?
                personInfo.getBiography() : getString(R.string.person_biography_empty);
        staffBiographyView.setText(biography);
        toolbarTitleView.setText(personInfo.getName());

        castAdapter.setData(personInfo.getMovieCredits().getCast());
        crewAdapter.setData(personInfo.getMovieCredits().getCrew());
    }

    @Override
    public void hideData() {
        contentContainer.setVisibility(View.GONE);
    }

    @Override
    public void showData() {
        contentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        errorText.setVisibility(View.GONE);
        errorButton.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        errorText.setVisibility(View.VISIBLE);
        errorButton.setVisibility(View.VISIBLE);
    }

    public void initRecyclerViews() {
        SimpleItemDecorator simpleItemDecorator = new SimpleItemDecorator(30, true);
        castRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        castRecyclerView.setAdapter(castAdapter);
        castRecyclerView.setNestedScrollingEnabled(false);
        castRecyclerView.addItemDecoration(simpleItemDecorator);

        crewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        crewRecyclerView.setAdapter(crewAdapter);
        crewRecyclerView.setNestedScrollingEnabled(false);
        crewRecyclerView.addItemDecoration(simpleItemDecorator);
    }
}
