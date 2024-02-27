package hr.k33zo.spacex.dao

import android.content.ContentValues
import android.database.Cursor

interface Repository {

    fun deleteLaunch(selection: String?, selectionArgs: Array<String>?): Int

    fun insertLaunch(values: ContentValues?): Long

    fun queryLaunch(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor?

    fun updateLaunch(
        values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int

    fun deleteMission(selection: String?, selectionArgs: Array<String>?): Int

    fun insertMission(values: ContentValues?): Long

    fun queryMission(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor?

    fun updateMission(
        values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int

    fun deleteRocket(selection: String?, selectionArgs: Array<String>?): Int

    fun insertRocket(values: ContentValues?): Long

    fun queryRocket(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor?

    fun updateRocket(
        values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int


    fun deleteLaunchPad(selection: String?, selectionArgs: Array<String>?): Int

    fun insertLaunchPad(values: ContentValues?): Long

    fun queryLaunchPad(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor?

    fun updateLaunchPad(
        values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int

}