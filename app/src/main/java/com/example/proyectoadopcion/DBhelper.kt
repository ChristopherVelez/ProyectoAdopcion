package com.example.proyectoadopcion

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBhelper (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object{
        private const val DATABASE_NAME = "DBappAdopcion.db"
        private const val DATABASE_VERSION =1
        private const val TABLE_NAMEMASCOTA = "mascota"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TIPOMASCOTA = "tipoMascota"
        private const val COLUMN_RAZA= "raza"
        private const val COLUMN_EDAD = "edad"
        private const val COLUMN_DESCRIPCION = "descripcion"
        private const val COLUMN_PESO = "peso"
        private const val COLUMN_GENERO = "genero"
        private const val COLUMN_URLIMG = "urlImg"

        //TABLA COORDINADOR MASCOTA
        private const val TABLE_NAMECOO = "coordinador"
        private const val COLUMN_IDCOO = "id"
        private const val COLUMN_CORREOCOO = "correo"
        private const val COLUMM_CONTRASENACOO = "contrasena"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQueryMacota = "CREATE TABLE $TABLE_NAMEMASCOTA ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TIPOMASCOTA TEXT, $COLUMN_RAZA TEXT, $COLUMN_EDAD TEXT, $COLUMN_DESCRIPCION TEXT, $COLUMN_PESO FLOAT, $COLUMN_GENERO TEXT, $COLUMN_URLIMG TEXT)"
        val createTableQueryCoordinador = "CREATE TABLE $TABLE_NAMECOO ($COLUMN_IDCOO INTEGER PRIMARY KEY, $COLUMN_CORREOCOO TEXT, $COLUMM_CONTRASENACOO TEXT)"
        db?.execSQL(createTableQueryMacota)
        db?.execSQL(createTableQueryCoordinador)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableMascotaQuery = "DROP TABLE IF EXISTS $TABLE_NAMEMASCOTA"
        val dropTableCoordinadorQuery = "DROP TABLE IF EXISTS $TABLE_NAMECOO"
        db?.execSQL(dropTableMascotaQuery)
        db?.execSQL(dropTableCoordinadorQuery)
        onCreate(db)

    }

    //CRUD MASCOTA
    fun insertarMascota(mascota: Mascota){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TIPOMASCOTA,mascota.tipoMascota)
            put( COLUMN_RAZA, mascota.raza)
            put( COLUMN_EDAD, mascota.edad)
            put( COLUMN_DESCRIPCION, mascota.descripcion)
            put( COLUMN_PESO, mascota.peso)
            put( COLUMN_GENERO, mascota.genero)
            put( COLUMN_URLIMG, mascota.urlImg)
        }
        db.insert(TABLE_NAMEMASCOTA, null, values)
        db.close()
    }
    fun getAllMascotas(): List<Mascota>{
        val mascotaList = mutableListOf<Mascota>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAMEMASCOTA"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val tipoMascota = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPOMASCOTA))
            val raza = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RAZA))
            val edad = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EDAD))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
            val peso = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_PESO))
            val genero = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENERO))
            val urlImg = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URLIMG))
            val mascota = Mascota(id, tipoMascota, raza, edad, descripcion, peso, genero, urlImg)
            mascotaList.add(mascota)
        }
        cursor.close()
        db.close()
        return mascotaList
    }

    fun insertarCoordinador(){

    }
    fun getAllCoordinador(){

    }

}