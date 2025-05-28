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
    }

    //CREAR BASE DE DATOS
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQueryMacota = "CREATE TABLE $TABLE_NAMEMASCOTA ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TIPOMASCOTA TEXT, $COLUMN_RAZA TEXT, $COLUMN_EDAD TEXT, $COLUMN_DESCRIPCION TEXT, $COLUMN_PESO FLOAT, $COLUMN_GENERO TEXT, $COLUMN_URLIMG TEXT)"
        db?.execSQL(createTableQueryMacota)
    }

    //ACTUALIZAR BASE DE DATOS
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableMascotaQuery = "DROP TABLE IF EXISTS $TABLE_NAMEMASCOTA"
        db?.execSQL(dropTableMascotaQuery)
        onCreate(db)

    }

    //CRUD MASCOTA
    //CREAR
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

    //CONSULTARLISTA
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

    //Actualiza mascota
    fun updateMascota(mascota: Mascota){
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
        val whereclasue = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(mascota.id.toString())
        db.update(TABLE_NAMEMASCOTA, values, whereclasue, whereArgs)
        db.close()
    }

    //Eliminar mascota
    fun deleteMascota(mascotaId : Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArg = arrayOf(mascotaId.toString())
        db.delete(TABLE_NAMEMASCOTA, whereClause, whereArg)
        db.close()
    }

    // Obtener nota por el ID
    fun getMascotaById(mascotaId: Int): Mascota{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAMEMASCOTA WHERE $COLUMN_ID = $mascotaId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val tipoMascota = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPOMASCOTA))
        val raza = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RAZA))
        val edad = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EDAD))
        val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
        val peso = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_PESO))
        val genero = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENERO))
        val urlImg = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URLIMG))
        cursor.close()
        db.close()
        return Mascota(id, tipoMascota, raza, edad, descripcion, peso, genero, urlImg)
    }
    fun getMascotasByGenero(MascotaGenero: String): List<Mascota>{
        val mascotaList = mutableListOf<Mascota>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAMEMASCOTA WHERE LOWER($COLUMN_GENERO) =  LOWER('$MascotaGenero')"
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













}