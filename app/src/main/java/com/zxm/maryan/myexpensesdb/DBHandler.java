package com.zxm.maryan.myexpensesdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION=1;

    private static  final String DB_NAME="DBExpenses";

    private static final String TABLE_PRODUCTS="products";
    private static  final String TABLE_EXPENSES="expenses";
    private static final String TABLE_CATEGORIES="categories";
    private static final String VIEW_EXPENSES="viewExpenses";


    //proizvodi polinja
    private static final String FIELD_ID_PRODUCT="id_product";
    private static final String FIELD_PRODUCT="product";
    private static final String FIELD_MEASURE="measure";
    private static final String FIELD_CTG="ctg";
    private static final String FIELD_ACTIVE_PRODUCT="active_product";
    //kategorija polinja
    private static final String FIELD_ID_CTG="id_ctg";
    private static final String FIELD_CATEGORY="category";
    private static final String FIELD_ACTIVE_CAT="active_cat";
    //edm polinja
    //private static final String POLE_EDM_ID="id_edm";
    //private static final String POLE_EDM="EdMerka";
    //trosoci polinja
    private static  final String FIELD_ID_EXPENSE="id";
    private static  final String FIELD_DATE_EXPENSE="date_expense";
    private static final String FIELD_PRODUCT_ID_EXPENSE="product_id";
    private static final String FIELD_QTY_EXPENSE="qty";
    private static final String FIELD_PRICE_EXPENSE="price";
    private static  final String FIELD_DESCRIPTION_EXPENSE="description";

    public DBHandler(Context context){

        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
/**
 *
        String CREATE_EDMERKI="CREATE TABLE IF NOT EXISTS "+TABLE_EDM+" (" +
                                POLE_EDM_ID+" INTEGER PRIMARY KEY  NOT NULL  UNIQUE , " +
                                POLE_EDM+"  NOT NULL  UNIQUE )";
 */

        String CREATE_CATEGORIES="CREATE TABLE IF NOT EXISTS "+TABLE_CATEGORIES+"(" +
                                FIELD_ID_CTG+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "+
                                FIELD_CATEGORY+" TEXT NOT NULL UNIQUE, "+
                                FIELD_ACTIVE_CAT+" BOOLEAN DEFAULT (1))";

        String CREATE_PRODUCTS="CREATE TABLE IF NOT EXISTS "+TABLE_PRODUCTS+" (" +
                FIELD_ID_PRODUCT+" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , " +
                FIELD_PRODUCT+" TEXT NOT NULL  UNIQUE , " +
                FIELD_MEASURE+" INTEGER NOT NULL , " +
                FIELD_CTG+" INTEGER NOT NULL , " +
                FIELD_ACTIVE_PRODUCT+" BOOLEAN DEFAULT (1))";

        String CREATE_EXPENSES="CREATE TABLE IF NOT EXISTS "+TABLE_EXPENSES+" (" +
                FIELD_ID_EXPENSE+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                FIELD_DATE_EXPENSE+" DATETIME NOT NULL, " +
                FIELD_PRODUCT_ID_EXPENSE+" INTEGER NOT NULL, " +
                FIELD_QTY_EXPENSE+" DECIMAL (9,2) NOT NULL, " +
                FIELD_PRICE_EXPENSE+" DECIMAL (8,2) NOT NULL, " +
                FIELD_DESCRIPTION_EXPENSE+" TEXT)";
/**
        String CREATE_VIEW_PROIZVODI="CREATE VIEW IF NOT EXISTS PregledProizvodi AS SELECT proizvodi.id_proizvod," +
                "proizvodi.naziv,edmerki.EdMerka,kategorii.kategorija " +
                "FROM proizvodi,edmerki,kategorii " +
                "WHERE (proizvodi.edm=id_edm AND proizvodi.pr_kat=id_kat) " +
                "ORDER BY proizvodi.naziv";

        String CREATE_VIEW_PREGLED="CREATE VIEW IF NOT EXISTS Pregled AS SELECT Trosoci.id_trosok,Trosoci.datum," +
                "Trosoci.proizvod,Trosoci.kolicina,Trosoci.cena,Trosoci.kolicina*Trosoci.cena AS iznos,Trosoci.opis," +
                "Proizvodi.naziv,EdMerki.EdMerka,Kategorii.kategorija FROM Trosoci,Proizvodi,Kategorii,Edmerki" +
                " WHERE (Trosoci.proizvod=Proizvodi.id_proizvod AND Proizvodi.edm=EdMerki.id_edm AND"+
                " Proizvodi.pr_kat=Kategorii.id_kat)" +
                " ORDER BY datum ASC";
*/
        db.execSQL(CREATE_CATEGORIES);
        db.execSQL(CREATE_PRODUCTS);
        db.execSQL(CREATE_EXPENSES);
        //db.execSQL((CREATE_VIEW_PROIZVODI));
       // db.execSQL(CREATE_VIEW_PREGLED);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
// Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EXPENSES);
        //db.execSQL("DROP VIEW IF EXISTS "+VIEW_PROIZVODI);
        //db.execSQL("DROP VIEW IF EXISTS "+VIEW_TROSOCI);

        // create fresh tables
        this.onCreate(db);
    }

    public boolean addCategory(String kat){

        boolean success=true;

       SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(FIELD_CATEGORY, kat);

        try{
            //db.insert(TABLE_KATEGORIJA, null, values);
            db.insertOrThrow(TABLE_CATEGORIES,null,values);
        } catch (Exception ex){
            success=false;
        } finally {
            db.close();
        }

        return success;
    }

    /**
    public boolean addMeasure(String edm){

        boolean success=true;

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(POLE_EDM, edm);

        try{
            //db.insert(TABLE_EDM,null,values);
            db.insertOrThrow(TABLE_EDM,null,values);
        } catch (Exception ex){
            success=false;
        } finally {
            db.close();
        }

        return success;

    }

    public boolean addProduct(Proizvod p){

        boolean success=true;

        return success;
    }

    public boolean addTrosok(Trosok t){

        boolean success=true;

        return success;
    }
     */
    
    public int countProizvodi(){

        SQLiteDatabase db=this.getReadableDatabase();

        int rows=(int) DatabaseUtils.queryNumEntries(db,TABLE_PRODUCTS);

        return rows;
    }

}
