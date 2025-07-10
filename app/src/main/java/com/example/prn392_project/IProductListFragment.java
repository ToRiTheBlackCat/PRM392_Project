package com.example.prn392_project;

import android.content.Context;

public interface IProductListFragment {
    // Get the ResourceId of the direction(or Fragment) to navigate to
    public int GetProductDetailActionId();
    public Context GetContext();
}
