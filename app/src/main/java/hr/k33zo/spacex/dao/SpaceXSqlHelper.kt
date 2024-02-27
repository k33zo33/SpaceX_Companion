package hr.k33zo.spacex.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hr.k33zo.spacex.model.LaunchItem
import hr.k33zo.spacex.model.LaunchPadItem
import hr.k33zo.spacex.model.MissionItem
import hr.k33zo.spacex.model.RocketItem


private const val DB_NAME = "spacex.db"
private const val DB_VERSION = 1
private const val TABLE_LAUNCHES = "launches"
private const val TABLE_MISSIONS = "missions"
private const val TABLE_ROCKETS = "rockets"
private const val TABLE_LAUNCH_PADS = "launchpads"


private val CREATE_LAUNCHES_TABLE = "CREATE TABLE $TABLE_LAUNCHES( " +
        "${LaunchItem::_id.name} INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "${LaunchItem::flight_number.name} INTEGER NOT NULL, " +
        "${LaunchItem::mission_name.name} TEXT NOT NULL, " +
        "${LaunchItem::launch_date_utc.name} TEXT NOT NULL, " +
        "${LaunchItem::launch_success.name} INTEGER NOT NULL, " +
        "${LaunchItem::mission_patch.name} TEXT, " +
        "${LaunchItem::details.name} TEXT," +
        "${LaunchItem::youtube_id.name} TEXT" +
        ")"

private val CREATE_MISSIONS_TABLE = "CREATE TABLE $TABLE_MISSIONS( " +
        "${MissionItem::_id.name} INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "${MissionItem::mission_name.name} TEXT NOT NULL, " +
        "${MissionItem::wikipedia.name} TEXT NOT NULL, " +
        "${MissionItem::website.name} TEXT NOT NULL, " +
        "${MissionItem::description.name} TEXT" +
        ")"

private val CREATE_ROCKETS_TABLE = "CREATE TABLE $TABLE_ROCKETS( " +
        "${RocketItem::_id.name} INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "${RocketItem::active.name} INTEGER NOT NULL, " +
        "${RocketItem::country.name} TEXT NOT NULL, " +
        "${RocketItem::company.name} TEXT NOT NULL, " +
        "${RocketItem::wikipedia.name} TEXT NOT NULL, " +
        "${RocketItem::description.name} TEXT NOT NULL, " +
        "${RocketItem::rocket_name.name} TEXT NOT NULL, " +
        "${RocketItem::rocket_type.name} TEXT NOT NULL, " +
        "${RocketItem::flickr_images.name} TEXT" +
        ")"

private val CREATE_LAUNCH_PADS_TABLE = "CREATE TABLE $TABLE_LAUNCH_PADS( " +
        "${LaunchPadItem::_id.name} INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "${LaunchPadItem::status.name} TEXT NOT NULL, " +
        "${LaunchPadItem::wikipedia.name} TEXT NOT NULL, " +
        "${LaunchPadItem::details.name} TEXT, " +
        "${LaunchPadItem::site_name_long.name} TEXT NOT NULL, " +
        "${LaunchPadItem::name.name} TEXT NOT NULL, " +
        "${LaunchPadItem::region.name} TEXT NOT NULL, " +
        "${LaunchPadItem::latitude.name} REAL NOT NULL, " +
        "${LaunchPadItem::longitude.name} REAL NOT NULL" +
        ")"


private const val DROP_LAUNCHES_TABLE = "DROP TABLE IF EXISTS $TABLE_LAUNCHES"
private const val DROP_MISSIONS_TABLE = "DROP TABLE IF EXISTS $TABLE_MISSIONS"
private const val DROP_ROCKETS_TABLE = "DROP TABLE IF EXISTS $TABLE_ROCKETS"
private const val DROP_LAUNCH_PADS_TABLE = "DROP TABLE IF EXISTS $TABLE_LAUNCH_PADS"


class SpaceXSqlHelper(val context: Context?)
    :SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), Repository {

    override fun onCreate(db: SQLiteDatabase) {
       db.execSQL(CREATE_LAUNCHES_TABLE)
        db.execSQL(CREATE_MISSIONS_TABLE)
        db.execSQL(CREATE_ROCKETS_TABLE)
        db.execSQL(CREATE_LAUNCH_PADS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
       db.execSQL(DROP_LAUNCHES_TABLE)
        db.execSQL(DROP_MISSIONS_TABLE)
        db.execSQL(DROP_ROCKETS_TABLE)
        db.execSQL(DROP_LAUNCH_PADS_TABLE)
        onCreate(db)
    }
    override fun deleteLaunch(selection: String?, selectionArgs: Array<String>?)
        = writableDatabase.delete(
        TABLE_LAUNCHES,
            selection,
            selectionArgs
        )
    override fun insertLaunch(values: ContentValues?)
        = writableDatabase.insert(
        TABLE_LAUNCHES,
            null,
            values
        )
    override fun queryLaunch(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = readableDatabase.query(
        TABLE_LAUNCHES,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder
    )

    override fun updateLaunch(
        values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    )= writableDatabase.update(
        TABLE_LAUNCHES,
        values,
        selection,
        selectionArgs
    )

    override fun deleteMission(selection: String?, selectionArgs: Array<String>?)
        = writableDatabase.delete(
        TABLE_MISSIONS,
        selection,
        selectionArgs
    )

    override fun insertMission(values: ContentValues?)
        = writableDatabase.insert(
        TABLE_MISSIONS,
        null,
        values
    )

    override fun queryMission(
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = readableDatabase.query(
        TABLE_MISSIONS,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder
    )

    override fun updateMission(
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    )= writableDatabase.update(
        TABLE_MISSIONS,
        values,
        selection,
        selectionArgs
    )

    override fun deleteRocket(selection: String?, selectionArgs: Array<String>?)
    = writableDatabase.delete(
        TABLE_ROCKETS,
        selection,
        selectionArgs
    )
    override fun insertRocket(values: ContentValues?)
    = writableDatabase.insert(
        TABLE_ROCKETS,
        null,
        values
    )

    override fun queryRocket(
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = readableDatabase.query(
        TABLE_ROCKETS,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder
    )

    override fun updateRocket(
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    )= writableDatabase.update(
        TABLE_ROCKETS,
        values,
        selection,
        selectionArgs
    )

    override fun deleteLaunchPad(selection: String?, selectionArgs: Array<String>?)
        = writableDatabase.delete(
        TABLE_LAUNCH_PADS,
        selection,
        selectionArgs
    )

    override fun insertLaunchPad(values: ContentValues?)
        = writableDatabase.insert(
        TABLE_LAUNCH_PADS,
        null,
        values
    )

    override fun queryLaunchPad(
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = readableDatabase.query(
        TABLE_LAUNCH_PADS,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder
    )

    override fun updateLaunchPad(
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    )= writableDatabase.update(
        TABLE_LAUNCH_PADS,
        values,
        selection,
        selectionArgs
    )

}