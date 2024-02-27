package hr.k33zo.spacex.activity

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import hr.k33zo.spacex.dao.Repository
import hr.k33zo.spacex.factory.getRepository
import hr.k33zo.spacex.model.LaunchItem
import hr.k33zo.spacex.model.LaunchPadItem
import hr.k33zo.spacex.model.MissionItem
import hr.k33zo.spacex.model.RocketItem
import java.lang.IllegalArgumentException


private const val AUTHORITY = "hr.k33zo.spacex.api.provider"
private const val PATH_LAUNCHES = "launches"
private const val PATH_MISSIONS = "missions"
private const val PATH_ROCKETS = "rockets"
private const val PATH_LAUNCH_PADS = "launchpads"
val SPACEX_PROVIDER_CONTENT_URI_LAUNCHES: Uri = Uri.parse("content://$AUTHORITY/$PATH_LAUNCHES")
val SPACEX_PROVIDER_CONTENT_URI_MISSIONS: Uri = Uri.parse("content://$AUTHORITY/$PATH_MISSIONS")
val SPACEX_PROVIDER_CONTENT_URI_ROCKETS: Uri = Uri.parse("content://$AUTHORITY/$PATH_ROCKETS")
val SPACEX_PROVIDER_CONTENT_URI_LAUNCH_PADS: Uri = Uri.parse("content://$AUTHORITY/$PATH_LAUNCH_PADS")

private const val ITEMS_LAUNCHES = 10
private const val ITEM_ID_LAUNCHES = 20
private const val ITEMS_MISSIONS = 30
private const val ITEM_ID_MISSIONS = 40
private const val ITEMS_ROCKETS = 50
private const val ITEM_ID_ROCKETS = 60
private const val ITEMS_LAUNCH_PADS = 70
private const val ITEM_ID_LAUNCH_PADS = 60

private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)){
    addURI(AUTHORITY, PATH_LAUNCHES, ITEMS_LAUNCHES)
    addURI(AUTHORITY, "$PATH_LAUNCHES/#", ITEM_ID_LAUNCHES)
    addURI(AUTHORITY, PATH_MISSIONS, ITEMS_MISSIONS)
    addURI(AUTHORITY, "$PATH_MISSIONS/#", ITEM_ID_MISSIONS)
    addURI(AUTHORITY, PATH_ROCKETS, ITEMS_ROCKETS)
    addURI(AUTHORITY, "$PATH_ROCKETS/#", ITEM_ID_ROCKETS)
    addURI(AUTHORITY, PATH_LAUNCH_PADS, ITEMS_LAUNCH_PADS)
    addURI(AUTHORITY, "$PATH_LAUNCH_PADS/#", ITEM_ID_LAUNCH_PADS)
    this
}

class SpaceXContentProvider : ContentProvider() {

    private lateinit var repository: Repository

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
       when(URI_MATCHER.match(uri)){
           ITEMS_LAUNCHES -> return repository.deleteLaunch(selection, selectionArgs)
           ITEM_ID_LAUNCHES -> {
               uri.lastPathSegment?.let { id->
                   return repository.deleteLaunch(
                       "${LaunchItem::_id.name}=?",
                       arrayOf(id))
               }
           }
           ITEMS_MISSIONS -> return  repository.deleteMission(selection, selectionArgs)
           ITEM_ID_LAUNCHES ->{
               uri.lastPathSegment?.let { id->
                   return repository.deleteMission(
                       "${MissionItem::_id.name}=?",
                       arrayOf(id))
               }
           }
           ITEMS_ROCKETS -> return  repository.deleteRocket(selection, selectionArgs)
           ITEM_ID_ROCKETS ->{
               uri.lastPathSegment?.let { id->
                   return repository.deleteRocket(
                       "${RocketItem::_id.name}=?",
                       arrayOf(id))
               }
           }
           ITEMS_LAUNCH_PADS -> return  repository.deleteRocket(selection, selectionArgs)
           ITEM_ID_LAUNCH_PADS ->{
               uri.lastPathSegment?.let { id->
                   return repository.deleteLaunchPad(
                       "${LaunchPadItem::_id.name}=?",
                       arrayOf(id))
               }
           }
       }
        throw IllegalArgumentException("WRONG URI")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when (URI_MATCHER.match(uri)) {
            ITEMS_LAUNCHES -> {
                val id = repository.insertLaunch(values)
                ContentUris.withAppendedId(SPACEX_PROVIDER_CONTENT_URI_LAUNCHES, id)
            }
            ITEMS_MISSIONS -> {
                val id = repository.insertMission(values)
                ContentUris.withAppendedId(SPACEX_PROVIDER_CONTENT_URI_MISSIONS, id)
            }
            ITEMS_ROCKETS -> {
                val id = repository.insertRocket(values)
                ContentUris.withAppendedId(SPACEX_PROVIDER_CONTENT_URI_ROCKETS, id)
            }
            ITEMS_LAUNCH_PADS -> {
                val id = repository.insertLaunchPad(values)
                ContentUris.withAppendedId(SPACEX_PROVIDER_CONTENT_URI_LAUNCH_PADS, id)
            }
            else -> throw IllegalArgumentException("WRONG URI")
        }
    }

    override fun onCreate(): Boolean {
        repository = getRepository(context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (URI_MATCHER.match(uri)) {
            ITEMS_LAUNCHES -> repository.queryLaunch(
                projection,
                selection,
                selectionArgs,
                sortOrder
            )

            ITEMS_MISSIONS -> repository.queryMission(
                projection,
                selection,
                selectionArgs,
                sortOrder
            )
            ITEMS_ROCKETS -> repository.queryRocket(
                projection,
                selection,
                selectionArgs,
                sortOrder
            )
            ITEMS_LAUNCH_PADS -> repository.queryLaunchPad(
                projection,
                selection,
                selectionArgs,
                sortOrder
            )

            else -> throw IllegalArgumentException("WRONG URI")
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when(URI_MATCHER.match(uri)){
            ITEMS_LAUNCHES -> return repository.updateLaunch(values, selection, selectionArgs)
            ITEM_ID_LAUNCHES -> {
                uri.lastPathSegment?.let { id->
                    return repository.updateLaunch(
                        values,
                        "${LaunchItem::_id.name}=?",
                        arrayOf(id)
                    )
                }
            }
            ITEMS_MISSIONS  -> return repository.updateMission(values, selection, selectionArgs)
            ITEM_ID_MISSIONS -> {
                uri.lastPathSegment?.let { id->
                    return repository.updateMission(
                        values,
                        "${MissionItem::_id.name}=?",
                        arrayOf(id)
                    )
                }
            }
            ITEMS_ROCKETS  -> return repository.updateRocket(values, selection, selectionArgs)
            ITEM_ID_ROCKETS -> {
                uri.lastPathSegment?.let { id->
                    return repository.updateRocket(
                        values,
                        "${RocketItem::_id.name}=?",
                        arrayOf(id)
                    )
                }
            }
            ITEMS_LAUNCH_PADS  -> return repository.updateLaunchPad(values, selection, selectionArgs)
            ITEM_ID_LAUNCH_PADS -> {
                uri.lastPathSegment?.let { id->
                    return repository.updateLaunchPad(
                        values,
                        "${LaunchPadItem::_id.name}=?",
                        arrayOf(id)
                    )
                }
            }
        }
        throw IllegalArgumentException("WRONG URI")
    }
}