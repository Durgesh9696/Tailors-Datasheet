package com.durgesh.tailorsdatasheet;

import android.widget.EditText;

/**
 * Created by Durgesh Parekh on 01/18/18.
 */

class Utility {
    public static boolean isBlankField(EditText etPersonData)
    {
        return etPersonData.getText().toString().trim().equals("");
    }
}
