package com.varlor.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.varlor.sqlitedemo.dao.PersonDao;
import com.varlor.sqlitedemo.entity.Person;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(this.getFilesDir());
        //testAdd();

        //findAll();


        //findAllBy("amosli");

//        try {
//            testUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            testDelete();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

////        testAddMoneyColumn();
//        try {
//            testTransaction();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        findAll();
        //测试打开数据库文件
            openDBFile();

    }


    //测试添加方法
    public void testAdd(){
        PersonDao personDao = new PersonDao(this);
//        personDao.add("amosli",10);
//        personDao.add("amosli",10);
//        for(int i=0;i<10;i++){
//            personDao.add("amos"+i,10+i);
//        }
        //personDao.add("老王",30);
        //personDao.add("老张",31);


    }

    //测试查询所有方法
    public void findAll(){
        PersonDao personDao = new PersonDao(this);
        List<Person> personList = personDao.findAll();
        for(Person person:personList){
            Log.d("person:==================>>>>>>",person.toString());
        }
    }

    //测试带参数查询方法
    public void findAllBy(String name){
        PersonDao personDao = new PersonDao(this);
        List<Person> personList = personDao.find(name);
        for(Person person:personList){
            Log.d("person:==================>>>>>>",person.toString());
        }
    }

    //测试update
    public void testUpdate() throws Exception{
        PersonDao personDao = new PersonDao(this);
        personDao.update("amos0","0amos",35);
    }

    //测试 delete方法：
    public void testDelete() throws Exception{
        PersonDao personDao = new PersonDao(this);
        personDao.delete("amosli");
    }

    //测试添加money字段
    public void testAddMoneyColumn(){
        PersonDao personDao = new PersonDao(this);
        personDao.addMoneyColumn();
    }

    //测试事务
    public void testTransaction() throws Exception{
        PersonDao personDao = new PersonDao(this);
        personDao.transferMoney();
    }

    public void openDBFile(){
        List<Person> persons = null;
        File file = new File("/data/data/com.varlor.sqlitedemo/databases/sqlitedb");
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(file,null);
            if(sqLiteDatabase.isOpen()) {
                persons = new ArrayList<Person>();
                Cursor cursor = sqLiteDatabase.query("person", null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                Person person = new Person();
                person.setName(cursor.getString(cursor.getColumnIndex("name")));
                person.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                person.setMoney(cursor.getInt(cursor.getColumnIndex("account")));
                persons.add(person);
            }
            cursor.close();
            sqLiteDatabase.close();
        }
        System.out.println(persons);
    }
}