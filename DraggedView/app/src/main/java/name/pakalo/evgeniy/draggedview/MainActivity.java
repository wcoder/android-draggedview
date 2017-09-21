package name.pakalo.evgeniy.draggedview;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout trashContainer;

    private int mXDelta;
    private int mYDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.droppedImg).setOnTouchListener(imageOnTouchListener);

        trashContainer = (LinearLayout) findViewById(R.id.trashContainer);
    }


    private final View.OnTouchListener imageOnTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent event) {

            final int x = (int) event.getRawX();
            final int y = (int) event.getRawY();

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    mXDelta = x - lParams.leftMargin;
                    mYDelta = y - lParams.topMargin;

                    view.bringToFront();
                    trashContainer.setVisibility(View.VISIBLE);
                    break;
                case MotionEvent.ACTION_UP:
                    if(isViewInBounds(trashContainer, x, y)) {
                        view.setVisibility(View.GONE);
                        // remove from layout
                    }
                    trashContainer.setVisibility(View.GONE);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:

                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = x - mXDelta;
                    layoutParams.topMargin = y - mYDelta;
                    view.setLayoutParams(layoutParams);

                    if(isViewInBounds(trashContainer, x, y)) {
                        trashContainer.setBackgroundColor(Color.RED);
                    } else {
                        trashContainer.setBackgroundColor(Color.parseColor("#33ff0000"));
                    }

                    Log.i("MOVE ", "X:" + x + "  Y:" +y);
                    break;

                default:
                    break;
            }
            return true;
        }
    };



    private boolean isViewInBounds(View view, int x, int y){
        Rect outRect = new Rect();
        int[] location = new int[2];

        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }
}
