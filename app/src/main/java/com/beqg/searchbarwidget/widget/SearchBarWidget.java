package com.beqg.searchbarwidget.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.beqg.searchbarwidget.R;

public class SearchBarWidget extends LinearLayout implements View.OnClickListener, TextWatcher {

    private LinearLayout mLlBack;
    private EditText mEdtSearchContent;
    private LinearLayout mLlCleanInput;
    private Button mBtnSearch;
    private String mInputContent;
    private OnSearchBarListener mListener;

    public SearchBarWidget(Context context) {
        this(context, null);
    }

    public SearchBarWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_search_bar, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //找子控件
        findView();

        setListener();
    }

    private void findView() {
        mLlBack = findViewById(R.id.ll_back);
        mEdtSearchContent = findViewById(R.id.edt_search_content);
        mLlCleanInput = findViewById(R.id.ll_clean_input);
        mBtnSearch = findViewById(R.id.btn_search);
    }

    private void setListener() {
        mLlBack.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);
        mLlCleanInput.setOnClickListener(this);
        mEdtSearchContent.addTextChangedListener(this);
        mBtnSearch.setClickable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                if (mListener != null) {
                    mListener.onClickBack(mLlBack);
                }
                break;
            case R.id.ll_clean_input:
                mEdtSearchContent.setText("");
                break;
            case R.id.btn_search:
                if (mListener != null) {
                    mListener.onClickSearchButton(mBtnSearch, mInputContent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mInputContent = charSequence.toString().trim();
        if (TextUtils.isEmpty(mInputContent)) {
            mBtnSearch.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            mBtnSearch.setClickable(false);
            mLlCleanInput.setVisibility(INVISIBLE);
        } else {
            mBtnSearch.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            mBtnSearch.setClickable(true);
            mLlCleanInput.setVisibility(VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public interface OnSearchBarListener {
        //点击了返回按钮
        void onClickBack(View v);

        //点击了搜索按钮
        void onClickSearchButton(View v, String inputContent);
    }

    public void setOnSearchBarListener(OnSearchBarListener listener) {
        mListener = listener;
    }

    public String getInputContent() {
        return mInputContent;
    }

    public void setInputContent(String inputContent) {
        mEdtSearchContent.setText(inputContent);
    }
}
