package demo.apps.imageprocessor.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.InputStream;

import javax.inject.Inject;

import demo.apps.imageprocessor.R;
import demo.apps.imageprocessor.common.BaseFragment;
import demo.apps.imageprocessor.di.components.IMainActivityComponent;
import demo.apps.imageprocessor.filters.BicubicFilter;
import demo.apps.imageprocessor.filters.BilinearFilter;
import demo.apps.imageprocessor.filters.IFilter;
import demo.apps.imageprocessor.presenter.ImageFragmentPresenterImpl;

public class ImageFragment extends BaseFragment implements IImageFragmentView {

    @Inject
    ImageFragmentPresenterImpl presenter;

    private Activity activity;
    private View rootView;

    private Button loadImgBtn;
    private Button btn;
    private ImageView srcImage;
    private ImageView destImage;
    private EditText sizeX, sizeY, ratioText, iterationsText;

    public ImageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_image, container, false);

            btn = (Button)rootView.findViewById(R.id.scale_btn);
            srcImage = (ImageView) rootView.findViewById(R.id.img_src);
            destImage = (ImageView) rootView.findViewById(R.id.img_dest);
            loadImgBtn = (Button)rootView.findViewById(R.id.load_image_btn);

            sizeX = (EditText)rootView.findViewById(R.id.sizeX);
            sizeY = (EditText)rootView.findViewById(R.id.sizeY);
            ratioText = (EditText)rootView.findViewById(R.id.ratio);
            iterationsText = (EditText)rootView.findViewById(R.id.iterations);
        }
        return rootView;
    }


    @Override
    public void onDestroyView() {
        if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        loadImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadImageTask((ImageView) rootView.findViewById(R.id.img_src))
                        .execute(String.format("http://lorempixel.com/%s/%s/cats/", sizeX.getText().toString(), sizeY.getText().toString()));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null)
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);

                Bitmap bmp = fromImageView(srcImage);

                double ratio = Double.parseDouble(ratioText.getText().toString());
                int iterations = Integer.parseInt(iterationsText.getText().toString());

                (new ImageScaleTask(bmp, ratio, iterations)).execute(bmp);
            }
        });

    }

    @Override
    public void addBitmapToImageView(ImageView destImage, Bitmap bitmap) {
        destImage.setImageBitmap(bitmap);
    }

    @Override
    public void replaceToDetailFragment(int id) {

    }

    private Bitmap fromImageView(ImageView srcImage) {
        BitmapDrawable bmpDraw = (BitmapDrawable) srcImage.getDrawable();
        Bitmap bmp = bmpDraw.getBitmap().copy(Bitmap.Config.RGB_565, true);
        return bmp;
    }

    //need moved to presenter; call it via presenter methods

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final ProgressDialog dialog = new ProgressDialog(getContext());
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setCancelable(false);
            dialog.setMessage("Image is loading...");
            dialog.show();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            dialog.dismiss();
            addBitmapToImageView(bmImage, result);
        }
    }

    private class ImageScaleTask extends AsyncTask<Bitmap, Void, Bitmap> {
        private final ProgressDialog dialog = new ProgressDialog(getContext());
        private final double ratio;
        private final int iterations;
        private Bitmap bmp;

        public ImageScaleTask(Bitmap bmp, double ratio, int iterations) {
            this.bmp = bmp;
            this.ratio = ratio;
            this.iterations = iterations;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setCancelable(false);
            dialog.setMessage("Please wait...");
            dialog.show();
        }

        protected Bitmap doInBackground(Bitmap... bmps) {
            //IFilter filter = new BilinearFilter(ratio);
            IFilter filter = new BicubicFilter(ratio);

            bmp = presenter.scale(bmps[0], filter, iterations);
            return bmp;
        }

        protected void onPostExecute(Bitmap bitmap) {
            dialog.dismiss();
            addBitmapToImageView(destImage, bmp);
        }
    }

}
