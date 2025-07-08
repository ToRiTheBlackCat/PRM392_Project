package com.example.prn392_project.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.prn392_project.R;
import com.example.prn392_project.data_classes.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ProductManagement.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCT = "Product";
    private static final String COLUMN_ID = "ProductId";
    private static final String COLUMN_NAME = "ProductName";
    private static final String COLUMN_PRICE = "ProductPrice";
    private static final String COLUMN_DESC = "Description";
    private static final String COLUMN_IMAGE = "ProductImage";

    public ProductDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        createUserTable();
    }

    //@Override
    public void onCreateDatabase(SQLiteDatabase db) {
        //createProductTable();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableProduct = "CREATE TABLE " + TABLE_PRODUCT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PRICE + " INTEGER, " +
                COLUMN_DESC + " TEXT, " +
                COLUMN_IMAGE + " INTEGER)";
        db.execSQL(createTableProduct);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(db);
    }

    // Tạo bảng Product
    public void createUserTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PRICE + " INTEGER, " +
                COLUMN_DESC + " TEXT, " +
                COLUMN_IMAGE + " INTEGER)";
        db.execSQL(query);
    }

    public void insertProduct(String name, int price, String description, int imageResourceId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DESC, description);
        values.put(COLUMN_IMAGE, imageResourceId);
        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }

    public void updateProduct(int id, String name, int price, String description, int imageResourceId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DESC, description);
        values.put(COLUMN_IMAGE, imageResourceId);
        db.update(TABLE_PRODUCT, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteAllProduct() {
        String sqlString;
        sqlString = "DELETE FROM " + TABLE_PRODUCT;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlString);
        db.close();
    }

    public List<Product> getAllUsers() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT, null);
        while (cursor.moveToNext()) {
            var product = new Product(
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4)
            );
            product.setProductId(cursor.getInt(0));

            productList.add(product);
        }
        cursor.close();
        db.close();

        return productList;
    }

    public void generateInitData() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(db);
        // Add temp data
        List<Product> productList = Product.getExampleProducts();

        for (var item : productList) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, item.getProductName());
            values.put(COLUMN_PRICE, item.getProductPrice());
            values.put(COLUMN_IMAGE, item.getProductImageId());
            db.insert(TABLE_PRODUCT, null, values);
        }
        db.close();
    }
}
