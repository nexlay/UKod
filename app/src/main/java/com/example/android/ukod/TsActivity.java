package com.example.android.ukod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.util.Log;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;


public class TsActivity extends AppCompatActivity implements OnPageChangeListener,OnLoadCompleteListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static String SAMPLE_PDF ;

    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ts);
        Toolbar myToolbar = findViewById(R.id.toolbar_custom);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }

        pdfView = findViewById(R.id.pdfView);


        Intent intentFromMain = getIntent();
        String currentTitle = intentFromMain.getStringExtra("codes");
        String secondTitle = intentFromMain.getStringExtra("ts");
        String thirdTitle = intentFromMain.getStringExtra("services");

        if(currentTitle != null && currentTitle.equals("kody_instalacji.pdf")){
            getSupportActionBar().setTitle(getString(R.string.description_of_work));
            SAMPLE_PDF = MainActivity.SAMPLE_FILE_CODES;

        }
        if(secondTitle != null && secondTitle.equals("wykaz_TS.pdf")  ) {
            getSupportActionBar().setTitle(getString(R.string.list_TS));
            SAMPLE_PDF = MainActivity.SAMPLE_FILE_TS;

        }
            if(thirdTitle != null && thirdTitle.equals("kody_serwis.pdf")){
            getSupportActionBar().setTitle(getString(R.string.list_service));
            SAMPLE_PDF = MainActivity.SAMPLE_FILE_SERVICE;

            }
        displayFromAsset(SAMPLE_PDF);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();}
        return super.onOptionsItemSelected(item);
    }
    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        pdfView.fromAsset(pdfFileName)
                .defaultPage(pageNumber)
                .enableSwipe(true)

                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }



}
