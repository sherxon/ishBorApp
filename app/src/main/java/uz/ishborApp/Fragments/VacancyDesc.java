package uz.ishborApp.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.path.android.jobqueue.JobManager;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import uz.ishborApp.Activity.BaseDrawerActivity;
import uz.ishborApp.MyApplication;
import uz.ishborApp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link VacancyDesc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VacancyDesc extends Fragment {

    @Bind(R.id.wvJobDesc)
    WebView jobDesc;

    @Inject
    JobManager jobManager;

    BaseDrawerActivity parentActivity;


    // TODO: Rename and change types and number of parameters
    public static VacancyDesc newInstance(String url) {
        VacancyDesc fragment = new VacancyDesc();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    public VacancyDesc() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        MyApplication.get(getActivity()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_vacancy_desc, container, false);

        parentActivity= (BaseDrawerActivity) getActivity();
        ButterKnife.bind(this, view);

        WebSettings ws=jobDesc.getSettings();
        ws.setJavaScriptEnabled(true);
        jobDesc.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public void performClick(String strl) {
                // TODO: 3/18/16 get id and save to favourites
                System.out.println(strl);
                Long vacId =Long.valueOf(strl);


            }
        }, "ok");
        jobDesc.loadUrl(getArguments().getString("url"));
        jobDesc.setWebViewClient(new MyWebViewClient());

        return view;
    }


    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
             view.loadUrl(url);
             return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            parentActivity.showProgress();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            parentActivity.hideProgress();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            parentActivity.hideProgress();
            // TODO: 3/18/16 add custom error message
        }
    }
}
