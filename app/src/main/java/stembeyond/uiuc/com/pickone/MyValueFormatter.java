package stembeyond.uiuc.com.pickone;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by imranafrulbasha on 12/25/16.
 */

public class MyValueFormatter implements ValueFormatter{
    private DecimalFormat mFormat;

    MyValueFormatter(){
        mFormat = new DecimalFormat("###,###,##0"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value) + " "; // e.g. append a dollar-sign
    }
}
