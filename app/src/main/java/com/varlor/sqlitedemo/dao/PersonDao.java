package com.varlor.sqlitedemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.varlor.sqlitedemo.DBHelper;
import com.varlor.sqlitedemo.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDao {
    private Context context;
    private DBHelper dbHelper;
    public PersonDao(Context context){
        this.context=context;
        dbHelper = new DBHelper(context);
    }

    //添加记录
    public void add(String name,int age){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put("age",age);
            values.put("name",name);
            db.insert("person",null,values);
        }
        db.close();
    }


    //查询所有
    public List<Person> findAll(){
        List<Person> persons = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if(db.isOpen()){
            persons = new ArrayList<Person>();
            Cursor cursor = db.query("person", null, null, null, null, null, null);
            while(cursor.moveToNext()){
                Person person = new Person();
                person.setName(cursor.getString(cursor.getColumnIndex("name")));
                person.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                person.setMoney(cursor.getInt(cursor.getColumnIndex("account")));
                persons.add(person);
            }
            cursor.close();
            db.close();
        }
        return persons;

    }

    //条件查询
    public List<Person> find(String name){
        List<Person> persons = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if(db.isOpen()){
            persons = new ArrayList<Person>();
            Cursor cursor = db.query("person", null, "name=?", new String[]{name}, null, null, null);
            while(cursor.moveToNext()){
                Person person = new Person();
                person.setName(cursor.getString(cursor.getColumnIndex("name")));
                person.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                persons.add(person);
            }
            cursor.close();
            db.close();
        }
        return persons;
    }

    //更新数据
    public void update(String name, String newname, int newage) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", newname);
            contentValues.put("age", newage);
            db.update("person", contentValues, "name=?", new String[]{name});
            db.close();
        }
    }

    //删除数据
    public void delete(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete("person", "name=?", new String[]{name});
            db.close();
        }
    }

    //添加字段
    public void addMoneyColumn(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            //给amos1账户里设置1000元,amost account=0;
            db.execSQL("update person  set account=?  where name = ?",new Object[]{1000,"老王"});
            db.execSQL("update person  set account=?  where name = ?",new Object[]{0,"老张"});
        }
    }

    public void transferMoney() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db.isOpen()){
            try{

                db.beginTransaction();

                //从老王账户里扣除200元
                db.execSQL("update person  set account=account-?  where name = ?",new Object[]{200,"老王"});
                //把老王的钱转给老张
                db.execSQL("update person set account=account+? where name=?",new Object[]{200,"老张"});

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                //显示的设置数据事务是否成功
                db.setTransactionSuccessful();
                db.endTransaction();

                db.close();
            }
        }


    }


}
