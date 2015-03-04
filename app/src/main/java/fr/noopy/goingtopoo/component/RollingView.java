package fr.noopy.goingtopoo.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.landru.goingtopoo.R;

import java.util.ArrayList;

/**
 * Created by cyrille on 03/03/15.
 */
public class RollingView extends LinearLayout {

    private ArrayList<RollingDigit> digitView;
    private ArrayList<RollingDigit> decimalView;
    private double mainValue;


    /**
     * Constructor
     * @param context
     */
    public RollingView(Context context) {
        this(context, null);
    }

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public RollingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor
     * @param context
     * @param attrs
     * @param defStyle
     */
    public RollingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.RollingView);
        attributes.recycle();

        this.mainValue = 0;

        this.setOrientation(HORIZONTAL);

        this.digitView = new ArrayList<RollingDigit>();
        this.decimalView = new ArrayList<RollingDigit>();

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_rolling_view, this, true);

        digitView.add((RollingDigit)findViewById(R.id.digitOne));
        digitView.add((RollingDigit)findViewById(R.id.digitTwo));
        digitView.add((RollingDigit)findViewById(R.id.digitThree));

        decimalView.add((RollingDigit)findViewById(R.id.decimalOne));
        decimalView.add((RollingDigit)findViewById(R.id.decimalTwo));

    }

    @Override
    public void onFinishInflate() {

    }

    /**
     * Set the value of the display
     * @param value value to display
     */
    public void setValue(double value) {
        if (this.mainValue != value) {
            for (int i = 0; i < digitView.size(); i++) {
                digitView.get(i).setValue(getDigit(value, i));
            }
            for (int i = 0; i < decimalView.size(); i++) {
                decimalView.get(i).setValue(getDecimal(value, i));
            }
        }
    }

    /**
     * Get a digit at a specific position (0 => least significant digit) - Whole integer
     * @param data
     * @param pos
     * @return
     */
    private static int getDigit(double data, int pos) {
        int intData = (int)Math.floor(data);
        return (int)Math.floor((intData % Math.pow(10, pos + 1)) / Math.pow(10, pos));
    }

    /**
     * Get a digit in the decimal part at a position (0 => most significant digit)
     * @param data
     * @param pos
     * @return
     */
    private static int getDecimal(double data, int pos) {
        int shift = (int)Math.round(Math.pow(10, pos));
        double decimal = data * shift - Math.floor(data * shift);
        return (int)Math.floor(decimal * 10);
    }

}